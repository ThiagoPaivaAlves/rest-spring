package com.thiago.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.thiago.exceptions.InvalidJwtAuthenticationException;
import com.thiago.security.models.TokenDto;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {
    
    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";
    @Value("${security.jwt.token.expire-length:3600000}")
    private final long validityInMilliSeconds = 3600000; // 1 hour
    @Value("${security.jwt.token.refresh-length:3}")
    private final long refreshLenghtInHours = 3;
    
    @Autowired
    private UserService service;
    
    Algorithm algorithm = null;
    
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }
    
    public TokenDto createAccessToken(String username, List<String> roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliSeconds);
        String accessToken = getAccessToken(username, roles, now, validity);
        String refreshToken = getRefreshToken(username, roles, now);
        return TokenDto.builder()
                       .username(username)
                       .authenticated(true)
                       .created(now)
                       .expiration(validity)
                       .accessToken(accessToken)
                       .refreshToken(refreshToken)
                       .build();
    }
    
    public TokenDto refreshToken(String refreshToken) {
        
        if( refreshToken != null && refreshToken.startsWith("Bearer") ) {
           refreshToken = refreshToken.replace("Bearer", "").trim();
           DecodedJWT decodedToken = decodeToken(refreshToken);
           return createAccessToken(decodedToken.getSubject(), decodedToken.getClaim("roles").asList(String.class));
        }
        return null;
    }
    
    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        UserDetails userDetails =  this.service.loadUserByUsername(decodedJWT.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        
        if( bearerToken != null && bearerToken.startsWith("Bearer") ) {
            return bearerToken.replace("Bearer", "").trim();
        }
        return null;
    }
    
    public boolean validateToken(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        
        try {
//            if(decodedJWT.getExpiresAt().before(new Date())) { //if token is already expired, it means it is invalid
//                return false;
//            }
//            return true;
            return !decodedJWT.getExpiresAt().before(new Date());
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token.");
        }
        
    }
    
    private DecodedJWT decodeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        
        return verifier.verify(token);
    }
    
    private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return JWT.create()
                  .withClaim("roles", roles)
                  .withIssuedAt(now)
                  .withExpiresAt(validity)
                  .withSubject(username)
                  .withIssuer(issuerUrl)
                  .sign(algorithm).strip();
    }
    
    private String getRefreshToken(String username, List<String> roles, Date now) {
        return JWT.create()
                  .withClaim("roles", roles)
                  .withIssuedAt(now)
                  .withExpiresAt(new Date(now.getTime() + (validityInMilliSeconds * refreshLenghtInHours))) //this refresher can be
                  // changed through a property
                  .withSubject(username)
                  .sign(algorithm).strip();
    }
    
    
}
