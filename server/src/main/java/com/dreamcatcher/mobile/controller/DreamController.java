package com.dreamcatcher.mobile.controller;

import com.dreamcatcher.mobile.dto.SubmitDreamDTO;
import com.dreamcatcher.mobile.dto.CallDreamDTO;
import com.dreamcatcher.mobile.service.DreamManagementService;
import com.dreamcatcher.mobile.enums.Mood;
import com.dreamcatcher.mobile.enums.SleepQuality;
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

@RequiredArgsConstructor
@RestController
public class DreamController {

    private final DreamManagementService dreamManagementService;

    //Creating dreams
    @PostMapping("users/{userId}/dreams")
    public ResponseEntity<CallDreamDTO> createDream(@PathVariable Integer userId, @RequestBody SubmitDreamDTO submitDreamDTO){
        CallDreamDTO createdDream = dreamManagementService.createDream(userId, submitDreamDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDream);
    }

    //Getting specific dream
    @GetMapping("dreams/{dreamId}")
    public ResponseEntity<CallDreamDTO> getDreamById(@PathVariable Integer dreamId) {
        CallDreamDTO foundDream = dreamManagementService.getDreamById(dreamId);
        return ResponseEntity.status(HttpStatus.OK).body(foundDream);
    }

    //Getting all dreams
    @GetMapping("/users/{userId}/dreams")
    public ResponseEntity<List<CallDreamDTO>> getAllDreamsByUser(@PathVariable Integer userId) {
        List<CallDreamDTO> foundDreams = dreamManagementService.getAllDreamsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(foundDreams);
    }

    //Delete dream
    @DeleteMapping("dreams/{dreamId}")
    public ResponseEntity<Void> deleteDreamById(@PathVariable Integer dreamId){
        dreamManagementService.deleteDreamById(dreamId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //Getting the mood options
    @GetMapping("/options/mood")
    public ResponseEntity<Mood[]> getMoodOptions() {
        return ResponseEntity.ok(Mood.values());
    }

    //Getting the sleep quality options
    @GetMapping("options/sleep-qualities")
    public ResponseEntity<SleepQuality[]> getSleepQualityOptions() {
        return ResponseEntity.ok(SleepQuality.values());
    }
}