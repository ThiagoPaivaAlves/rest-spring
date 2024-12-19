package com.thiago.services;

import com.thiago.exceptions.NotFoundException;
import com.thiago.mocks.MockBook;
import com.thiago.models.BookDto;
import com.thiago.models.entities.Book;
import com.thiago.repositories.BookRepository;
import com.thiago.util.Mapper;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    
    @InjectMocks
    BookService service;
    
    @Mock
    BookRepository repository;
    
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    @DisplayName("Check if HATEOS link is empty")
    void findBydIdHateos() {
        
        Book entity = MockBook.mockBook();
        
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        
        BookDto result = service.findById(1L);
        
        assertFalse(result.getLinks().stream().toList().isEmpty());
    }
    
    @Test
    @DisplayName("Check if result is compatible")
    void findBydIResult() {
        
        BookDto dto = MockBook.mockBookDto();
        Book entity = MockBook.mockBook();
        
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        
        BookDto result = service.findById(1L);
        assertTrue(new ReflectionEquals(dto, "links").matches(result));
    }
    
    @Test
    @DisplayName("Testing create book")
    void testCreateBook() {
        
        Book entity = MockBook.mockBook();
        
        when(repository.save(any(Book.class))).thenReturn(entity);
        
        BookDto dto = MockBook.mockBookDto().withKey(null);
        BookDto result = service.createBook(dto);
        
        assertTrue(new ReflectionEquals(entity, "links").matches(Mapper.bookDtoMapper(result)));
    }
    
    @Test
    @DisplayName("Test throws exception when creating")
    void testCreateBookThrowsException() {
        Exception exception = assertThrows(BadRequestException.class, () -> service.createBook(null));
        
        String expectedMessage = "You need to provide a proper book input";
        String actualMessage = exception.getMessage();
        
        assertEquals(expectedMessage, actualMessage);
    }
    
    @Test
    @DisplayName("Test throws exception when updating")
    void testUpdateBookThrowsException() {
        
        when(repository.findById(anyLong())).thenReturn(Optional.of(MockBook.mockBook()));
        
        Exception exception = assertThrows(BadRequestException.class, () -> service.updateBook(anyLong(), null));
        
        String expectedMessage = "You need to provide a proper book input";
        String actualMessage = exception.getMessage();
        
        assertEquals(expectedMessage, actualMessage);
    }
    
    @Test
    @DisplayName("Test throws not found exception when updating")
    void testUpdateBookThrowsNotFoundException() {
        
        Exception exception = assertThrows(NotFoundException.class, () -> service.updateBook(anyLong(), null));
        
        String expectedMessage = "Book not found";
        String actualMessage = exception.getMessage();
        
        assertEquals(expectedMessage, actualMessage);
    }
    
    @Test
    @DisplayName("Test throws not found exception when deleting")
    void testDeleteBookThrowsNotFoundException() {
        
        Exception exception = assertThrows(NotFoundException.class, () -> service.deleteBook(anyLong()));
        
        String expectedMessage = "Book not found";
        String actualMessage = exception.getMessage();
        
        assertEquals(expectedMessage, actualMessage);
    }
    
    @Test
    void testUpdateBook() {
        
        BookDto dto = MockBook.mockBookDto();
        Book wanted = MockBook.mockBook();
        
        when(repository.findById(anyLong())).thenReturn(Optional.of(MockBook.mockBook()));
        when(repository.save(any(Book.class))).thenReturn(wanted);
        
        BookDto result = service.updateBook(1L, dto);
        
        assertTrue(new ReflectionEquals(wanted, "links").matches(Mapper.bookDtoMapper(result)));
    }
}
