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

    //Method to save user to the database
    public UserAuthDTO createUser(UserAuthDTO userAuthDTO) {
        //Check if mail already exists in db
        if (userRepository.existsByEmail(userAuthDTO.email())) {
            throw new IllegalArgumentException("The createUser method in the UserService class failed because the email exists already.");
        }

        User createdUser = userEntityMapper.mapToUserAuthEntity(userAuthDTO);

        try {
            User savedUser = userRepository.save(createdUser);
            return userDTOMapper.mapToUserAuthDTO(savedUser);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error occurred while saving the user", e);
        }
    }

    //Method to provide user information if user visits user profile
    public UserProfileDTO getUser(Integer userId) {

        //Retrieve user from db
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new EmptyResultDataAccessException("User with ID " + userId + " not found", 1));

        return userDTOMapper.mapToUserProfileDTO(foundUser);
    }

    //Method to change profile data
    public UserProfileDTO modifyProfile(Integer userId, UserProfileDTO userProfileDTO) {

        //Retrieving user by id from repo and converting dto user to entity user
        User currentUser = userRepository.findById(userId).orElseThrow(() -> new EmptyResultDataAccessException("User with ID " + userId + " not found", 1));

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

    //Method to change user auth data
    public UserAuthDTO modifyAuth(Integer userId, UserAuthDTO userAuthDTO) {

        //Retrieving user by id from repo and converting dto user to entity user
        User currentUser = userRepository.findById(userId).orElseThrow(() -> new EmptyResultDataAccessException("User with ID " + userId + " not found", 1));

        User submittedUser = userEntityMapper.mapToUserAuthEntity(userAuthDTO);

        //Apply change to current user and check if change was made
        boolean userAppliedChange = userUpdater.updateAuthFields(currentUser, submittedUser);

        if (userAppliedChange) {
            try {
                User savedUser = userRepository.save(currentUser);
                return userDTOMapper.mapToUserAuthDTO(savedUser);
            } catch (DataAccessException e) {
                throw new RuntimeException("Database error occurred while saving the user", e);
            }
        } else {
            return userDTOMapper.mapToUserAuthDTO(currentUser);
        }
    }

    //Method to delete User
    public void deleteUser(Integer userId){
        try {
            userRepository.deleteById(userId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error occurred while deleting the user", e);
        }
    }
}