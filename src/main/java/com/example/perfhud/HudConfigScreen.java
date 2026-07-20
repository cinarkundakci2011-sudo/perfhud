package com.example.perfhud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class HudConfigScreen extends Screen {
    private boolean isDragging = false;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;

    public HudConfigScreen() {
        super(Component.literal("PerfHud Configuration"));
    }

    @Override
    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        Minecraft mc = Minecraft.getInstance();
        
        // 1. Draw a semi-transparent dark background tint over the game
        graphics.fill(0, 0, this.width, this.height, 0x66000000);

        // 2. Render the LIVE HUD layout preview at its actual position
        HudRenderer.render(graphics, null);

        // 3. Draw a visual anchor/border around the HUD box so the user knows it can be dragged
        int hudWidth = 130;
        int hudHeight = 110; // Approximate height including keystroke layout
        graphics.fill(HudConfig.x - 2, HudConfig.y - 2, HudConfig.x + hudWidth + 2, HudConfig.y + hudHeight + 2, 0x33FFFF00);

        // 4. Render the Configuration Control Window in the center of the screen
        int menuWidth = 200;
        int menuHeight = 90;
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int left = centerX - (menuWidth / 2);
        int top = centerY - (menuHeight / 2);

        // Background box
        graphics.fill(left, top, left + menuWidth, top + menuHeight, 0xDD000000);
        
        // Title & Controls Info
        graphics.text(mc.font, Component.literal("⚙ PerfHud Options"), left + 10, top + 10, 0xFFFF5555);
        
        // Toggle Button Area
        int btnTop = top + 32;
        int btnBottom = top + 52;
        int btnColor = HudConfig.enabled ? 0xFF22AA22 : 0xFFAA2222;
        graphics.fill(left + 10, btnTop, left + menuWidth - 10, btnBottom, btnColor);
        
        String toggleText = HudConfig.enabled ? "✔ HUD status: ENABLED" : "❌ HUD status: DISABLED";
        graphics.text(mc.font, Component.literal(toggleText), left + 20, btnTop + 6, 0xFFFFFFFF);

        // Instructions
        graphics.text(mc.font, Component.literal("🖱 Click & Drag the HUD to reposition"), left + 10, top + 60, 0xFF999999);
        graphics.text(mc.font, Component.literal("⌨ Use Arrow Keys for fine tuning"), left + 10, top + 74, 0xFF999999);

        super.render(graphics, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int left = centerX - 100;
        int top = centerY - 45;

        // Click detection: Check if toggle button is clicked
        if (mouseX >= left + 10 && mouseX <= left + 190 && mouseY >= top + 32 && mouseY <= top + 52) {
            HudConfig.enabled = !HudConfig.enabled;
            Minecraft.getInstance().getSoundManager().play(net.minecraft.client.sound.PositionedSoundInstance.master(
                net.minecraft.sound.SoundEvents.UI_BUTTON_CLICK, 1.0F
            ));
            return true;
        }

        // Click detection: Check if user clicked inside the HUD bounds to drag it
        if (mouseX >= HudConfig.x && mouseX <= HudConfig.x + 130 && mouseY >= HudConfig.y && mouseY <= HudConfig.y + 110) {
            isDragging = true;
            dragOffsetX = (int) mouseX - HudConfig.x;
            dragOffsetY = (int) mouseY - HudConfig.y;
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        isDragging = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (isDragging) {
            HudConfig.x = (int) mouseX - dragOffsetX;
            HudConfig.y = (int) mouseY - dragOffsetY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Pixel-perfect micro adjustments using arrow keys
        if (keyCode == GLFW.GLFW_KEY_UP) HudConfig.y -= 1;
        if (keyCode == GLFW.GLFW_KEY_DOWN) HudConfig.y += 1;
        if (keyCode == GLFW.GLFW_KEY_LEFT) HudConfig.x -= 1;
        if (keyCode == GLFW.GLFW_KEY_RIGHT) HudConfig.x += 1;

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false; // Prevents singleplayer game from freezing while customizing
    }
}
