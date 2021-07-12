package me.cunzai.bili.util;/*
 * @ Created with IntelliJ IDEA
 * @ Author EmptyIrony
 * @ Date 2021/7/12
 * @ Time 21:59
 */

public enum MedalColor {
    GREEN(0, 4, "&2"),
    BLUE(5, 8, "&3"),
    PURPLE(9, 16, "&d"),
    GOLD(17, 20, "&6"),
    RE_GREEN(21, 24, "&a"),
    RE_BLUE(25, 28, "&9"),
    RE_PURPLE(29, 32, "&5"),
    RED(33, 36, "&c"),
    YELLOW(37, 40, "&e");


    final int min;
    final int max;
    final String color;

    MedalColor(int min, int max, String color) {
        this.min = min;
        this.max = max;
        this.color = color;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public String getColor() {
        return color;
    }
}
