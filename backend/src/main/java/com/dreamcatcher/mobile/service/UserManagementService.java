package com.dreamcatcher.mobile.service;

import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.repository.UserRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManagementService {

    private UserRepository userRepository;

    //Constructor
    @Autowired
    public UserManagementService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    private void createUser(String email, String password, String name, String gender, Date birthdate, String country, String occupation) {
        // Logic to create a user profile
        User user = new User(email, password, name, gender, birthdate, country, occupation);
        userRepository.save(user);
    }
}