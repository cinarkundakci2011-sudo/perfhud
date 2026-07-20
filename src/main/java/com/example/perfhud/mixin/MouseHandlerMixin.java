package com.example.perfhud.mixin;

import com.example.perfhud.util.CpsTracker;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @Inject(method = "onPress", at = @At("HEAD"))
    private void catchClick(long window, MouseInput input, int action, CallbackInfo ci) {
        // action == 1 represents a GLFW_PRESS event
        if (action == 1) {
            if (input.button() == 0) {       // 0 = Left Mouse Button
                CpsTracker.addLeftClick();
            } else if (input.button() == 1) { // 1 = Right Mouse Button
                CpsTracker.addRightClick();
            }
        }
    }
}
