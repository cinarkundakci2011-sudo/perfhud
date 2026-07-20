package com.example.perfhud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.resources.Identifier;

public class PerformanceHudClient implements ClientModInitializer {
    public static final String MOD_ID = "perfhud";

    @Override
    public void onInitializeClient() {
        // This will now accept your renderer out-of-the-box
        HudElementRegistry.attachElementBefore(
            VanillaHudElements.CHAT,
            Identifier.fromNamespaceAndPath(MOD_ID, "hud_overlay"),
            new HudRenderer()
        );
    }
}
