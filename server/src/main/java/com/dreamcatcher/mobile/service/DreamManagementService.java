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
    private EncryptionService encryptionService;
    private DreamAnalysisService dreamAnalysisService;

    public DreamManagementService(DreamRepository dreamRepository, UserRepository userRepository, DreamDTOMapper dreamDTOMapper, DreamEntityMapper dreamEntityMapper, EncryptionService encryptionService, DreamAnalysisService dreamAnalysisService){
        this.dreamRepository = dreamRepository;
        this.userRepository = userRepository;
        this.dreamEntityMapper = dreamEntityMapper;
        this.dreamDTOMapper = dreamDTOMapper;
        this.encryptionService = encryptionService;
        this.dreamAnalysisService = dreamAnalysisService;
    }

    //Create Dream Method
    public CallDreamDTO createDream(String auth0Id, SubmitDreamDTO submitDreamDTO){

        //Translate DTO to entity
        Dream createdDream = dreamEntityMapper.mapToDreamEntity(submitDreamDTO);

        // Enrcypt fields before saving
        createdDream.setVisitor(encryptionService.encrypt(createdDream.getVisitor()));
        createdDream.setPlot(encryptionService.encrypt(createdDream.getPlot()));
        createdDream.setLocation(encryptionService.encrypt(createdDream.getLocation()));
        createdDream.setMood(encryptionService.encrypt(createdDream.getMood()));
        createdDream.setSleepQuality(encryptionService.encrypt(createdDream.getSleepQuality()));
        createdDream.setAdditionalInfo(encryptionService.encrypt(createdDream.getAdditionalInfo()));

        //Look up user based on auth0Id
        User user = userRepository.findByAuth0Id(auth0Id).orElseThrow(() -> new EmptyResultDataAccessException("User with ID " + auth0Id + " not found", 1));

        //we need this because we require the FK from user
        createdDream.setUser(user);

        //save dream to db
        try {
            Dream savedDream = dreamRepository.save(createdDream);

            // Trigger async analysis (fire and forget - don't wait for result)
            dreamAnalysisService.analyzeDreamAsync(savedDream, user)
                .exceptionally(ex -> {
                    System.err.println("Dream analysis failed for dream ID " + savedDream.getDreamId() + ": " + ex.getMessage());
                    return null;
                });

            //Make sure to return decrypted values
            savedDream.setVisitor(encryptionService.decrypt(savedDream.getVisitor()));
            savedDream.setPlot(encryptionService.decrypt(savedDream.getPlot()));
            savedDream.setLocation(encryptionService.decrypt(savedDream.getLocation()));
            savedDream.setMood(encryptionService.decrypt(savedDream.getMood()));
            savedDream.setSleepQuality(encryptionService.decrypt(savedDream.getSleepQuality()));
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

        //decrypt fields before returning them
        try {
            dream.setVisitor(encryptionService.decrypt(dream.getVisitor()));
            dream.setPlot(encryptionService.decrypt(dream.getPlot()));
            dream.setLocation(encryptionService.decrypt(dream.getLocation()));
            dream.setMood(encryptionService.decrypt(dream.getMood()));
            dream.setSleepQuality(encryptionService.decrypt(dream.getSleepQuality()));
            dream.setAdditionalInfo(encryptionService.decrypt(dream.getAdditionalInfo()));

            return dreamDTOMapper.mapToDreamDTO(dream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt dream data for dream ID: " + dreamId, e);
        }
    }

    //Get all dreams for a user by userID
    public List<CallDreamDTO> getAllDreamsByUserId(String auth0Id){
        
        //Look up user based on auth0Id
        User user = userRepository.findByAuth0Id(auth0Id).orElseThrow(() -> new EmptyResultDataAccessException("User with ID " + auth0Id + " not found", 1));
        
        List<Dream> foundDreams = dreamRepository.findByUserId(user.getUserId());
        
        //decrypt each dream
        foundDreams.forEach(dream -> {
            try {
                dream.setVisitor(encryptionService.decrypt(dream.getVisitor()));
                dream.setPlot(encryptionService.decrypt(dream.getPlot()));
                dream.setLocation(encryptionService.decrypt(dream.getLocation()));
                dream.setMood(encryptionService.decrypt(dream.getMood()));
                dream.setSleepQuality(encryptionService.decrypt(dream.getSleepQuality()));
                dream.setAdditionalInfo(encryptionService.decrypt(dream.getAdditionalInfo()));
            } catch (Exception e) {
                throw new RuntimeException("Failed to decrypt dream data for dream ID: " + dream.getDreamId(), e);
            }
        });

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
            throw new RuntimeException("Dream with ID " + dreamId + " cannot be deleted. User does not have permission or dream does not exist.");
        }

        try {
            dreamRepository.deleteById(dreamId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete dream with ID " + dreamId, e);
        }
    }
}