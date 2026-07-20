package com.example.perfhud.mixin;

import com.example.perfhud.CpsTracker;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Inject(method = "onMouseButton", at = @At("HEAD"))
    private void catchClick(long window, int button, int action, int mods, CallbackInfo ci) {
        if (action == 1) { // 1 represents GLFW_PRESS
            if (button == 0) CpsTracker.addLeftClick();
            if (button == 1) CpsTracker.addRightClick();
        }
    }
}
