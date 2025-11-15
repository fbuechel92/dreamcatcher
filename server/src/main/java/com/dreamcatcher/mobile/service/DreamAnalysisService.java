package com.dreamcatcher.mobile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.dreamcatcher.mobile.entity.Dream;
import com.dreamcatcher.mobile.entity.DreamAnalysis;
import com.dreamcatcher.mobile.entity.Theory;
import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.repository.DreamAnalysisRepository;
import com.dreamcatcher.mobile.repository.TheoryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.CompletableFuture;

@Service
public class DreamAnalysisService {
    
    @Autowired
    private OllamaClient ollamaClient;
    
    @Autowired
    private DreamAnalysisRepository dreamAnalysisRepository;
    
    @Autowired
    private TheoryRepository theoryRepository;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Async
    public CompletableFuture<DreamAnalysis> analyzeDreamAsync(Dream dream, User user) {
        try {
            // Build prompt for LLM
            String prompt = buildDreamAnalysisPrompt(dream);
            
            // Call Ollama
            String llmResponse = ollamaClient.generateAnalysis(prompt);
            
            // Parse LLM response
            DreamAnalysisResponse parsedResponse = parseLlmResponse(llmResponse);
            
            // Get default theory (ID = 1)
            Theory defaultTheory = theoryRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Default theory not found"));
            
            // Create DreamAnalysis entity
            DreamAnalysis analysis = new DreamAnalysis();
            analysis.setUser(user);
            analysis.setTheory(defaultTheory);
            analysis.setDreamTitle(parsedResponse.title);
            analysis.setDreamTheme(parsedResponse.theme);
            analysis.setInterpretation(parsedResponse.interpretation);
            analysis.setImplications(parsedResponse.implications);
            
            // Save to database
            DreamAnalysis savedAnalysis = dreamAnalysisRepository.save(analysis);
            
            return CompletableFuture.completedFuture(savedAnalysis);
            
        } catch (Exception e) {
            System.err.println("Error analyzing dream: " + e.getMessage());
            e.printStackTrace();
            return CompletableFuture.failedFuture(e);
        }
    }
    
    // create prompt
    private String buildDreamAnalysisPrompt(Dream dream) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Analyze the following dream and provide a structured analysis in JSON format while staying below 200 tokens.\n\n");
        prompt.append("Dream Details:\n");
        if (dream.getVisitor() != null) prompt.append("Visitor: ").append(dream.getVisitor()).append("\n");
        if (dream.getPlot() != null) prompt.append("Plot: ").append(dream.getPlot()).append("\n");
        if (dream.getLocation() != null) prompt.append("Location: ").append(dream.getLocation()).append("\n");
        if (dream.getMood() != null) prompt.append("Mood: ").append(dream.getMood()).append("\n");
        if (dream.getSleepQuality() != null) prompt.append("Sleep Quality: ").append(dream.getSleepQuality()).append("\n");
        if (dream.getAdditionalInfo() != null) prompt.append("Additional Info: ").append(dream.getAdditionalInfo()).append("\n");
        
        prompt.append("\nProvide your analysis in this exact JSON format:\n");
        prompt.append("{\n");
        prompt.append("  \"title\": \"A short, meaningful title for this dream\",\n");
        prompt.append("  \"theme\": \"The main theme of the dream\",\n");
        prompt.append("  \"interpretation\": \"Your interpretation of what the dream might mean\",\n");
        prompt.append("  \"implications\": \"Potential implications or insights for the dreamer\"\n");
        prompt.append("}\n");
        
        return prompt.toString();
    }
    
    private DreamAnalysisResponse parseLlmResponse(String llmResponse) throws Exception {
        // extract JSON from response and parse to text
        String jsonString = extractJson(llmResponse);
        
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        
        DreamAnalysisResponse response = new DreamAnalysisResponse();
        response.title = jsonNode.get("title").asText();
        response.theme = jsonNode.get("theme").asText();
        response.interpretation = jsonNode.get("interpretation").asText();
        response.implications = jsonNode.get("implications").asText();
        
        return response;
    }
    
    private String extractJson(String response) {
        // Find JSON object in response
        int startIndex = response.indexOf("{");
        int endIndex = response.lastIndexOf("}");
        
        if (startIndex != -1 && endIndex != -1) {
            return response.substring(startIndex, endIndex + 1);
        }
        
        return response;
    }
    
    // Inner class for parsing LLM response
    private static class DreamAnalysisResponse {
        String title;
        String theme;
        String interpretation;
        String implications;
    }
}