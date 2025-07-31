package com.dreamcatcher.mobile.service;

import com.dreamcatcher.mobile.dto.SubmitDreamDTO;
import com.dreamcatcher.mobile.dto.CallDreamDTO;
import com.dreamcatcher.mobile.entity.Dream;
import com.dreamcatcher.mobile.mapper.DreamEntityMapper;
import com.dreamcatcher.mobile.mapper.DreamDTOMapper;
import com.dreamcatcher.mobile.repository.DreamRepository;
import com.dreamcatcher.mobile.repository.UserRepository;
import com.dreamcatcher.mobile.entity.User;

import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class DreamManagementService {

    private DreamRepository dreamRepository;
    private UserRepository userRepository;
    private DreamEntityMapper dreamEntityMapper;
    private DreamDTOMapper dreamDTOMapper;


    public DreamManagementService(DreamRepository dreamRepository, UserRepository userRepository, DreamDTOMapper dreamDTOMapper, DreamEntityMapper dreamEntityMapper){
        this.dreamRepository = dreamRepository;
        this.userRepository = userRepository;
        this.dreamEntityMapper = dreamEntityMapper;
        this.dreamDTOMapper = dreamDTOMapper;
    }

    //Create Dream Method
    public CallDreamDTO createDream(Integer userId, SubmitDreamDTO submitDreamDTO){

        //Translate DTO to entity
        Dream createdDream = dreamEntityMapper.mapToDreamEntity(submitDreamDTO);

        //Get user from database
        User user = userRepository.findById(userId).orElseThrow(() -> new EmptyResultDataAccessException("User with ID " + userId + " not found", 1));

        //we need this because we require the FK from user
        createdDream.setUser(user);

        //save dream to db
        try {
            Dream savedDream = dreamRepository.save(createdDream);
            return dreamDTOMapper.mapToDreamDTO(savedDream);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error occurred while saving the dream", e);
        }
    }

    //Get Dream by ID
    public CallDreamDTO getDreamById(Integer dreamId){
        Dream foundDream = dreamRepository.findById(dreamId).orElseThrow(() -> new EmptyResultDataAccessException("Dream with ID " + dreamId + " not found", 1));
        return dreamDTOMapper.mapToDreamDTO(foundDream);
    }

    //Get all dreams for a user by userID
    public List<CallDreamDTO> getAllDreamsByUserId(Integer userId){
        List<Dream> foundDreams = dreamRepository.findByUserId(userId);
        return foundDreams.stream()
            .map(dreamDTOMapper::mapToDreamDTO)
            .toList();
    }

    //Delete Dream by ID
    public void deleteDreamById(Integer dreamId){
        if (!dreamRepository.existsById(dreamId)) {
            throw new RuntimeException("Dream with ID " + dreamId + " does not exist.");
        }

        try {
            dreamRepository.deleteById(dreamId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete dream with ID " + dreamId, e);
        }
    }
}