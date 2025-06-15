package com.dreamcatcher.mobile.service;

import com.dreamcatcher.mobile.dto.UserAuthDTO;
import com.dreamcatcher.mobile.dto.UserProfileDTO;
import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.repository.UserRepository;
import com.dreamcatcher.mobile.utilities.UserDTOMapper;
import com.dreamcatcher.mobile.utilities.UserEntityMapper;
import com.dreamcatcher.mobile.utilities.UserUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManagementService {

    private UserRepository userRepository;
    private UserEntityMapper userEntityMapper;
    private UserDTOMapper userDTOMapper;
    private BCryptPasswordEncoder passwordEncoder;
    private UserUpdater userUpdater;
    private static final Logger logger = LoggerFactory.getLogger(UserManagementService.class);

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
    public User createUser(UserAuthDTO userAuthDTO) {
        logger.info("Creating user with email: {}", userAuthDTO.email());

        //Check if mail already exists in db
        if (userRepository.existsByEmail(userAuthDTO.email())) {
            logger.warn("Email already exists: {}", userAuthDTO.email());
            throw new IllegalArgumentException("The createUser method in the UserService class failed because the email exists already.");
        }

        User user = userEntityMapper.mapToUserAuthEntity(userAuthDTO);

        //Create hashed password
        String hashedPassword = passwordEncoder.encode(userAuthDTO.password());
        user.setPassword(hashedPassword);

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error saving user: {}", e.getMessage(), e);
            throw new RuntimeException("The createUser method in the UserService class threw an exception.");
        }
    }

    //Method to provide user information if user visits user profile
    public UserProfileDTO getUser(Integer userId) {
        logger.info("Fetching user profile for ID: {}", userId);

        try {
            User user = userRepository.findById(userId).orElseThrow();
            UserProfileDTO userProfileDTO = userDTOMapper.mapToUserProfileDTO(user);
            return userProfileDTO;
        } catch (Exception e) {
            logger.error("User not found for ID {}: {}", userId, e.getMessage());
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }
    }

    //Method to change profile data
    public User modifyProfile(Integer userId, UserProfileDTO userProfileDTO) {
        logger.info("Modifying profile for user ID: {}", userId);

        try {
            //Retrieving user by id from repo and converting dto user to entity user
            User currentUser = userRepository.findById(userId).orElseThrow();
            User submittedUser = userEntityMapper.mapToUserProfileEntity(userProfileDTO);

            //Apply change to current user and check if change was made
            boolean userAppliedChange = userUpdater.updateProfileFields(currentUser, submittedUser);

            if (userAppliedChange) {
                logger.info("Changes applied to user profile for ID: {}", userId);
                return userRepository.save(currentUser);
            } else {
                logger.info("No changes detected for user profile ID: {}", userId);
                return currentUser;
            }
        } catch (Exception e) {
            logger.error("Error modifying profile for user ID {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("There was a problem saving the profile data using modifyProfile().");
        }
    }

    //Method to change user auth data
    public User modifyAuth(Integer userId, UserAuthDTO userAuthDTO) {
        logger.info("Modifying authentication details for user ID: {}", userId);

        try {
            //Retrieving user by id from repo and converting dto user to entity user
            User currentUser = userRepository.findById(userId).orElseThrow();
            User submittedUser = userEntityMapper.mapToUserAuthEntity(userAuthDTO);

            //Apply change to current user and check if change was made
            boolean userAppliedChange = userUpdater.updateAuthFields(currentUser, submittedUser);

            if (userAppliedChange) {
                logger.info("Authentication changes applied for user ID: {}", userId);
                return userRepository.save(currentUser);
            } else {
                logger.info("No authentication changes detected for user ID: {}", userId);
                return currentUser;
            }
        } catch (Exception e) {
            logger.error("Error modifying authentication for user ID {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("There was a problem saving the profile data using modifyAuth().");
        }
    }

    //Method to delete User
    public void deleteUser(Integer userId){
        logger.info("Attempting to delete user with ID: {}", userId);

        try {
            userRepository.deleteById(userId);
            logger.info("Successfully deleted user with ID: {}", userId);
        } catch (Exception e) {
            logger.error("Failed to delete user with ID {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("User with id " + userId + " could not be deleted");
        }
    }
}