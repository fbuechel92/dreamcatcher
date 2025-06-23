package com.dreamcatcher.mobile.controller;

import com.dreamcatcher.mobile.dto.DreamDTO;
import com.dreamcatcher.mobile.entity.Dream;
import com.dreamcatcher.mobile.service.DreamManagementService;
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
    public ResponseEntity<Dream> createDream(@PathVariable Integer userId, @RequestBody DreamDTO dreamDTO){
        Dream dream = dreamManagementService.createDream(userId, dreamDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dream);
    }

    //Getting specific dream
    @GetMapping("dreams/{dreamId}")
    public ResponseEntity<Dream> getDreamById(@PathVariable Integer dreamId) {
        Dream dream = dreamManagementService.getDreamById(dreamId);
        return ResponseEntity.status(HttpStatus.OK).body(dream);
    }

    //Getting all dreams
    @GetMapping("/users/{userId}/dreams")
    public ResponseEntity<List<Dream>> getAllDreamsByUser(@PathVariable Integer userId) {
        List<Dream> dreams = dreamManagementService.getAllDreamsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(dreams);
    }

    //Delete dream
    @DeleteMapping("dreams/{dreamId}")
    public ResponseEntity<Void> deleteDreamById(@PathVariable Integer dreamId){
        dreamManagementService.deleteDreamById(dreamId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}