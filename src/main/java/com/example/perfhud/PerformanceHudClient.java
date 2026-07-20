package com.example.perfhud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.resources.Identifier;

public class PerformanceHudClient implements ClientModInitializer {
    public static final String MOD_ID = "perfhud";

    @Override
    public void onInitializeClient() {
        // Passing the method reference satisfies the functional interface cleanly
        HudElementRegistry.attachElementBefore(
            VanillaHudElements.CHAT,
            Identifier.fromNamespaceAndPath(MOD_ID, "hud_overlay"),
            HudRenderer::render
        );
    }
}
