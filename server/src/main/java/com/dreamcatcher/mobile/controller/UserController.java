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
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserManagementService userManagementService;

    //to save profile changes
    @PutMapping("/profile")
    public ResponseEntity<UserProfileDTO> modifyProfile(@AuthenticationPrincipal Jwt jwt, @RequestBody UserProfileDTO userProfileDTO){
        String auth0Id = jwt.getSubject();
        UserProfileDTO modifiedProfile = userManagementService.modifyProfile(auth0Id, userProfileDTO);
        return ResponseEntity.status(HttpStatus.OK).body(modifiedProfile);
    }

    //to get the profile
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getProfile(@AuthenticationPrincipal Jwt jwt){
        String auth0Id = jwt.getSubject();
        UserProfileDTO foundProfile = userManagementService.getProfile(auth0Id);
        return ResponseEntity.status(HttpStatus.OK).body(foundProfile);
    }

    //to checka nd save auth info
    @PostMapping("/auth")
    public ResponseEntity<UserAuthDTO> checkAndCreateUser(@AuthenticationPrincipal Jwt jwt, @RequestBody Map<String, String> userInfo) {
        String auth0Id = jwt.getSubject();
        String email = userInfo.get("email");

        // Delegate the check-and-create logic to the service
        UserAuthDTO userAuthDTO = userManagementService.checkAndCreateUser(auth0Id, email);

        // Return 201 Created for new users, or 200 OK for existing users
        HttpStatus status = userAuthDTO != null ? HttpStatus.CREATED : HttpStatus.OK;
        return ResponseEntity.status(status).body(userAuthDTO);
    }

    //to get auth info
    @GetMapping("/auth")
    public ResponseEntity<UserAuthDTO> getAuth(@AuthenticationPrincipal Jwt jwt){
        String auth0Id = jwt.getSubject();
        UserAuthDTO foundAuth = userManagementService.getAuth(auth0Id);
        return ResponseEntity.status(HttpStatus.CREATED).body(foundAuth);
    }

    //to delete user record
    @DeleteMapping("/user")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal Jwt jwt){
        String auth0Id = jwt.getSubject();
        userManagementService.deleteUser(auth0Id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}