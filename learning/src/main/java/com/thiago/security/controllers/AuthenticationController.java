package com.thiago.security.controllers;

import com.thiago.models.PersonDto;
import com.thiago.security.models.AccountCredentialsDto;
import com.thiago.security.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@Tag(name = "Authentication endpoint", description="Controller for authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;
    
    @SuppressWarnings("rawtypes")
    @PostMapping(path="/signin", produces = MediaType.APPLICATION_JSON_VALUE, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Authenticate a user using its credentials",
            summary = "Authenticate a user using its credentials", responses = {
            @ApiResponse(description = "SUCCESS", responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = PersonDto.class))}),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)})
    public ResponseEntity signIn(@RequestBody @Valid AccountCredentialsDto credentials) {
        return service.signIn(credentials);
    }
    
    @SuppressWarnings("rawtypes")
    @PutMapping(path="/refresh", produces = MediaType.APPLICATION_JSON_VALUE, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Refresh an authenticate user's token",
            summary = "Refresh an authenticate user's token", responses = {
            @ApiResponse(description = "SUCCESS", responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = PersonDto.class))}),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)})
    public ResponseEntity refresh(@RequestHeader("Authorization") @NotEmpty String refreshToken) {
        return service.refreshToken(refreshToken);
    }
}
