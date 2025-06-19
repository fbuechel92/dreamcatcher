package com.dreamcatcher.mobile.service;

import com.dreamcatcher.mobile.dto.DreamDTO;
import com.dreamcatcher.mobile.entity.Dream;
import com.dreamcatcher.mobile.mapper.DreamEntityMapper;
import com.dreamcatcher.mobile.repository.DreamRepository;

public class DreamManagementService {

    private DreamRepository dreamRepository;
    private DreamEntityMapper dreamEntityMapper;

    public DreamManagementService(DreamRepository dreamRepository, DreamEntityMapper dreamEntityMapper){
        this.dreamRepository = dreamRepository;
        this.dreamEntityMapper = dreamEntityMapper;
    }

    //Create Dream Method
    public Dream createDream(DreamDTO dreamDTO){

        DreamEntityMapper dreamEntityMapper;

        //Translate DTO to entity
        Dream dream = DreamEntityMapper.mapToDreamEntity(dreamDTO);

        //save dream to db
        return dreamRepository.save(dream);
    }

    //Get all dreams for a user by userID

    //Get Dream by ID

    //Delete Dream by ID
}