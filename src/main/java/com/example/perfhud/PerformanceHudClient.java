package com.example.perfhud;

import net.fabricmc.api.ClientModInitializer;
// 1. Added the new .hud sub-package
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
// 2. ResourceLocation is now Identifier
import net.minecraft.resources.Identifier;

public class PerformanceHudClient implements ClientModInitializer {
    public static final String MOD_ID = "perfhud";

    @Override
    public void onInitializeClient() {
        // 3. Updated to use Identifier instead of ResourceLocation
        HudElementRegistry.attachElementBefore(
            VanillaHudElements.CHAT,
            Identifier.fromNamespaceAndPath(MOD_ID, "hud_overlay"),
            new HudRenderer()
        );
    }
}
