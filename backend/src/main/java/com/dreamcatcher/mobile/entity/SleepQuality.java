package com.dreamcatcher.mobile.entity;

public class SleepQuality {
    private int sleepId;
    private int sleepQuality; // 0: no answer, 1: bad, 2: ok, 3: great
    private int sleepLength; // in hours

    //Constructor
    public SleepQuality(){};

    public SleepQuality(int sleepId, int sleepQuality, int sleepLength) {
        this.sleepId = sleepId;
        this.sleepQuality = sleepQuality;
        this.sleepLength = sleepLength;
    }

    //Getters
    public int getSleepId() {
        return sleepId;
    }

    public int getSleepQuality() {
        return sleepQuality;
    }

    public int getSleepLength() {
        return sleepLength;
    }

    //Setters
    public void setSleepId(int sleepId) {
        this.sleepId = sleepId;
    }

    public void setSleepQuality(int sleepQuality) {
        this.sleepQuality = sleepQuality;
    }

    public void setSleepLength(int sleepLength) {
        this.sleepLength = sleepLength;
    }

    @Override
    public String toString() {
        return "SleepQuality{" +
                "sleepId=" + sleepId +
                ", sleepQuality=" + sleepQuality +
                ", sleepLength=" + sleepLength +
                '}';
    }
}



