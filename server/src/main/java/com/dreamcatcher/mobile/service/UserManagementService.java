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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManagementService {

    private UserRepository userRepository;
    private UserEntityMapper userEntityMapper;
    private UserDTOMapper userDTOMapper;
    private BCryptPasswordEncoder passwordEncoder;
    private UserUpdater userUpdater;

    @Autowired
    public UserManagementService(UserRepository userRepository, UserEntityMapper userEntityMapper,
                                 UserDTOMapper userDTOMapper, BCryptPasswordEncoder passwordEncoder, UserUpdater userUpdater) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
        this.userDTOMapper = userDTOMapper;
        this.passwordEncoder = passwordEncoder;
        this.userUpdater = userUpdater;
    }

    //Method to save user to the database
    public UserAuthDTO createUser(UserAuthDTO userAuthDTO) {
        //Check if mail already exists in db
        if (userRepository.existsByEmail(userAuthDTO.email())) {
            throw new IllegalArgumentException("The createUser method in the UserService class failed because the email exists already.");
        }

        User user = userEntityMapper.mapToUserAuthEntity(userAuthDTO);

        //Create hashed password
        String hashedPassword = passwordEncoder.encode(userAuthDTO.password());
        user.setPassword(hashedPassword);

        try {
            User savedUser = userRepository.save(user);
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
    public User modifyProfile(Integer userId, UserProfileDTO userProfileDTO) {

        //Retrieving user by id from repo and converting dto user to entity user
        User currentUser = userRepository.findById(userId).orElseThrow(() -> new EmptyResultDataAccessException("User with ID " + userId + " not found", 1));

        User submittedUser = userEntityMapper.mapToUserProfileEntity(userProfileDTO);

        //Apply change to current user and check if change was made
        boolean userAppliedChange = userUpdater.updateProfileFields(currentUser, submittedUser);

        if (userAppliedChange) {
            try {
                return userRepository.save(currentUser);
            } catch (DataAccessException e) {
                throw new RuntimeException("Database error occurred while saving the user", e);
            }
        } else {
            return currentUser;
        }
    }

    //Method to change user auth data
    public User modifyAuth(Integer userId, UserAuthDTO userAuthDTO) {

        //Retrieving user by id from repo and converting dto user to entity user
        User currentUser = userRepository.findById(userId).orElseThrow(() -> new EmptyResultDataAccessException("User with ID " + userId + " not found", 1));

        User submittedUser = userEntityMapper.mapToUserAuthEntity(userAuthDTO);

        //Apply change to current user and check if change was made
        boolean userAppliedChange = userUpdater.updateAuthFields(currentUser, submittedUser);

        if (userAppliedChange) {
            try {
                return userRepository.save(currentUser);
            } catch (DataAccessException e) {
                throw new RuntimeException("Database error occurred while saving the user", e);
            }
        } else {
            return currentUser;
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