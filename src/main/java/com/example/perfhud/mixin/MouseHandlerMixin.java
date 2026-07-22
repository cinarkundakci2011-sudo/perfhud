package com.example.perfhud.mixin;

import com.example.perfhud.util.CpsTracker;
import net.minecraft.client.MouseHandler;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    // Target modern GLFW mouse button handler
    @Inject(method = "onButton", at = @At("HEAD"), require = 0)
    private void onMouseButton(long windowPointer, int button, int action, int modifiers, CallbackInfo ci) {
        if (action == GLFW.GLFW_PRESS) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                CpsTracker.onClickLeft();
            } else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                CpsTracker.onClickRight();
            }
        }
    }

    // Fallback signature for alternative mappings
    @Inject(method = "onPress", at = @At("HEAD"), require = 0)
    private void onMousePress(long windowPointer, int button, int action, int modifiers, CallbackInfo ci) {
        if (action == GLFW.GLFW_PRESS) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                CpsTracker.onClickLeft();
            } else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                CpsTracker.onClickRight();
            }
        }
    }
}
