package com.dreamcatcher.mobile.controller;

import com.dreamcatcher.mobile.dto.UserAuthDTO;
import com.dreamcatcher.mobile.dto.UserProfileDTO;
import com.dreamcatcher.mobile.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserManagementService userManagementService;

    //Controller Methods
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getUser(@AuthenticationPrincipal Jwt jwt){
        String auth0Id = jwt.getSubject();
        UserProfileDTO foundUserProfile = userManagementService.getUser(auth0Id);
        return ResponseEntity.status(HttpStatus.OK).body(foundUserProfile);
    }

    @PostMapping("/profile")
    public ResponseEntity<UserAuthDTO> createUser(@AuthenticationPrincipal Jwt jwt){
        String auth0Id = jwt.getSubject();
        String email = jwt.getClaim("email");
        String name = jwt.getClaim("name");
        UserAuthDTO createdUser = userManagementService.createUser(auth0Id, email, name);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileDTO> modifyUser(@AuthenticationPrincipal Jwt jwt, @RequestBody UserProfileDTO userProfileDTO){
        String auth0Id = jwt.getSubject();
        UserProfileDTO modifiedUser = userManagementService.modifyProfile(auth0Id, userProfileDTO);
        return ResponseEntity.status(HttpStatus.OK).body(modifiedUser);
    }

    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal Jwt jwt){
        String auth0Id = jwt.getSubject();
        userManagementService.deleteUser(auth0Id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}