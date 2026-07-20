package com.example.perfhud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

public class HudRenderer {

    public static void render(GuiGraphicsExtractor graphics, DeltaTracker tickCounter) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui || !HudConfig.enabled || mc.player == null) return;

        Font font = mc.getFont(); // Updated from getTextRenderer() to getFont()
        int baseX = HudConfig.x;
        int baseY = HudConfig.y;

        // 1. Calculate and fetch standard network/performance updates
        int fps = mc.getFps();
        int ping = 0;
        if (mc.getConnection() != null) {
            var entry = mc.getConnection().getPlayerInfo(mc.player.getUUID());
            if (entry != null) ping = entry.getLatency();
        }

        String stats = String.format("FPS: %d | Ping: %dms", fps, ping);
        String cps = String.format("CPS: %d L | %d R", CpsTracker.getLeftCps(), CpsTracker.getRightCps());

        // Display Info Plate
        graphics.fill(baseX, baseY, baseX + 130, baseY + 26, HudConfig.backgroundColor);
        graphics.text(font, Component.literal(stats), baseX + 5, baseY + 4, HudConfig.textColor);
        graphics.text(font, Component.literal(cps), baseX + 5, baseY + 14, HudConfig.textColor);

        // 2. Map Key Configurations
        int startKeyY = baseY + 30;
        int size = 20;
        int gap = 2;

        boolean W = mc.options.keyUp.isPressed();
        boolean A = mc.options.keyLeft.isPressed();
        boolean S = mc.options.keyDown.isPressed();
        boolean D = mc.options.keyRight.isPressed();
        boolean SPACE = mc.options.keyJump.isPressed();
        boolean LMB = mc.options.keyAttack.isPressed();
        boolean RMB = mc.options.keyUse.isPressed();

        // Row 1: W
        drawIndividualKey(graphics, font, "W", baseX + size + gap, startKeyY, size, size, W);

        // Row 2: A, S, D
        drawIndividualKey(graphics, font, "A", baseX, startKeyY + size + gap, size, size, A);
        drawIndividualKey(graphics, font, "S", baseX + size + gap, startKeyY + size + gap, size, size, S);
        drawIndividualKey(graphics, font, "D", baseX + (size + gap) * 2, startKeyY + size + gap, size, size, D);

        // Row 3: Mouse Triggers
        int splitMouseWidth = ((size * 3) + (gap * 2) - gap) / 2;
        drawIndividualKey(graphics, font, "LMB", baseX, startKeyY + (size + gap) * 2, splitMouseWidth, size, LMB);
        drawIndividualKey(graphics, font, "RMB", baseX + splitMouseWidth + gap, startKeyY + (size + gap) * 2, splitMouseWidth, size, RMB);

        // Row 4: Spacebar Layout
        int spaceBarWidth = (size * 3) + (gap * 2);
        drawIndividualKey(graphics, font, "_______", baseX, startKeyY + (size + gap) * 3, spaceBarWidth, 14, SPACE);
    }

    private static void drawIndividualKey(GuiGraphicsExtractor gfx, Font font, String key, int x, int y, int w, int h, boolean active) {
        int back = active ? HudConfig.highlightColor : HudConfig.backgroundColor;
        int text = active ? 0xFF000000 : HudConfig.textColor;

        gfx.fill(x, y, x + w, y + h, back);
        
        int textX = x + (w - font.width(key)) / 2;
        int textY = y + (h - 8) / 2;
        gfx.text(font, Component.literal(key), textX, textY, text);
    }
}
