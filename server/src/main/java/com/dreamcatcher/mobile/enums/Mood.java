package com.dreamcatcher.mobile.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Mood {
    HAPPY("Happy"),
    EXCITED("Excited"),
    CALM("Calm"),
    INDIFFERENT("Indifferent"),
    CONFUSED("Confused"),
    PEACEFUL("Peaceful"),
    NEUTRAL("Neutral"),
    SAD("Sad"),
    ANXIOUS("Anxious"),
    FEARFUL("Fearful");

    private final String displayName;

    Mood(String displayName){
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName(){
        return displayName;
    }
}