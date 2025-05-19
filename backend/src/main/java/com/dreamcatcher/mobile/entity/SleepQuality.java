package com.dreamcatcher.mobile.entity;

public class SleepQuality {
    private Integer sleepId;
    private Integer sleepQuality; // 0: no answer, 1: bad, 2: ok, 3: great
    private Integer sleepLength; // in hours

    //Constructor
    public SleepQuality(){};

    public SleepQuality(Integer sleepId, Integer sleepQuality, Integer sleepLength) {
        this.sleepId = sleepId;
        this.sleepQuality = sleepQuality;
        this.sleepLength = sleepLength;
    }

    //Getters
    public Integer getSleepId() {
        return sleepId;
    }

    public Integer getSleepQuality() {
        return sleepQuality;
    }

    public Integer getSleepLength() {
        return sleepLength;
    }

    //Setters
    public void setSleepId(Integer sleepId) {
        this.sleepId = sleepId;
    }

    public void setSleepQuality(Integer sleepQuality) {
        this.sleepQuality = sleepQuality;
    }

    public void setSleepLength(Integer sleepLength) {
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



