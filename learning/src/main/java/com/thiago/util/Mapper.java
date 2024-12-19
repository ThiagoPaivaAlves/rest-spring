package com.thiago.util;

import com.thiago.models.BookDto;
import com.thiago.models.PersonDto;
import com.thiago.models.entities.Book;
import com.thiago.models.entities.Person;

public class Mapper {

    public static PersonDto personMapper(Person person) {
        return PersonDto.builder().key(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .address(person.getAddress())
                .gender(person.getGender())
                .build();

    }

    public static Person personDtoMapper(PersonDto personDto) {
        return new Person(personDto.getKey(), personDto.getFirstName(), personDto.getLastName(), personDto.getAddress(),
                personDto.getGender());
    }
    
    public static BookDto bookMapper(Book book) {
        return BookDto.builder()
                      .key(book.getId())
                      .author(book.getAuthor())
                      .title(book.getTitle())
                      .launch_date(book.getLaunch_date())
                      .price(book.getPrice())
                      .build();
    }
    
    public static Book bookDtoMapper(BookDto bookDto) {
        return Book.builder()
                   .id(bookDto.getKey())
                   .title(bookDto.getTitle())
                   .price(bookDto.getPrice())
                   .author(bookDto.getAuthor())
                   .launch_date(bookDto.getLaunch_date())
                   .build();
    }
}
