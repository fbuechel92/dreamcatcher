package com.dreamcatcher.mobile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.interfaces.RSAPublicKey;
import java.security.KeyFactory;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.math.BigInteger;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/public/**", "/options/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> {})
            );
        return http.build();
    }
    
    @Bean
    public JwtDecoder jwtDecoder() {
        try {
            // Auth0 public key (kid: "65HdtNpldsaBT1GRTb-v8") - hardcoded to avoid networking issues
            String n = "mXHTe5HL9jol3GaeE69GwzyoCs1ZNAXESYXzGsOx6KZxP5lisV6cK-nqACn3M4lFzIAgp53QjEMntNDlmGMhIaCp2xiP7m86l3ScatvVoJtI6jUR879Gmkf_-htGVPPg4uoA1MMuDorKv2286Gfu-J0JoNuSlB2skFYZZf70jIzQwrHVnxJlZz11R1CWUNveWAgKlTKF3zjFY-5fUdFQWCFSWA3GTi9_3-7vx3L-e8JEl7jfxh4GuqCtDqj7BKtTUukhXIpjNUJYKc3nhRDqxxwAtfWhh0C7nufj1KdDPdGzRANI4yjpeCcXoW5YXR4nWKbO4rcSZFHDVzBUXYeBXQ";
            String e = "AQAB";
            
            return NimbusJwtDecoder.withPublicKey(createRSAPublicKey(n, e)).build();
        } catch (Exception ex) {
            throw new RuntimeException("JWT decoder configuration failed", ex);
        }
    }
    
    private RSAPublicKey createRSAPublicKey(String n, String e) throws Exception {
        byte[] nBytes = Base64.getUrlDecoder().decode(n);
        byte[] eBytes = Base64.getUrlDecoder().decode(e);
        
        BigInteger modulus = new BigInteger(1, nBytes);
        BigInteger exponent = new BigInteger(1, eBytes);
        
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }
}