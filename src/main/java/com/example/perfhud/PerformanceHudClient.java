package com.example.perfhud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry; // Updated package
import net.minecraft.resources.Identifier; // Or net.minecraft.util.Identifier depending on your mappings

public class PerformanceHudClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register your HUD element to render at the end of the HUD pipeline
        HudElementRegistry.addLast(
            Identifier.of("perfhud", "performance_hud"), 
            HudRenderer::render
        );
    }
}
