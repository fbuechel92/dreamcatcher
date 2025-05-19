package com.dreamcatcher.mobile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Dream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dreamId;
    private Integer userId;
    private Integer analysisId;
    private Integer sleepId;
    private Integer theoryId;
    private String visitor;
    private String plot;
    private String location;
    private String mood;
    private String additionalInfo;

    //Constructor
    public Dream(){};

    public Dream(Integer dreamId, Integer userId, Integer analysisId, Integer sleepId, Integer theoryId, String visitor, String plot, String location, String mood, String additionalInfo){
        this.dreamId = dreamId;
        this.userId = userId;
        this.analysisId = analysisId;
        this.sleepId = sleepId;
        this.theoryId = theoryId;
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

    public Integer getUserId(){
        return userId;
    }

    public Integer getAnalysisId(){
        return analysisId;
    }

    public Integer getSleepId(){
        return sleepId;
    }

    public Integer getTheoryId(){
        return theoryId;
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

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setAnalysisId(Integer analysisId) {
        this.analysisId = analysisId;
    }

    public void setSleepId(Integer sleepId) {
        this.sleepId = sleepId;
    }

    public void setTheoryId(Integer theoryId) {
        this.theoryId = theoryId;
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
            ", userId=" + userId +
            ", analysisId=" + analysisId +
            ", sleepId=" + sleepId +
            ", theoryId=" + theoryId +
            ", visitor='" + visitor +
            ", plot='" + plot +
            ", location='" + location +
            ", mood='" + mood +
            ", additionalInfo='" + additionalInfo +
            '}';
    }
}