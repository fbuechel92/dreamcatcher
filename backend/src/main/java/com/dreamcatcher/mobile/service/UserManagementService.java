package com.dreamcatcher.mobile.service;

import com.dreamcatcher.mobile.dto.UserDTO;
import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.repository.UserRepository;
import com.dreamcatcher.mobile.utilities.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManagementService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserManagementService(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    //Method to save user to the database
    public User createUser(UserDTO userDTO) {
        //Check if mail already exists in db
        if(userRepository.existsByEmail(userDTO.email())){
            throw new IllegalArgumentException("Email already exists. Please use another email address.");
        }

        User user = userMapper.mapToEntity(userDTO);

        //Create hashed password
        String hashedPassword = passwordEncoder.encode(userDTO.password());
        user.setPassword(hashedPassword);

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error saving user: " + e.getMessage());
        }
    }

    //Method to provide user information if user visits user profile
    public UserDTO getUserData(Integer userId){
        try {
            User user = userRepository.findById(userId).orElseThrow();
            UserDTO userDTO = userMapper.mapToDTO(user);
            return userDTO;
        } catch (Exception e) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }
    }
}