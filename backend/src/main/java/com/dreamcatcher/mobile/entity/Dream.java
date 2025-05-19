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
    @JoinColumn(name = "analysisId", nullable = false)
    private Analysis analysis;

    @ManyToOne
    @JoinColumn(name = "sleepId", nullable = false)
    private Sleep sleep;

    @ManyToOne
    @JoinColumn(name = "theoryId", nullable = false)
    private Theory theory;

    private String visitor;
    private String plot;
    private String location;
    private String mood;
    private String additionalInfo;

    //Constructor
    public Dream(){};

    public Dream(Integer dreamId, User user, Analysis analysis, Sleep sleep, Theory theory, String visitor, String plot, String location, String mood, String additionalInfo){
        this.dreamId = dreamId;
        this.user = user;
        this.analysis = analysis;
        this.sleep = sleep;
        this.theory = theory;
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

    public Analysis getAnalysis(){
        return analysis;
    }

    public Sleep getSleep(){
        return sleep;
    }

    public Theory getTheory(){
        return theory;
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

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }

    public void setSleep(Sleep sleep) {
        this.sleep = sleep;
    }

    public void setTheory(Theory theory) {
        this.theory = theory;
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
            ", userId=" + (user != null ? user.getId() : null) +
            ", analysisId=" + (analysis != null ? analysis.getId() : null) +
            ", sleepId=" + (sleep != null ? sleep.getSleepId() : null) +
            ", theoryId=" + (theory != null ? theory.getTheoryId() : null) +
            ", visitor='" + visitor +
            ", plot='" + plot +
            ", location='" + location +
            ", mood='" + mood +
            ", additionalInfo='" + additionalInfo +
            '}';
    }
}