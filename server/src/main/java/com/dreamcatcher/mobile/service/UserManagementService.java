package com.dreamcatcher.mobile.service;

import com.dreamcatcher.mobile.dto.UserAuthDTO;
import com.dreamcatcher.mobile.dto.UserProfileDTO;
import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.repository.UserRepository;
import com.dreamcatcher.mobile.mapper.UserDTOMapper;
import com.dreamcatcher.mobile.mapper.UserEntityMapper;
import com.dreamcatcher.mobile.utilities.UserUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserManagementService {

    private UserRepository userRepository;
    private UserEntityMapper userEntityMapper;
    private UserDTOMapper userDTOMapper;
    private UserUpdater userUpdater;

    @Autowired
    public UserManagementService(UserRepository userRepository, UserEntityMapper userEntityMapper,
                                 UserDTOMapper userDTOMapper, UserUpdater userUpdater) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
        this.userDTOMapper = userDTOMapper;
        this.userUpdater = userUpdater;
    }

    //Method to save auth info
    @Transactional
    public UserAuthDTO checkAndCreateUser(String auth0Id, String email) {
        // Acquire a lock on the user record
        Optional<User> existingUser = userRepository.findByAuth0IdWithLock(auth0Id);
        if (existingUser.isPresent()) {
            return userDTOMapper.mapToUserAuthDTO(existingUser.get());
        }

        // If the user does not exist, create it
        UserAuthDTO userAuthDTO = new UserAuthDTO(auth0Id, email);
        User createdAuth = userEntityMapper.mapToUserAuthEntity(userAuthDTO);

        User savedAuth = userRepository.save(createdAuth);
        return userDTOMapper.mapToUserAuthDTO(savedAuth);
    }

    //Method get auth info
    public UserAuthDTO getAuth(String auth0Id) {
        
        //Retrieve user from db using auth0Id
        User foundUser = userRepository.findByAuth0Id(auth0Id).orElseThrow(() -> new EmptyResultDataAccessException("User with ID " + auth0Id + " not found", 1));

        return userDTOMapper.mapToUserAuthDTO(foundUser);
    }

    //Method to get profile info
    public UserProfileDTO getProfile(String auth0Id) {

        //Retrieve user from db using auth0Id
        User foundUser = userRepository.findByAuth0Id(auth0Id).orElseThrow(() -> new EmptyResultDataAccessException("User with ID " + auth0Id + " not found", 1));

        return userDTOMapper.mapToUserProfileDTO(foundUser);
    }

    //Method to change profile info
    public UserProfileDTO modifyProfile(String auth0Id, UserProfileDTO userProfileDTO) {

        //Retrieving user by auth0Id from repo and converting dto user to entity user
        User currentUser = userRepository.findByAuth0Id(auth0Id).orElseThrow(() -> new EmptyResultDataAccessException("User with ID " + auth0Id + " not found", 1));

        User submittedUser = userEntityMapper.mapToUserProfileEntity(userProfileDTO);

        //Apply change to current user and check if change was made
        boolean userAppliedChange = userUpdater.updateProfileFields(currentUser, submittedUser);

        if (userAppliedChange) {
            try {
                User savedUser = userRepository.save(currentUser);
                return userDTOMapper.mapToUserProfileDTO(savedUser);
            } catch (DataAccessException e) {
                throw new RuntimeException("Database error occurred while saving the user", e);
            }
        } else {
            return userDTOMapper.mapToUserProfileDTO(currentUser);
        }
    }

    //Method to delete User
    public void deleteUser(String auth0Id){
        
        //retrieve userId by auth0Id
        User user = userRepository.findByAuth0Id(auth0Id).orElseThrow(() -> new EmptyResultDataAccessException("User with ID " + auth0Id + " not found", 1));
        int foundUser = user.getUserId();

        try {
            userRepository.deleteById(foundUser);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error occurred while deleting the user", e);
        }
    }
}