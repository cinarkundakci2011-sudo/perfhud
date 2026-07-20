package com.example.perfhud.mixin;

import com.example.perfhud.util.CpsTracker;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    
    // The exact signature requires: long window, int button, int action, int mods
    @Inject(method = "onPress", at = @At("HEAD"))
    private void catchClick(long window, int button, int action, int mods, CallbackInfo ci) {
        // action == 1 represents a GLFW_PRESS event
        if (action == 1) {
            if (button == 0) {       // 0 = Left Mouse Button
                CpsTracker.addLeftClick();
            } else if (button == 1) { // 1 = Right Mouse Button
                CpsTracker.addRightClick();
            }
        }
    }
}
