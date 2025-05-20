package com.dreamcatcher.mobile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Theory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer theoryId;

    private String theoryName;
    private String theoryDescription;

    // Constructor
    public Theory() {}

    public Theory(Integer theoryId, String theoryName, String theoryDescription) {
        this.theoryId = theoryId;
        this.theoryName = theoryName;
        this.theoryDescription = theoryDescription;
    }

    // Getters
    public Integer getTheoryId() {
        return theoryId;
    }

    public String getTheoryName() {
        return theoryName;
    }

    public String getTheoryDescription() {
        return theoryDescription;
    }

    // Setters
    public void setTheoryId(Integer theoryId) {
        this.theoryId = theoryId;
    }

    public void setTheoryName(String theoryName) {
        this.theoryName = theoryName;
    }

    public void setTheoryDescription(String theoryDescription) {
        this.theoryDescription = theoryDescription;
    }

    @Override
    public String toString() {
        return "Theory{" +
                "theoryId=" + theoryId +
                ", theoryName=" + theoryName +
                ", theoryDescription=" + theoryDescription +
                "}";
    }
}
