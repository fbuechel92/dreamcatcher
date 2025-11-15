package com.dreamcatcher.mobile.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.dreamcatcher.mobile.dto.DreamAnalysisDTO;
import com.dreamcatcher.mobile.entity.Dream;
import com.dreamcatcher.mobile.entity.DreamAnalysis;
import com.dreamcatcher.mobile.entity.Theory;
import com.dreamcatcher.mobile.entity.User;
import com.dreamcatcher.mobile.mapper.DreamAnalysisDTOMapper;
import com.dreamcatcher.mobile.repository.DreamAnalysisRepository;
import com.dreamcatcher.mobile.repository.DreamRepository;
import com.dreamcatcher.mobile.repository.TheoryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.CompletableFuture;

@Service
public class DreamAnalysisService {
    
    private OllamaClient ollamaClient;
    private DreamAnalysisRepository dreamAnalysisRepository;
    private TheoryRepository theoryRepository;
    private EncryptionService encryptionService;
    private DreamRepository dreamRepository;
    private DreamAnalysisDTOMapper dreamAnalysisDTOMapper;
    private final ObjectMapper objectMapper;
    
    public DreamAnalysisService(
            OllamaClient ollamaClient,
            DreamAnalysisRepository dreamAnalysisRepository,
            TheoryRepository theoryRepository,
            EncryptionService encryptionService,
            DreamRepository dreamRepository,
            DreamAnalysisDTOMapper dreamAnalysisDTOMapper) {
        this.ollamaClient = ollamaClient;
        this.dreamAnalysisRepository = dreamAnalysisRepository;
        this.theoryRepository = theoryRepository;
        this.encryptionService = encryptionService;
        this.dreamRepository = dreamRepository;
        this.dreamAnalysisDTOMapper = dreamAnalysisDTOMapper;
        this.objectMapper = new ObjectMapper();
    }
    
    @Async
    public CompletableFuture<DreamAnalysis> analyzeDreamAsync(Dream dream, User user) {
        try {
            // Decrypt dream fields before analysis
            Dream decryptedDream = decryptDream(dream);
            
            // Build prompt for LLM
            String prompt = buildDreamAnalysisPrompt(decryptedDream);
            
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
            
            // Encrypt analysis fields before saving
            analysis.setDreamTitle(encryptionService.encrypt(parsedResponse.title));
            analysis.setDreamTheme(encryptionService.encrypt(parsedResponse.theme));
            analysis.setInterpretation(encryptionService.encrypt(parsedResponse.interpretation));
            analysis.setImplications(encryptionService.encrypt(parsedResponse.implications));
            
            // Save to database
            DreamAnalysis savedAnalysis = dreamAnalysisRepository.save(analysis);
            
            // Link analysis back to dream
            Dream existingDream = dreamRepository.findById(dream.getDreamId())
                .orElseThrow(() -> new RuntimeException("Dream not found"));

            // Update only the DreamAnalysis field
            existingDream.setDreamAnalysis(savedAnalysis);

            // Save the updated Dream entity
            dreamRepository.save(existingDream);

            return CompletableFuture.completedFuture(savedAnalysis);
            
        } catch (Exception e) {
            System.err.println("Error analyzing dream: " + e.getMessage());
            e.printStackTrace();
            return CompletableFuture.failedFuture(e);
        }
    }
    
    // Decrypt dream fields for LLM processing
    private Dream decryptDream(Dream dream) {
        Dream decrypted = new Dream();
        
        try {
            decrypted.setVisitor(encryptionService.decrypt(dream.getVisitor()));
            decrypted.setPlot(encryptionService.decrypt(dream.getPlot()));
            decrypted.setLocation(encryptionService.decrypt(dream.getLocation()));
            decrypted.setMood(encryptionService.decrypt(dream.getMood()));
            decrypted.setSleepQuality(encryptionService.decrypt(dream.getSleepQuality()));
            decrypted.setAdditionalInfo(encryptionService.decrypt(dream.getAdditionalInfo()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt dream data for analysis", e);
        }
        
        return decrypted;
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

    public DreamAnalysisDTO getAnalysisForDream(Integer dreamId, String auth0Id) {
    // Find the dream and verify ownership
    Dream dream = dreamRepository.findById(dreamId)
        .orElseThrow(() -> new RuntimeException("Dream not found"));
    
    // Verify user owns this dream
    if (!dream.getUser().getAuth0Id().equals(auth0Id)) {
        throw new RuntimeException("Unauthorized access to dream analysis");
    }
    
    // Check if analysis exists for this dream
    if (dream.getDreamAnalysis() == null) {
        return null; // Analysis not yet complete
    }
    
    DreamAnalysis analysis = dream.getDreamAnalysis();
    
    // Decrypt fields in service (following your pattern)
    try {
        analysis.setDreamTitle(encryptionService.decrypt(analysis.getDreamTitle()));
        analysis.setDreamTheme(encryptionService.decrypt(analysis.getDreamTheme()));
        analysis.setInterpretation(encryptionService.decrypt(analysis.getInterpretation()));
        analysis.setImplications(encryptionService.decrypt(analysis.getImplications()));
    } catch (Exception e) {
        throw new RuntimeException("Failed to decrypt analysis data", e);
    }
    
    // Use mapper to convert decrypted entity to DTO
    return dreamAnalysisDTOMapper.mapToDreamAnalysisDTO(analysis);
}
}