package com.dreamcatcher.mobile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String email;
    private String password;
    private String name;
    private String gender;
    private Date birthdate;
    private String country;
    private String occupation;

    //Constructors
    public User(String email, String name, String gender, Date birthdate, String country, String occupation){
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.country = country;
        this.occupation = occupation;
    };

    public User(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    };

    public User(){};

    //Getter
    public Integer getUserId(){
        return userId;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getName(){
        return name;
    }

    public String getGender(){
        return gender;
    }

    public Date getBirthdate(){
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

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setBirthdate(Date birthdate){
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
            ", email=" + email +
            ", password=" + password +
            ", name=" + name +
            ", gender=" + gender +
            ", birthdate=" + birthdate +
            ", country=" + country +
            ", occupation=" + occupation +
            "}";
    }
}