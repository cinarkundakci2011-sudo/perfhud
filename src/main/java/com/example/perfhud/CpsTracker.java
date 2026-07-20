package com.example.perfhud;

import java.util.ArrayList;
import java.util.List;

public class CpsTracker {
    private static final List<Long> leftClicks = new ArrayList<>();
    private static final List<Long> rightClicks = new ArrayList<>();

    public static synchronized void addLeftClick() { leftClicks.add(System.currentTimeMillis()); }
    public static synchronized void addRightClick() { rightClicks.add(System.currentTimeMillis()); }

    public static synchronized int getLeftCps() {
        cleanOldClicks(leftClicks);
        return leftClicks.size();
    }

    public static synchronized int getRightCps() {
        cleanOldClicks(rightClicks);
        return rightClicks.size();
    }

    private static void cleanOldClicks(List<Long> clickList) {
        long limit = System.currentTimeMillis() - 1000;
        clickList.removeIf(timestamp -> timestamp < limit);
    }
}
