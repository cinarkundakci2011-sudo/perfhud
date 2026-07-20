package com.example.perfhud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class PerfHudClient implements ClientModInitializer {
    private static KeyBinding configKeyBinding;

    @Override
    public void onInitializeClient() {
        // 1. Register the "O" key as the configuration menu hotkey
        configKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.perfhud.config", 
            InputUtil.Type.KEYSYM, 
            GLFW.GLFW_KEY_O, 
            "category.perfhud"
        ));

        // 2. Listen to ticks so that pressing the key instantly invokes the GUI screen
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (configKeyBinding.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new HudConfigScreen());
                }
            }
        });
        
        // Your existing Hud rendering callback hook remains untouched here...
    }
}
