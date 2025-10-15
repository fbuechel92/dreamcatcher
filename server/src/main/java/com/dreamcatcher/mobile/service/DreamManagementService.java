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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class DreamManagementService {

    private DreamRepository dreamRepository;
    private UserRepository userRepository;
    private DreamEntityMapper dreamEntityMapper;
    private DreamDTOMapper dreamDTOMapper;
    
    @Autowired
    private SimpleEncryptionService encryptionService;


    public DreamManagementService(DreamRepository dreamRepository, UserRepository userRepository, DreamDTOMapper dreamDTOMapper, DreamEntityMapper dreamEntityMapper){
        this.dreamRepository = dreamRepository;
        this.userRepository = userRepository;
        this.dreamEntityMapper = dreamEntityMapper;
        this.dreamDTOMapper = dreamDTOMapper;
    }

    //Create Dream Method
    public CallDreamDTO createDream(String auth0Id, SubmitDreamDTO submitDreamDTO){
        try {
            //Translate DTO to entity
            Dream createdDream = dreamEntityMapper.mapToDreamEntity(submitDreamDTO);

            // ENCRYPT SENSITIVE FIELDS BEFORE SAVING
            createdDream.setVisitor(encryptionService.encrypt(createdDream.getVisitor()));
            createdDream.setPlot(encryptionService.encrypt(createdDream.getPlot()));
            createdDream.setLocation(encryptionService.encrypt(createdDream.getLocation()));
            createdDream.setAdditionalInfo(encryptionService.encrypt(createdDream.getAdditionalInfo()));

            //Look up user based on auth0Id
            User user = userRepository.findByAuth0Id(auth0Id).orElseThrow(() -> new EmptyResultDataAccessException("User with ID " + auth0Id + " not found", 1));

            //we need this because we require the FK from user
            createdDream.setUser(user);

            //save dream to db (encrypted data)
            Dream savedDream = dreamRepository.save(createdDream);
            
            // DECRYPT FOR RESPONSE TO USER
            savedDream.setVisitor(encryptionService.decrypt(savedDream.getVisitor()));
            savedDream.setPlot(encryptionService.decrypt(savedDream.getPlot()));
            savedDream.setLocation(encryptionService.decrypt(savedDream.getLocation()));
            savedDream.setAdditionalInfo(encryptionService.decrypt(savedDream.getAdditionalInfo()));
            
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
            throw new RuntimeException("Action not permitted.");
        }

        // DECRYPT BEFORE RETURNING
        dream.setVisitor(encryptionService.decrypt(dream.getVisitor()));
        dream.setPlot(encryptionService.decrypt(dream.getPlot()));
        dream.setLocation(encryptionService.decrypt(dream.getLocation()));
        dream.setAdditionalInfo(encryptionService.decrypt(dream.getAdditionalInfo()));

        return dreamDTOMapper.mapToDreamDTO(dream);
    }

    //Get all dreams for a user by userID
    public List<CallDreamDTO> getAllDreamsByUserId(String auth0Id){
        
        //Look up user based on auth0Id
        User user = userRepository.findByAuth0Id(auth0Id).orElseThrow(() -> new EmptyResultDataAccessException("User with ID " + auth0Id + " not found", 1));
        
        List<Dream> foundDreams = dreamRepository.findByUserId(user.getUserId());
        
        // DECRYPT ALL DREAMS BEFORE RETURNING
        foundDreams.forEach(dream -> {
            dream.setVisitor(encryptionService.decrypt(dream.getVisitor()));
            dream.setPlot(encryptionService.decrypt(dream.getPlot()));
            dream.setLocation(encryptionService.decrypt(dream.getLocation()));
            dream.setAdditionalInfo(encryptionService.decrypt(dream.getAdditionalInfo()));
        });
        
        return foundDreams.stream()
            .map(dreamDTOMapper::mapToDreamDTO)
            .toList();
    }

    //Delete Dream by ID
    public void deleteDreamById(String auth0Id, Integer dreamId){
        try {
            //check if dream is associated with the user that put the request
            Dream dream = dreamRepository.findById(dreamId).orElseThrow(() -> new EmptyResultDataAccessException("Dream with ID " + dreamId + " not found", 1));
            String dreamUserAuthId = dream.getUser().getAuth0Id();
            String requestUserAuthId = auth0Id;

            if(! dreamUserAuthId.equals(requestUserAuthId)){
                throw new RuntimeException("Dream with ID " + dreamId + " cannot be deleted. User does not have permission or dream does not exist.");
            }

            dreamRepository.deleteById(dreamId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete dream with ID " + dreamId, e);
        }
    }
}