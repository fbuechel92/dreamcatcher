package com.dreamcatcher.mobile.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcatcher.mobile.dto.DreamAnalysisDTO;
import com.dreamcatcher.mobile.service.DreamAnalysisService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class DreamAnalysisController {

    private final DreamAnalysisService dreamAnalysisService;

    //Getting dream analysis
    @GetMapping("/dreams/{dreamId}/analysis")
    public ResponseEntity<?> getDreamAnalysis(@AuthenticationPrincipal Jwt jwt, @PathVariable Integer dreamId) {
        String auth0Id = jwt.getSubject();
        
        DreamAnalysisDTO analysis = dreamAnalysisService.getAnalysisForDream(dreamId, auth0Id);
        
        if (analysis == null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Analysis in progress. Please check back later.");
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(analysis);
    }
}