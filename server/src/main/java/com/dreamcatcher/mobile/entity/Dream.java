package com.dreamcatcher.mobile.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    private String visitor;
    private String plot;
    private String location;
    private String mood;
    private String sleepQuality;

    private String additionalInfo;

    @CreationTimestamp 
    private LocalDateTime createdAt;

    @UpdateTimestamp 
    private LocalDateTime updatedAt;

    //Constructor
    public Dream(){};

    public Dream(String visitor, String plot, String location, String mood, String sleepQuality, String additionalInfo, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.visitor = visitor;
        this.plot = plot;
        this.location = location;
        this.mood = mood;
        this.sleepQuality = sleepQuality;
        this.additionalInfo = additionalInfo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Dream(String visitor, String plot, String location, String mood, String sleepQuality, String additionalInfo){
        this.visitor = visitor;
        this.plot = plot;
        this.location = location;
        this.mood = mood;
        this.sleepQuality = sleepQuality;
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

    public String getSleepQuality(){
        return sleepQuality;
    }

    public String getAdditionalInfo(){
        return additionalInfo;
    }
    
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public LocalDateTime getUpdatedAt(){
        return updatedAt;
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

    public void setSleepQuality(String sleepQuality) {
        this.sleepQuality = sleepQuality;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public LocalDateTime setCreatedAt(){
        return createdAt;
    }

    public LocalDateTime setUpdatedAt(){
        return updatedAt;
    }

    //toString
    @Override
    public String toString() {
        return "Dream{" +
            "dreamId=" + dreamId +
            ", userId=" + (user != null ? user.getUserId() : null) +
            ", analysisId=" + (dreamAnalysis != null ? dreamAnalysis.getDreamAnalysisId() : null) +
            ", visitor=[ENCRYPTED]" +
            ", plot=[ENCRYPTED]" +
            ", location=[ENCRYPTED]" +
            ", mood=" + mood +
            ", sleepQuality=" + sleepQuality +
            ", additionalInfo=[ENCRYPTED]" +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            "}";
    }
}