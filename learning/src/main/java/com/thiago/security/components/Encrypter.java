package com.thiago.security.components;

import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Encrypter {
    
    public String encrypt(String value) {
        
        int saltLength = 8;
        int iterations = 185000;
        
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        
        Pbkdf2PasswordEncoder pbkdf2Encoder =
         new Pbkdf2PasswordEncoder("", saltLength, iterations,
                                   Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

         encoders.put("pbkdf2", pbkdf2Encoder);
         DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
         passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
         
         return passwordEncoder.encode(value).replace("{pbkdf2}", "");
    }
}
