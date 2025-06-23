package com.dreamcatcher.mobile.controller;

import com.dreamcatcher.mobile.dto.DreamDTO;
import com.dreamcatcher.mobile.entity.Dream;
import com.dreamcatcher.mobile.service.DreamManagementService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DreamController {

    private DreamManagementService dreamManagementService;

    //Creating dreams
    @PostMapping("/dream/")
    public ResponseEntity<Dream> createDream(@RequestBody Integer userId, @RequestBody DreamDTO dreamDTO){
        Dream dream = dreamManagementService.createDream(userId, dreamDTO);
        return ResponseEntity.status(HttpStatus.OK).body(dream);
    }

    //Getting specific dream
    @GetMapping("/dream/{dreamId}")
    public ResponseEntity<Dream> getDreamById(@PathVariable Integer dreamId) {
        Dream dream = dreamManagementService.getDreamById(dreamId);
        return ResponseEntity.status(HttpStatus.OK).body(dream);
    }

    //Getting all dreams
    @GetMapping("/dreams/{userId}")
    public ResponseEntity<List<Dream>> getAllDreamsByUser(@PathVariable Integer userId) {
        List<Dream> dreams = dreamManagementService.getAllDreamsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(dreams);
    }

    //Delete dream
    @PutMapping("/dream/{dreamId}")
    public ResponseEntity<Void> deleteDreamById(Integer dreamId){
        dreamManagementService.deleteDreamById(dreamId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}