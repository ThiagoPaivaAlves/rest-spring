package com.thiago.controllers;

import com.thiago.models.BookDto;
import com.thiago.services.BookService;
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
@RequestMapping("/book")
@Tag(name="Book" , description="Controller for books")
//@CrossOrigin({"http://localhost:8080", ""})
public class BookController {
    
    @Autowired
    private BookService service;
    
    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Finds a Book registered in database by its id",
            summary = "Finds a Book registered in database by its id", responses = {
            @ApiResponse(description = "SUCCESS", responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = BookDto.class))}),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)})
    public BookDto findBookById(@PathVariable Long id){
        return service.findById(id);
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Finds all Books registered in database",
            summary = "Finds all Books registered in database", responses = {
            @ApiResponse(description = "SUCCESS", responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = BookDto.class)))}),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)})
    public List<BookDto> findAllBooks() {
        return service.findAllBooks();
    }
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Inserts a Book object in the database",
            summary = "Inserts a Book object in the database", responses = {
            @ApiResponse(description = "SUCCESS", responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = BookDto.class))}),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)})
    public BookDto createBook(@RequestBody @Valid BookDto newBook) {
        return service.createBook(newBook);
    }
    
    @PutMapping("/{id}")
    @Operation(description = "Updates a Book object in the database",
            summary = "Updates a Book object in the database", responses = {
            @ApiResponse(description = "SUCCESS", responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = BookDto.class))}),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)})
    
    public BookDto updateBook(@PathVariable Long id, @RequestBody @Valid BookDto updatedBook) {
        return service.updateBook(id, updatedBook);
    }
    
    @DeleteMapping("/{id}")
    @Operation(description = "Deletes a Book from database", summary = "Deletes a Book from database",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)})
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
