package com.dreamcatcher.mobile.service;

import com.dreamcatcher.mobile.dto.UserAccountCreationDTO;
import com.dreamcatcher.mobile.dto.UserAccountDTO;
import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.repository.UserRepository;
import com.dreamcatcher.mobile.utilities.UserDTOMapper;
import com.dreamcatcher.mobile.utilities.UserEntityMapper;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManagementService {

    private UserRepository userRepository;
    private UserEntityMapper userEntityMapper;
    private UserDTOMapper userDTOMapper;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserManagementService(UserRepository userRepository, UserEntityMapper userEntityMapper, UserDTOMapper userDTOMapper, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
        this.userDTOMapper = userDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }

    //Method to save user to the database
    public User createUser(UserAccountCreationDTO userAccountCreationDTO) {
        //Check if mail already exists in db
        if(userRepository.existsByEmail(userAccountCreationDTO.email())){
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
    public UserAccountDTO getUser(Integer userId){
        try {
            User user = userRepository.findById(userId).orElseThrow();
            UserAccountDTO userAccountDTO = userDTOMapper.mapToUserAccountDTO(user);
            return userAccountDTO;
        } catch (Exception e) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }
    }

    //Method to change user data
    public User modifyUser(Integer userId, UserAccountDTO userAccountDTO){
        //Retrieving user by id from repo and converting dto user to entity user
        try {
            User currentUser = userRepository.findById(userId).orElseThrow();
            User submittedUser = userEntityMapper.mapToUserAccountEntity(userAccountDTO);
            Integer changeCounter = 0;

            //Check what data changed
            if(!currentUser.getEmail().equals(submittedUser.getEmail())){
                currentUser.setEmail(submittedUser.getEmail());
                changeCounter += 1;
            }

            if(!currentUser.getName().equals(submittedUser.getName())){
                currentUser.setName(submittedUser.getName());
                changeCounter += 1;
            }

            if(!currentUser.getGender().equals(submittedUser.getGender())){
                currentUser.setGender(submittedUser.getGender());
                changeCounter += 1;
            }

            if(!currentUser.getBirthdate().equals(submittedUser.getBirthdate())){
                currentUser.setBirthdate(submittedUser.getBirthdate());
                changeCounter += 1;
            }

            if(!currentUser.getCountry().equals(submittedUser.getCountry())){
                currentUser.setCountry(submittedUser.getCountry());
                changeCounter += 1;
            }

            if(!currentUser.getOccupation().equals(submittedUser.getOccupation())){
                currentUser.setOccupation(submittedUser.getOccupation());
                changeCounter += 1;
            }

            if(changeCounter>0){
                return userRepository.save(currentUser);
            } else {
                System.out.println("No change detected");
                return currentUser;
            }

        } catch (Exception e) {
            throw new RuntimeException("We had trouble saving this :-(");
        }
    }
}