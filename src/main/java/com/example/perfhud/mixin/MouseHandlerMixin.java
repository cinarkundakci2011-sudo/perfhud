package com.example.perfhud.mixin;

import com.example.perfhud.util.CpsTracker;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Inject(method = "onMouseButton", at = @At("HEAD"), require = 0)
    private void onMouseButton(long windowPointer, MouseButtonInfo buttonInfo, int action, CallbackInfo ci) {
        if (action == GLFW.GLFW_PRESS) {
            if (buttonInfo.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                CpsTracker.onClickLeft();
            } else if (buttonInfo.button() == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                CpsTracker.onClickRight();
            }
        }
    }
}
