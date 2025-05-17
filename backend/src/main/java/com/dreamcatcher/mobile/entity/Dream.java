package com.dreamcatcher.mobile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Dream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String visitor;
    private String plot;
    private String location;
    private String mood;

    //Constructor
    public Dream(){};

    public Dream(Integer id, String name, String visitor, String plot, String location, String mood){
        this.id = id;
        this.name = name;
        this.visitor = visitor;
        this.plot = plot;
        this.location = location;
        this.mood = mood;
    }

    //Getters
    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
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

    //Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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

    //toString
    @Override
    public String toString(){
        return "Dream{" +
            "id=" + id +
            ", name=" + name +
            ", visitor=" + visitor +
            ", plot=" + plot +
            ", location=" + location +
            ", mood=" + mood +
            "}";
    }
}