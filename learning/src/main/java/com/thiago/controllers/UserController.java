package com.thiago.controllers;

import com.thiago.models.UserDto;
import com.thiago.services.SystemUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "Endpoint for managing System users")
public class UserController {
    
    @Autowired
    private SystemUserService service;
    
    @GetMapping("/{id}")
    @Operation(description = "Finds a user registered in database by its id",
            summary = "Finds a user registered in database by its id", responses = {
            @ApiResponse(description = "SUCCESS", responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)})
    public UserDto findUser(@PathVariable("id") Long id) {
        return service.findById(id);
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Finds all users registered in database",
            summary = "Finds all users registered in database", responses = {
            @ApiResponse(description = "SUCCESS", responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))}),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)})
    public List<UserDto> findAllUsers() {
        return service.findAll();
    }
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Inserts a User object in the database",
            summary = "Inserts a User object in the database", responses = {
            @ApiResponse(description = "SUCCESS", responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)})
    public UserDto createUser(@RequestBody @Valid UserDto userDto) {
        return service.create(userDto);
    }
    
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Updates a User object in the database",
            summary = "Updates a User object in the database", responses = {
            @ApiResponse(description = "SUCCESS", responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)})
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return service.update(id, userDto);
    }
    
    @DeleteMapping("/{id}")
    @Operation(description = "Deletes a User from database", summary = "Deletes a User from database",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)})
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
