package com.dreamcatcher.mobile.service;

import com.dreamcatcher.mobile.dto.UserAuthDTO;
import com.dreamcatcher.mobile.dto.UserProfileDTO;
import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.repository.UserRepository;
import com.dreamcatcher.mobile.utilities.ReflectionUpdater;
import com.dreamcatcher.mobile.utilities.UserDTOMapper;
import com.dreamcatcher.mobile.utilities.UserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManagementService {

    private UserRepository userRepository;
    private UserEntityMapper userEntityMapper;
    private UserDTOMapper userDTOMapper;
    private BCryptPasswordEncoder passwordEncoder;
    private ReflectionUpdater reflectionUpdater;

    @Autowired
    public UserManagementService(UserRepository userRepository, UserEntityMapper userEntityMapper,
                                 UserDTOMapper userDTOMapper, BCryptPasswordEncoder passwordEncoder, ReflectionUpdater reflectionUpdater) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
        this.userDTOMapper = userDTOMapper;
        this.passwordEncoder = passwordEncoder;
        this.reflectionUpdater = reflectionUpdater;
    }

    //Method to save user to the database
    public User createUser(UserAuthDTO userAuthDTO) {
        //Check if mail already exists in db
        if (userRepository.existsByEmail(userAuthDTO.email())) {
            throw new IllegalArgumentException("The createUser method in the UserService class failed because the email exists already.");
        }

        User user = userEntityMapper.mapToUserAuthEntity(userAuthDTO);

        //Create hashed password
        String hashedPassword = passwordEncoder.encode(userAuthDTO.password());
        user.setPassword(hashedPassword);

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("The createUser method in the UserService class threw an exception.");
        }
    }

    //Method to provide user information if user visits user profile
    public UserProfileDTO getUser(Integer userId) {
        try {
            User user = userRepository.findById(userId).orElseThrow();
            UserProfileDTO userProfileDTO = userDTOMapper.mapToUserProfileDTO(user);
            return userProfileDTO;
        } catch (Exception e) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }
    }

    //Method to change profile data
    public User modifyProfile(Integer userId, UserProfileDTO userProfileDTO) {
        try {
            //Retrieving user by id from repo and converting dto user to entity user
            User currentUser = userRepository.findById(userId).orElseThrow();
            User submittedUser = userEntityMapper.mapToUserProfileEntity(userProfileDTO);

            //Apply change to current user and check if change was made
            boolean userAppliedChange = reflectionUpdater.updateFields(currentUser, submittedUser);

            if (userAppliedChange) {
                return userRepository.save(currentUser);
            } else {
                return currentUser;
            }
        } catch (Exception e) {
            throw new RuntimeException("There was a problem saving the profile data using modifyProfile().");
        }
    }

    //Method to change user auth data
    public User modifyAuth(Integer userId, UserAuthDTO userAuthDTO) {
        try {
            //Retrieving user by id from repo and converting dto user to entity user
            User currentUser = userRepository.findById(userId).orElseThrow();
            User submittedUser = userEntityMapper.mapToUserAuthEntity(userAuthDTO);

            //Apply change to current user and check if change was made
            boolean userAppliedChange = reflectionUpdater.updateFields(currentUser, submittedUser);

            if (userAppliedChange) {
                return userRepository.save(currentUser);
            } else {
                return currentUser;
            }
        } catch (Exception e) {
            throw new RuntimeException("There was a problem saving the profile data using modifyAuth().");
        }
    }

    //Method to delete User
    public void deleteUser(Integer userId){
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new RuntimeException("User with id " + userId + " could not be deleted");
        }
    }
}