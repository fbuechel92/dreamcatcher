package com.dreamcatcher.mobile.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.apache.hc.client5.http.config.RequestConfig; // Correct import
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient; // Correct import
import org.apache.hc.client5.http.impl.classic.HttpClients; // Correct import
import org.apache.hc.core5.util.Timeout; // Correct import for Timeout
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Component
public class OllamaClient {

    @Value("${ollama.api.url:http://localhost:11434}")
    private String ollamaApiUrl;

    @Value("${ollama.model:mistral}")
    private String model;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OllamaClient() {
        // Set timeouts
        Timeout connectTimeout = Timeout.ofSeconds(240);
        Timeout responseTimeout = Timeout.ofSeconds(240);

        // Configure RequestConfig with timeouts
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setResponseTimeout(responseTimeout)
                .build();

        // Build the HTTP client with the RequestConfig
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(config)
                .build();

        // Configure RestTemplate
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        this.restTemplate = new RestTemplate(requestFactory);
        this.objectMapper = new ObjectMapper();
    }

    public String generateAnalysis(String prompt) throws Exception {
        String endpoint = ollamaApiUrl + "/api/generate";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("prompt", prompt);
        requestBody.put("stream", false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(endpoint, request, String.class);

        // Parse JSON response
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        return jsonNode.get("response").asText();
    }
}