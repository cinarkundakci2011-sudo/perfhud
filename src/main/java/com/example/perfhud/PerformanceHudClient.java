package com.example.perfhud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class PerformanceHudClient implements ClientModInitializer {
    private boolean wasOPressed = false;

    @Override
    public void onInitializeClient() {
        // 1. Register HUD element in the rendering pipeline
        HudElementRegistry.addLast(
            Identifier.fromNamespaceAndPath("perfhud", "performance_hud"),
            HudRenderer::render
        );

        // 2. Listen on every client tick for pressing 'O' to open the configuration menu
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
