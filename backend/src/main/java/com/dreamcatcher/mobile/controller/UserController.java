package com.dreamcatcher.mobile.controller;

import com.dreamcatcher.mobile.dto.UserAccountCreationDTO;
import com.dreamcatcher.mobile.dto.UserAccountDTO;
import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserManagementService userManagementService;

    @Autowired
    public UserController(UserManagementService userManagementService){
        this.userManagementService = userManagementService;
    }

    //Controller Methods
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserAccountDTO> getUser(@PathVariable Integer userId){
        UserAccountDTO userAccountDTO = userManagementService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userAccountDTO);
    }

    @PostMapping("/profile")
    public ResponseEntity<User> createUser(@RequestBody UserAccountCreationDTO userAccountCreationDTO){
        User user = userManagementService.createUser(userAccountCreationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<User> modifyUser(@PathVariable Integer userId, @RequestBody UserAccountDTO userAccountDTO){
        User user = userManagementService.modifyUser(userId, userAccountDTO);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteUser(@RequestParam Integer userId){
        userManagementService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}