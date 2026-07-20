package com.example.perfhud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.rendering.v1.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.VanillaHudElements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

public class PerformanceHudClient implements ClientModInitializer {
    public static final String MOD_ID = "perfhud";

    @Override
    public void onInitializeClient() {
        // Registers our custom element right before the chat overlay handles z-spacing
        HudElementRegistry.attachElementBefore(
            VanillaHudElements.CHAT,
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "hud_overlay"),
            HudRenderer::render
        );

        // Standard client command for launcher-agnostic configurations
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommands.literal("hud")
                .then(ClientCommands.literal("toggle")
                    .executes(context -> {
                        HudConfig.enabled = !HudConfig.enabled;
                        context.getSource().sendFeedback(Component.literal("HUD set to: " + HudConfig.enabled));
                        return 1;
                    })
                )
                .then(ClientCommands.literal("position")
                    .then(ClientCommands.argument("x", IntegerArgumentType.integer())
                        .then(ClientCommands.argument("y", IntegerArgumentType.integer())
                            .executes(context -> {
                                HudConfig.x = IntegerArgumentType.getInteger(context, "x");
                                HudConfig.y = IntegerArgumentType.getInteger(context, "y");
                                context.getSource().sendFeedback(Component.literal("HUD updated to X:" + HudConfig.x + " Y:" + HudConfig.y));
                                return 1;
                            })
                        )
                    )
                )
            );
        });
    }
}
