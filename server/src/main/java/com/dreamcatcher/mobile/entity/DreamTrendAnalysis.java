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
public class DreamTrendAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dreamTrendAnalysisId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private String trend;
    private String interpretation;
    private String implications;

    @CreationTimestamp 
    private LocalDateTime createdAt;

    @UpdateTimestamp 
    private LocalDateTime updatedAt;

    // Constructor
    public DreamTrendAnalysis() {}

    public DreamTrendAnalysis(Integer dreamTrendAnalysisId, User user, String trend, String interpretation, String implications) {
        this.dreamTrendAnalysisId = dreamTrendAnalysisId;
        this.user = user;
        this.trend = trend;
        this.interpretation = interpretation;
        this.implications = implications;
    }

    // Getters
    public Integer getDreamTrendAnalysisId() {
        return dreamTrendAnalysisId;
    }

    public User getUser() {
        return user;
    }

    public String getTrend() {
        return trend;
    }

    public String getInterpretation() {
        return interpretation;
    }

    public String getImplications() {
        return implications;
    }

    // Setters
    public void setDreamTrendAnalysisId(Integer dreamTrendAnalysisId) {
        this.dreamTrendAnalysisId = dreamTrendAnalysisId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public void setInterpretation(String interpretation) {
        this.interpretation = interpretation;
    }

    public void setImplications(String implications) {
        this.implications = implications;
    }

    @Override
    public String toString() {
        return "DreamTrendAnalysis{" +
                "dreamTrendAnalysisId=" + dreamTrendAnalysisId +
                ", user=" + user +
                ", trend=" + trend +
                ", interpretation=" + interpretation +
                ", implications=" + implications +
                "}";
    }
}