package com.example.perfhud.util;

import java.util.concurrent.ConcurrentLinkedQueue;

public class CpsTracker {
    private static final ConcurrentLinkedQueue<Long> LEFT_CLICKS = new ConcurrentLinkedQueue<>();
    private static final ConcurrentLinkedQueue<Long> RIGHT_CLICKS = new ConcurrentLinkedQueue<>();

    public static void addLeftClick() {
        LEFT_CLICKS.add(System.currentTimeMillis());
    }

    public static void addRightClick() {
        RIGHT_CLICKS.add(System.currentTimeMillis());
    }

    public static int getLeftCps() {
        long now = System.currentTimeMillis();
        LEFT_CLICKS.removeIf(timestamp -> now - timestamp > 1000);
        return LEFT_CLICKS.size();
    }

    public static int getRightCps() {
        long now = System.currentTimeMillis();
        RIGHT_CLICKS.removeIf(timestamp -> now - timestamp > 1000);
        return RIGHT_CLICKS.size();
    }
}
