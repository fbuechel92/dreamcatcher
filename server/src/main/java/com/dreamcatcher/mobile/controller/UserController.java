package com.dreamcatcher.mobile.controller;

import com.dreamcatcher.mobile.dto.UserAuthDTO;
import com.dreamcatcher.mobile.dto.UserProfileDTO;
import com.dreamcatcher.mobile.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserManagementService userManagementService;

    //Controller Methods
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileDTO> getUser(@PathVariable Integer userId){
        UserProfileDTO foundUserProfile = userManagementService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(foundUserProfile);
    }

    @PostMapping("/profile")
    public ResponseEntity<UserAuthDTO> createUser(@RequestBody UserAuthDTO userAuthDTO){
        UserAuthDTO createdUser = userManagementService.createUser(userAuthDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<UserProfileDTO> modifyUser(@PathVariable Integer userId, @RequestBody UserProfileDTO userProfileDTO){
        UserProfileDTO modifiedUser = userManagementService.modifyProfile(userId, userProfileDTO);
        return ResponseEntity.status(HttpStatus.OK).body(modifiedUser);
    }

    @PutMapping("/auth/{userId}")
    public ResponseEntity<UserAuthDTO> modifyAuth(@PathVariable Integer userId, @RequestBody UserAuthDTO userAuthDTO){
        UserAuthDTO modifiedUser = userManagementService.modifyAuth(userId, userAuthDTO);
        return ResponseEntity.status(HttpStatus.OK).body(modifiedUser);
    }

    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteUser(@RequestParam Integer userId){
        userManagementService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}