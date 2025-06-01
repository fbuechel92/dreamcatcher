package com.dreamcatcher.mobile.service;

import com.dreamcatcher.mobile.dto.UserAccountCreationDTO;
import com.dreamcatcher.mobile.dto.UserAccountDTO;
import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.repository.UserRepository;
import com.dreamcatcher.mobile.utilities.ReflectionUpdater;
import com.dreamcatcher.mobile.utilities.UserDTOMapper;
import com.dreamcatcher.mobile.utilities.UserEntityMapper;
import java.util.Date;
import java.util.List;
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
    public User createUser(UserAccountCreationDTO userAccountCreationDTO) {
        //Check if mail already exists in db
        if (userRepository.existsByEmail(userAccountCreationDTO.email())) {
            throw new IllegalArgumentException("Email already exists. Please use another email address.");
        }

        User user = userEntityMapper.mapToUserAccountCreationEntity(userAccountCreationDTO);

        //Create hashed password
        String hashedPassword = passwordEncoder.encode(userAccountCreationDTO.password());
        user.setPassword(hashedPassword);

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error saving user: " + e.getMessage());
        }
    }

    //Method to provide user information if user visits user profile
    public UserAccountDTO getUser(Integer userId) {
        try {
            User user = userRepository.findById(userId).orElseThrow();
            UserAccountDTO userAccountDTO = userDTOMapper.mapToUserAccountDTO(user);
            return userAccountDTO;
        } catch (Exception e) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }
    }

    //Method to change user data
    public User modifyUser(Integer userId, UserAccountDTO userAccountDTO) {
        try {
            //Retrieving user by id from repo and converting dto user to entity user
            User currentUser = userRepository.findById(userId).orElseThrow();
            User submittedUser = userEntityMapper.mapToUserAccountEntity(userAccountDTO);

            //Apply change to current user and check if change was made
            boolean userAppliedChange = reflectionUpdater.updateFields(currentUser, submittedUser);

            if (userAppliedChange) {
                return userRepository.save(currentUser);
            } else {
                return currentUser;
            }
        } catch (Exception e) {
            throw new RuntimeException("We had trouble saving this :-(");
        }
    }
}