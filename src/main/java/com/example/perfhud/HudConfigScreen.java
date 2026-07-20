package com.example.perfhud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class HudConfigScreen extends Screen {
    private boolean isDragging = false;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;
    private boolean wasMouseDown = false;
    private int clickCooldown = 0;
    private int keyCooldown = 0;

    public HudConfigScreen() {
        super(Component.literal("PerfHud Configuration"));
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);
        Minecraft mc = Minecraft.getInstance();
        
        // Ultimate Mapping Bypass: Pulls the handle directly from the active OpenGL render context
        long window = GLFW.glfwGetCurrentContext(); 

        // Direct Input Polling
        boolean isMouseDown = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
        
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int menuWidth = 200;
        int menuHeight = 90;
        int left = centerX - (menuWidth / 2);
        int top = centerY - (menuHeight / 2);

        if (clickCooldown > 0) clickCooldown--;
        if (keyCooldown > 0) keyCooldown--;

        if (isMouseDown) {
            if (!wasMouseDown) {
                // Check if options toggle button is clicked
                if (clickCooldown == 0 && mouseX >= left + 10 && mouseX <= left + 190 && mouseY >= top + 32 && mouseY <= top + 52) {
                    HudConfig.enabled = !HudConfig.enabled;
                    clickCooldown = 10;
                }
                
                // Check if HUD container is clicked for dragging
                if (mouseX >= HudConfig.x && mouseX <= HudConfig.x + 130 && mouseY >= HudConfig.y && mouseY <= HudConfig.y + 110) {
                    isDragging = true;
                    dragOffsetX = mouseX - HudConfig.x;
                    dragOffsetY = mouseY - HudConfig.y;
                }
            }
            
            if (isDragging) {
                HudConfig.x = mouseX - dragOffsetX;
                HudConfig.y = mouseY - dragOffsetY;
            }
        } else {
            isDragging = false;
        }
        wasMouseDown = isMouseDown;

        // Micro pixel adjustments via Arrow Keys
        if (keyCooldown == 0) {
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_UP) == GLFW.GLFW_PRESS) { HudConfig.y -= 1; keyCooldown = 2; }
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_DOWN) == GLFW.GLFW_PRESS) { HudConfig.y += 1; keyCooldown = 2; }
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT) == GLFW.GLFW_PRESS) { HudConfig.x -= 1; keyCooldown = 2; }
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_PRESS) { HudConfig.x += 1; keyCooldown = 2; }
        }

        graphics.fill(0, 0, this.width, this.height, 0x66000000); 
        HudRenderer.render(graphics, null); 

        graphics.fill(HudConfig.x - 2, HudConfig.y - 2, HudConfig.x + 132, HudConfig.y + 112, 0x33FFFF00);

        graphics.fill(left, top, left + menuWidth, top + menuHeight, 0xDD000000);
        graphics.text(mc.font, Component.literal("⚙ PerfHud Options"), left + 10, top + 10, 0xFFFF5555);
        
        int btnColor = HudConfig.enabled ? 0xFF22AA22 : 0xFFAA2222;
        graphics.fill(left + 10, top + 32, left + menuWidth - 10, top + 52, btnColor);
        String toggleText = HudConfig.enabled ? "✔ HUD status: ENABLED" : "❌ HUD status: DISABLED";
        graphics.text(mc.font, Component.literal(toggleText), left + 20, top + 38, 0xFFFFFFFF);

        graphics.text(mc.font, Component.literal("🖱 Click & Drag the HUD to move it"), left + 10, top + 60, 0xFF999999);
        graphics.text(mc.font, Component.literal("⌨ Use Arrow Keys for micro tuning"), left + 10, top + 74, 0xFF999999);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
