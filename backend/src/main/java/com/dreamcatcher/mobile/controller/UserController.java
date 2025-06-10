package com.dreamcatcher.mobile.controller;

import com.dreamcatcher.mobile.dto.UserAccountDTO;
import com.dreamcatcher.mobile.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
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
}