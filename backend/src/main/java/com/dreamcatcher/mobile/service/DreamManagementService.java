package com.dreamcatcher.mobile.service;

import com.dreamcatcher.mobile.dto.DreamDTO;
import com.dreamcatcher.mobile.entity.Dream;
import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.mapper.DreamEntityMapper;
import com.dreamcatcher.mobile.repository.DreamRepository;
import com.dreamcatcher.mobile.repository.UserRepository;

public class DreamManagementService {

    private DreamRepository dreamRepository;
    private DreamEntityMapper dreamEntityMapper;
    private UserRepository userRepository;

    public DreamManagementService(DreamRepository dreamRepository, UserRepository userRepository, DreamEntityMapper dreamEntityMapper){
        this.dreamRepository = dreamRepository;
        this.dreamEntityMapper = dreamEntityMapper;
        this.userRepository = userRepository;
    }

    //Create Dream Method
    public Dream createDream(Integer userId, DreamDTO dreamDTO){

        //Translate DTO to entity
        User user = userRepository.findById(userId).orElseThrow();
        Dream dream = dreamEntityMapper.mapToDreamEntity(user, dreamDTO);

        //save dream to db
        return dreamRepository.save(dream);
    }

    //Get all dreams for a user by userID

    //Get Dream by ID

    //Delete Dream by ID
}