package com.example.perfhud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping; // Mojang Mapping fix
import com.mojang.blaze3d.platform.InputConstants; // Mojang Mapping fix
import org.lwjgl.glfw.GLFW;

public class PerformanceHudClient implements ClientModInitializer { // Fixed class name mismatch
    private static KeyMapping configKeyBinding; 

    @Override
    public void onInitializeClient() {
        // Register the "O" key as the configuration menu hotkey
        configKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "key.perfhud.config", 
            InputConstants.Type.KEYSYM, 
            GLFW.GLFW_KEY_O, 
            "category.perfhud"
        ));

        // Listen to ticks to catch the input trigger
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (configKeyBinding.consumeClick()) { // Mojang mapping variant of wasPressed()
                if (client.gui.screen() == null) { // Minecraft 26.2 field relocation fix
                    client.setScreenAndShow(new HudConfigScreen()); // Minecraft 26.2 method change fix
                }
            }
        });
    }
}
