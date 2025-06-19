package com.dreamcatcher.mobile.service;

import com.dreamcatcher.mobile.entity.Dream;
import com.dreamcatcher.mobile.entity.Sleep;
import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.repository.DreamRepository;

public class DreamManagementService {

    private DreamRepository dreamRepository;

    DreamManagementService dreamManagementService(DreamRepository dreamRepository){
        this.dreamRepository = dreamRepository;
    }

    //Create Dream Method
    public User createDream(User user, String visitor, String plot, String location, String mood, String additionalInfo, String sleepQuality, String sleepLength){

        //Create dream
        Dream dream = new Dream(User user, String visitor, String plot, String location, String mood, String sleepQuality, String additionalInfo)

        //save dream to db
        dreamRepository.save(dream);
    }

    //Get all dreams for a user by userID

    //Get Dream by ID

    //Delete Dream by ID
}