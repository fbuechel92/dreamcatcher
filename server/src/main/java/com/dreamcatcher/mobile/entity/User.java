package com.dreamcatcher.mobile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String auth0Id;

    private String email;
    private String name;
    private String gender;
    private LocalDate birthdate;
    private String country;
    private String occupation;

    @CreationTimestamp 
    private LocalDateTime createdAt;

    @UpdateTimestamp 
    private LocalDateTime updatedAt;

    //Constructors
    public User(String name, String gender, LocalDate birthdate, String country, String occupation){
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.country = country;
        this.occupation = occupation;
    };

    public User(String auth0Id, String email){
        this.auth0Id = auth0Id;
        this.email = email;
    };

    public User(){};

    //Getter
    public Integer getUserId(){
        return userId;
    }

    public String getAuth0Id(){
        return auth0Id;
    }

    public String getEmail(){
        return email;
    }

    public String getName(){
        return name;
    }

    public String getGender(){
        return gender;
    }

    public LocalDate getBirthdate(){
        return birthdate;
    }

    public String getCountry(){
        return country;
    }

    public String getOccupation(){
        return occupation;
    }
    //Setter
    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public void setAuth0Id(String auth0Id){
        this.auth0Id = auth0Id;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setBirthdate(LocalDate birthdate){
        this.birthdate = birthdate;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public void setOccupation(String occupation){
        this.occupation = occupation;
    }

    @Override
    public String toString(){
        return "User{" +
            "userId=" + userId +
            ", auth0Id=" + auth0Id +
            ", email=" + email +
            ", name=" + name +
            ", gender=" + gender +
            ", birthdate=" + birthdate +
            ", country=" + country +
            ", occupation=" + occupation +
            "}";
    }
}