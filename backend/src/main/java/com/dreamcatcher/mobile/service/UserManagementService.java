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

        //Create hashed password
        String hashedPassword = passwordEncoder.encode(userDTO.password());

        User user = userMapper.mapToEntity(userDTO);

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error saving user: " + e.getMessage());
        }
    }
}