package com.example.perfhud.util;

import java.util.ArrayList;
import java.util.List;

public class CpsTracker {
    private static final List<Long> leftClicks = new ArrayList<>();
    private static final List<Long> rightClicks = new ArrayList<>();

    public static synchronized void onClickLeft() {
        leftClicks.add(System.currentTimeMillis());
    }

    public static synchronized void onClickRight() {
        rightClicks.add(System.currentTimeMillis());
    }

    public static synchronized int getLeftCps() {
        return getCps(leftClicks);
    }

    public static synchronized int getRightCps() {
        return getCps(rightClicks);
    }

    private static int getCps(List<Long> clicks) {
        long now = System.currentTimeMillis();
        clicks.removeIf(timestamp -> now - timestamp > 1000);
        return clicks.size();
    }
}
