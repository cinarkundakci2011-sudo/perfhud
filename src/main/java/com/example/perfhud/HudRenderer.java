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

        Font font = mc.font; // Mojang mapped field name
        int baseX = HudConfig.x;
        int baseY = HudConfig.y;

        int fps = mc.getFps();
        int ping = 0;
        if (mc.getConnection() != null) {
            var entry = mc.getConnection().getPlayerInfo(mc.player.getUUID());
            if (entry != null) ping = entry.getLatency();
        }

        String stats = String.format("FPS: %d | Ping: %dms", fps, ping);
        String cps = String.format("CPS: %d L | %d R", CpsTracker.getLeftCps(), CpsTracker.getRightCps());

        graphics.fill(baseX, baseY, baseX + 130, baseY + 26, HudConfig.backgroundColor);
        graphics.text(font, Component.literal(stats), baseX + 5, baseY + 4, HudConfig.textColor);
        graphics.text(font, Component.literal(cps), baseX + 5, baseY + 14, HudConfig.textColor);

        int startKeyY = baseY + 30;
        int size = 20;
        int gap = 2;

        // Uses official Mojang mapping method signatures (.isDown())
        boolean W = mc.options.keyUp.isDown();
        boolean A = mc.options.keyLeft.isDown();
        boolean S = mc.options.keyDown.isDown();
        boolean D = mc.options.keyRight.isDown();
        boolean SPACE = mc.options.keyJump.isDown();
        boolean LMB = mc.options.keyAttack.isDown();
        boolean RMB = mc.options.keyUse.isDown();

        drawIndividualKey(graphics, font, "W", baseX + size + gap, startKeyY, size, size, W);
        drawIndividualKey(graphics, font, "A", baseX, startKeyY + size + gap, size, size, A);
        drawIndividualKey(graphics, font, "S", baseX + size + gap, startKeyY + size + gap, size, size, S);
        drawIndividualKey(graphics, font, "D", baseX + (size + gap) * 2, startKeyY + size + gap, size, size, D);

        int splitMouseWidth = ((size * 3) + (gap * 2) - gap) / 2;
        drawIndividualKey(graphics, font, "LMB", baseX, startKeyY + (size + gap) * 2, splitMouseWidth, size, LMB);
        drawIndividualKey(graphics, font, "RMB", baseX + splitMouseWidth + gap, startKeyY + (size + gap) * 2, splitMouseWidth, size, RMB);

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
