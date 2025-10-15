package com.dreamcatcher.mobile.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

@Service
public class SimpleEncryptionService {
    
    @Value("${app.encryption.salt:mySecretSalt123}")
    private String salt;
    
    public String encrypt(String data) {
        if (data == null || data.isEmpty()) {
            return data;
        }
        
        try {
            // Simple encryption: salt + data + salt, then Base64 encode
            String combined = salt + data + salt;
            return Base64.getEncoder().encodeToString(combined.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }
    
    public String decrypt(String encryptedData) {
        if (encryptedData == null || encryptedData.isEmpty()) {
            return encryptedData;
        }
        
        try {
            String decoded = new String(Base64.getDecoder().decode(encryptedData), StandardCharsets.UTF_8);
            // Remove salt from beginning and end
            return decoded.substring(salt.length(), decoded.length() - salt.length());
        } catch (Exception e) {
            // If decryption fails, return original data (might be unencrypted)
            return encryptedData;
        }
    }
}