package com.dreamcatcher.mobile.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class DreamAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dreamAnalysisId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private String dreamTitle;
    private String dreamTheme;
    private String interpretation;
    private String implications;

    // Constructor
    public DreamAnalysis() {}

    public DreamAnalysis(Integer dreamAnalysisId, User user, String dreamTitle, String dreamTheme, String interpretation, String implications) {
        this.dreamAnalysisId = dreamAnalysisId;
        this.user = user;
        this.dreamTitle = dreamTitle;
        this.dreamTheme = dreamTheme;
        this.interpretation = interpretation;
        this.implications = implications;
    }

    // Getters
    public Integer getDreamAnalysisId() {
        return dreamAnalysisId;
    }

    public User getUser() {
        return user;
    }

    public String getDreamTitle() {
        return dreamTitle;
    }

    public String getDreamTheme() {
        return dreamTheme;
    }

    public String getInterpretation() {
        return interpretation;
    }

    public String getImplications() {
        return implications;
    }

    // Setters
    public void setDreamAnalysisId(Integer dreamAnalysisId) {
        this.dreamAnalysisId = dreamAnalysisId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDreamTitle(String dreamTitle) {
        this.dreamTitle = dreamTitle;
    }

    public void setDreamTheme(String dreamTheme) {
        this.dreamTheme = dreamTheme;
    }

    public void setInterpretation(String interpretation) {
        this.interpretation = interpretation;
    }

    public void setImplications(String implications) {
        this.implications = implications;
    }

    @Override
    public String toString() {
        return "DreamAnalysis{" +
                "DreamAnalysisId=" + dreamAnalysisId +
                ", user=" + user +
                ", dreamTitle=" + dreamTitle +
                ", dreamTheme=" + dreamTheme +
                ", interpretation=" + interpretation +
                ", implications=" + implications +
                "}";
    }
}
