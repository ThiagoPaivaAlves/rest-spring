package com.thiago.security.services;

import com.thiago.security.models.AccountCredentialsDto;
import com.thiago.security.models.entities.User;
import com.thiago.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    
    @Autowired
    private JwtTokenProvider provider;
    
    @Autowired
    private AuthenticationManager manager;
    
    @Autowired
    private UserRepository repository;
    
    @SuppressWarnings("rawtypes")
    public ResponseEntity signIn(AccountCredentialsDto credentials) {
        try {
            
            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));

            User user = repository.findByUserName(credentials.getUsername()).orElseThrow(
                    () -> new UsernameNotFoundException("There is no data for available for user name "
                                                                + credentials.getUsername()));
            
            return ResponseEntity.ok(provider.createAccessToken(credentials.getUsername(), user.getRoles()));
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }
    
    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String refreshToken) {
        return ResponseEntity.ok(provider.refreshToken(refreshToken));
    }
}
