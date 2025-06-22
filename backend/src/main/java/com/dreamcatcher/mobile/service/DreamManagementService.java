package com.dreamcatcher.mobile.service;

import com.dreamcatcher.mobile.dto.DreamDTO;
import com.dreamcatcher.mobile.entity.Dream;
import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.mapper.DreamEntityMapper;
import com.dreamcatcher.mobile.repository.DreamRepository;
import com.dreamcatcher.mobile.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

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
        User user = userRepository.findById(userId).orElseThrow(() -> new EmptyResultDataAccessException("User with ID " + userId + " not found", 1));
        Dream dream = dreamEntityMapper.mapToDreamEntity(user, dreamDTO);

        //save dream to db
        try {
            return dreamRepository.save(dream);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error occurred while saving the dream", e);
        }
    }

    //Get all dreams for a user by userID
    public List<Dream> getAllDreamsByUserId(Integer userId){
        return dreamRepository.findByUserId(userId);
    }

    //Get Dream by ID
    public Dream getDreamById(Integer dreamId){
        return dreamRepository.findById(dreamId).orElseThrow(() -> new EmptyResultDataAccessException("Dream with ID " + dreamId + " not found", 1));
    }

    //Delete Dream by ID
    public void deleteDreamById(Integer dreamId){
        dreamRepository.deleteById(dreamId);
    }
}