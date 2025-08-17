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
    public CallDreamDTO createDream(String auth0Id, SubmitDreamDTO submitDreamDTO){

        //Translate DTO to entity
        Dream createdDream = dreamEntityMapper.mapToDreamEntity(submitDreamDTO);

        //Look up user based on auth0Id
        User user = userRepository.findByAuth0Id(auth0Id).orElseThrow(() -> new EmptyResultDataAccessException("User with ID " + auth0Id + " not found", 1));

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
    public CallDreamDTO getDreamById(String auth0Id, Integer dreamId){
        Dream dream = dreamRepository.findById(dreamId).orElseThrow(() -> new EmptyResultDataAccessException("Dream with ID " + dreamId + " not found", 1));
        String dreamUserAuthId = dream.getUser().getAuth0Id();
        String requestUserAuthId = auth0Id;

        if(! dreamUserAuthId.equals(requestUserAuthId)){
            throw new RuntimeException("Action not permitted");
        }

        return dreamDTOMapper.mapToDreamDTO(dream);
    }

    //Get all dreams for a user by userID
    public List<CallDreamDTO> getAllDreamsByUserId(String auth0Id){
        
        //Look up user based on auth0Id
        User user = userRepository.findByAuth0Id(auth0Id).orElseThrow(() -> new EmptyResultDataAccessException("User with ID " + auth0Id + " not found", 1));
        
        List<Dream> foundDreams = dreamRepository.findByUserId(user.getUserId());
        return foundDreams.stream()
            .map(dreamDTOMapper::mapToDreamDTO)
            .toList();
    }

    //Delete Dream by ID
    public void deleteDreamById(String auth0Id, Integer dreamId){
        //check if dream is associated with the user that put the request
        Dream dream = dreamRepository.findById(dreamId).orElseThrow(() -> new EmptyResultDataAccessException("Dream with ID " + dreamId + " not found", 1));
        String dreamUserAuthId = dream.getUser().getAuth0Id();
        String requestUserAuthId = auth0Id;

        if(! dreamUserAuthId.equals(requestUserAuthId)){
            throw new RuntimeException("Dream with ID " + dreamId + " cannot be deleted. Missing permission or missing dream");
        }

        try {
            dreamRepository.deleteById(dreamId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete dream with ID " + dreamId, e);
        }
    }
}