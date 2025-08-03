package com.dreamcatcher.mobile.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.EnumType;

import com.dreamcatcher.mobile.enums.Mood;
import com.dreamcatcher.mobile.enums.SleepQuality;

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

    @Enumerated(EnumType.STRING)
    private Mood mood;

    @Enumerated(EnumType.STRING)
    private SleepQuality sleepQuality;

    private String additionalInfo;

    @CreationTimestamp 
    private LocalDateTime createdAt;

    @UpdateTimestamp 
    private LocalDateTime updatedAt;

    //Constructor
    public Dream(){};

    public Dream(String visitor, String plot, String location, Mood mood, SleepQuality sleepQuality, String additionalInfo, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.visitor = visitor;
        this.plot = plot;
        this.location = location;
        this.mood = mood;
        this.sleepQuality = sleepQuality;
        this.additionalInfo = additionalInfo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Dream(String visitor, String plot, String location, Mood mood, SleepQuality sleepQuality, String additionalInfo){
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

    public Mood getMood(){
        return mood;
    }

    public SleepQuality getSleepQuality(){
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

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public void setSleepQuality(SleepQuality sleepQuality) {
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
            ", visitor=" + visitor +
            ", plot=" + plot +
            ", location=" + location +
            ", mood=" + mood +
            ", sleepQuality=" + sleepQuality +
            ", additionalInfo=" + additionalInfo +
            ", additionalInfo=" + createdAt +
            ", additionalInfo=" + updatedAt +
            "}";
    }
}