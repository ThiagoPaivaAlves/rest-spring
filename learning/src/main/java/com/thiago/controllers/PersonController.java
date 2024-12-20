package com.thiago.controllers;

import com.thiago.models.PersonDto;
import com.thiago.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@Tag(name = "People", description = "Endpoint for managing people")
public class PersonController {
    
    @Autowired
    PersonService service;
    
    @GetMapping("/{id}")
    @Operation(description = "Finds a Person registered in database by its id",
            summary = "Finds a Person registered in database by its id", responses = {
            @ApiResponse(description = "SUCCESS", responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = PersonDto.class))}),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)})
    public PersonDto findPerson(@PathVariable("id") Long id) {
        return service.findById(id);
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Finds all Person registered in database",
            summary = "Finds all Person registered in database", responses = {
            @ApiResponse(description = "SUCCESS", responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = PersonDto.class)))}),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)})
    public List<PersonDto> findAllPerson() {
        return service.findAll();
    }
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Inserts a Person object in the database",
            summary = "Inserts a Person object in the database", responses = {
            @ApiResponse(description = "SUCCESS", responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = PersonDto.class))}),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)})
    public PersonDto createPerson(@RequestBody PersonDto personDto) {
        return service.create(personDto);
    }
    
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Updates a Person object in the database",
            summary = "Updates a Person object in the database", responses = {
            @ApiResponse(description = "SUCCESS", responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = PersonDto.class))}),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)})
    public PersonDto updatePerson(@PathVariable Long id, @RequestBody PersonDto personDto) {
        return service.update(id, personDto);
    }
    
    @DeleteMapping("/{id}")
    @Operation(description = "Deletes a Person from database", summary = "Deletes a Person from database",
            responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)})
    public ResponseEntity<?> deletePerson(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
