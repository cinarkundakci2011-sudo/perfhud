package com.example.perfhud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.lwjgl.glfw.GLFW;

public class PerformanceHudClient implements ClientModInitializer {
    private boolean wasOPressed = false;

    @Override
    public void onInitializeClient() {
        // 1. Tell Minecraft to actually run our HUD rendering loop on every frame
        HudRenderCallback.EVENT.register(HudRenderer::render);

        // 2. Listen on every client tick for the 'O' key press to open the config screen
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            long window = GLFW.glfwGetCurrentContext();
            boolean isOPressed = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_O) == GLFW.GLFW_PRESS;

            if (isOPressed && !wasOPressed) {
                if (client.gui.screen() == null) {
                    client.setScreenAndShow(new HudConfigScreen());
                }
            }
            wasOPressed = isOPressed;
        });
    }
}
