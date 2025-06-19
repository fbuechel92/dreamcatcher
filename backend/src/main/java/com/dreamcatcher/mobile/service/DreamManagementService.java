package com.dreamcatcher.mobile.service;

import com.dreamcatcher.mobile.entity.Dream;
import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.repository.DreamRepository;

public class DreamManagementService {

    private DreamRepository dreamRepository;

    public DreamManagementService(DreamRepository dreamRepository){
        this.dreamRepository = dreamRepository;
    }

    //Create Dream Method
    public User createDream(User user, String visitor, String plot, String location, String mood, String sleepQuality, String additionalInfo){

        //Create dream
        Dream dream = new Dream(user, visitor, plot, location, mood, sleepQuality, additionalInfo);


        //save dream to db
        dreamRepository.save(dream);
    }

    //Get all dreams for a user by userID

    //Get Dream by ID

    //Delete Dream by ID
}