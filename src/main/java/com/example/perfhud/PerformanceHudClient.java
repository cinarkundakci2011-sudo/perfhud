package com.example.perfhud;

import net.fabricmc.api.ClientModInitializer;

public class PerformanceHudClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Intentionally left empty!
        // The HUD and configuration menu keys are tracked natively in the render pipeline,
        // but this class must exist to satisfy the fabric.mod.json entrypoint loader.
    }
}
