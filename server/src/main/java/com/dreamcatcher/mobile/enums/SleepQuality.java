package com.dreamcatcher.mobile.enums;

public enum SleepQuality {
    EXCELLENT("Excellent"),
    GOOD("Good"),
    AVERAGE("Average"),
    POOR("Poor"),
    TERRIBLE("Terrible");

    private final String displayName;

    SleepQuality(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}