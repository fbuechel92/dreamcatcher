package com.dreamcatcher.mobile.enums;

import com.fasterxml.jackson.annotation.JsonValue;

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

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }
}