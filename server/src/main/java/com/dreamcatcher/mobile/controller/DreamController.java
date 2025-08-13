package com.dreamcatcher.mobile.controller;

import com.dreamcatcher.mobile.dto.SubmitDreamDTO;
import com.dreamcatcher.mobile.dto.CallDreamDTO;
import com.dreamcatcher.mobile.service.DreamManagementService;
import com.dreamcatcher.mobile.enums.Mood;
import com.dreamcatcher.mobile.enums.SleepQuality;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RequiredArgsConstructor
@RestController
public class DreamController {

    private final DreamManagementService dreamManagementService;

    //Creating dreams
    @PostMapping("dreams")
    public ResponseEntity<CallDreamDTO> createDream(@AuthenticationPrincipal Jwt jwt, @RequestBody SubmitDreamDTO submitDreamDTO){
        String auth0Id = jwt.getSubject();
        CallDreamDTO createdDream = dreamManagementService.createDream(auth0Id, submitDreamDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDream);
    }

    //Getting specific dream
    @GetMapping("dreams/{dreamId}")
    public ResponseEntity<CallDreamDTO> getDreamById(@PathVariable Integer dreamId) {
        CallDreamDTO foundDream = dreamManagementService.getDreamById(dreamId);
        return ResponseEntity.status(HttpStatus.OK).body(foundDream);
    }

    //Getting all dreams
    @GetMapping("/dreams")
    public ResponseEntity<List<CallDreamDTO>> getAllDreamsByUser(@AuthenticationPrincipal Jwt jwt) {
        String auth0Id = jwt.getSubject();
        List<CallDreamDTO> foundDreams = dreamManagementService.getAllDreamsByUserId(auth0Id);
        return ResponseEntity.status(HttpStatus.OK).body(foundDreams);
    }

    //Delete dream
    @DeleteMapping("dreams/{dreamId}")
    public ResponseEntity<Void> deleteDreamById(@PathVariable Integer dreamId){
        dreamManagementService.deleteDreamById(dreamId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //Getting the mood options
    @GetMapping("/options/moods")
    public ResponseEntity<String[]> getMoodOptions() {
        String[] displayName = Arrays.stream(Mood.values())
            .map(Mood::getDisplayName)
            .toArray(String[]::new);
        return ResponseEntity.ok(displayName);
    }

    //Getting the sleep quality options
    @GetMapping("/options/sleep-qualities")
    public ResponseEntity<String[]> getSleepQualityOptions() {
        String[] displayNames = Arrays.stream(SleepQuality.values())
            .map(SleepQuality::getDisplayName)
            .toArray(String[]::new);
    return ResponseEntity.ok(displayNames);
    }
}