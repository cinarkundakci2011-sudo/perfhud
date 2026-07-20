package com.example.perfhud.mixin;

import com.example.perfhud.util.CpsTracker;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo; // Imported the new modern input wrapper
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    
    // The exact match: long (window), MouseButtonInfo (info), int (action)
    @Inject(method = "onButton", at = @At("HEAD"))
    private void catchClick(long window, MouseButtonInfo buttonInfo, int action, CallbackInfo ci) {
        // action == 1 represents a GLFW_PRESS event
        if (action == 1) {
            int button = buttonInfo.button(); // Extracting the button int from the record component
            if (button == 0) {       // 0 = Left Mouse Button
                CpsTracker.addLeftClick();
            } else if (button == 1) { // 1 = Right Mouse Button
                CpsTracker.addRightClick();
            }
        }
    }
}
