package com.dreamcatcher.mobile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Dream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dreamId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "dreamAnalysisId", nullable = true)
    private DreamAnalysis dreamAnalysis;

    @ManyToOne
    @JoinColumn(name = "sleepId", nullable = true)
    private Sleep sleep;

    private String visitor;
    private String plot;
    private String location;
    private String mood;
    private String additionalInfo;

    //Constructor
    public Dream(){};

    public Dream(User user, Sleep sleep, String visitor, String plot, String location, String mood, String additionalInfo){
        this.user = user;
        this.sleep = sleep;
        this.visitor = visitor;
        this.plot = plot;
        this.location = location;
        this.mood = mood;
        this.additionalInfo = additionalInfo;
    }

    //Getters
    public Integer getDreamId(){
        return dreamId;
    }

    public User getUser(){
        return user;
    }

    public DreamAnalysis getDreamAnalysis(){
        return dreamAnalysis;
    }

    public Sleep getSleep(){
        return sleep;
    }

    public String getVisitor(){
        return visitor;
    }

    public String getPlot(){
        return plot;
    }

    public String getLocation(){
        return location;
    }

    public String getMood(){
        return mood;
    }

    public String getAdditionalInfo(){
        return additionalInfo;
    }

    //Setters
    public void setId(Integer dreamId) {
        this.dreamId = dreamId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDreamAnalysis(DreamAnalysis dreamAnalysis) {
        this.dreamAnalysis = dreamAnalysis;
    }

    public void setSleep(Sleep sleep) {
        this.sleep = sleep;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    //toString
    @Override
    public String toString() {
        return "Dream{" +
            "dreamId=" + dreamId +
            ", userId=" + (user != null ? user.getUserId() : null) +
            ", analysisId=" + (dreamAnalysis != null ? dreamAnalysis.getDreamAnalysisId() : null) +
            ", sleepId=" + (sleep != null ? sleep.getSleepId() : null) +
            ", visitor=" + visitor +
            ", plot=" + plot +
            ", location=" + location +
            ", mood=" + mood +
            ", additionalInfo=" + additionalInfo +
            "}";
    }
}