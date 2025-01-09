package com.thiago.security.config;

import com.thiago.security.JwtTokenFilter;
import com.thiago.security.services.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    
    @Autowired
    private JwtTokenProvider provider;
    
    @Bean
    PasswordEncoder encoder() {
        int saltLength = 8;
        int iterations = 185000;
        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("", saltLength, iterations,
                                                                  Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", encoder);
        
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(encoder);
        
        return passwordEncoder;
    }
    
    @Bean
    AuthenticationManager authenticationManagerBean(
            AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtTokenFilter customFilter = new JwtTokenFilter(provider);
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers(
                                        "/authentication/**",
                                        "/authentication/refresh/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/styles/**" ,
                                        "/error/**"
                                                ).permitAll()
                                .requestMatchers("/book/**").authenticated()
                                .requestMatchers("/person/**").authenticated()
                                .requestMatchers("/api/**").authenticated()
//                                .requestMatchers("/users").denyAll()
                                      )
                .cors(cors -> {})
                .build();
        
    }
}
