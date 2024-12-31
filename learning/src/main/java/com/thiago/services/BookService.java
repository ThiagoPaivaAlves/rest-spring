package com.thiago.services;

import com.thiago.controllers.BookController;
import com.thiago.exceptions.NotFoundException;
import com.thiago.models.BookDto;
import com.thiago.repositories.BookRepository;
import com.thiago.util.Mapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@Service
public class BookService {
    
    @Autowired
    private BookRepository repository;
    
    @Autowired
    PagedResourcesAssembler<BookDto> assembler;
    
    public BookDto findById(Long id) {
        
        log.info("Finding a specific book");
        
        BookDto response = Mapper.bookMapper(repository.findById(id).orElseThrow(NotFoundException::new));
        response.add(linkTo(methodOn(BookController.class).findBookById(id)).withSelfRel());
        
        return response;
    }
    
    public PagedModel<EntityModel<BookDto>> findAllBooks(Pageable pageable) {
        
        log.info("Finding all books");
        
        return assembler.toModel(repository.findAll(pageable).map(book -> {
            BookDto dto = Mapper.bookMapper(book);
            dto.add(linkTo(methodOn(BookController.class).findBookById(dto.getKey())).withSelfRel());
            return dto;
        }), linkTo(methodOn(BookController.class).findAllBooks(pageable.getPageNumber(), pageable.getPageSize(),
                                                                  "ASC")).withSelfRel());
    }
    
    @SneakyThrows
    public BookDto createBook(BookDto newBook) {
        log.info("Inserting a book");
        
        if(newBook == null) {
            throw new BadRequestException("You need to provide a proper book input");
        }
        
        BookDto response = Mapper.bookMapper(repository.save(Mapper.bookDtoMapper(newBook)));
        response.add(linkTo(methodOn(BookController.class).createBook(newBook)).withSelfRel());
        
        return response;
    }
    
    @SneakyThrows
    public BookDto updateBook(Long id, BookDto updatedBook) {
        log.info("updating a book");
        
        repository.findById(id).orElseThrow(() -> new NotFoundException("Book not found"));
        
        if(updatedBook == null) {
            throw new BadRequestException("You need to provide a proper book input");
        }
        
        updatedBook.setKey(id);
        
        BookDto response = Mapper.bookMapper(repository.save(Mapper.bookDtoMapper(updatedBook)));
        response.add(linkTo(methodOn(BookController.class).updateBook(id, updatedBook)).withSelfRel());
        return response;
    }
    
    public void deleteBook(Long id) {
        log.info("deleting a book");
        
        repository.findById(id).orElseThrow(() -> new NotFoundException("Book not found"));
        repository.deleteById(id);
    }
}
