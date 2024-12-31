package com.thiago.util;

import com.thiago.models.BookDto;
import com.thiago.models.PersonDto;
import com.thiago.models.UserDto;
import com.thiago.models.entities.Book;
import com.thiago.models.entities.Person;
import com.thiago.security.models.entities.User;

public class Mapper {

    public static PersonDto personMapper(Person person) {
        return PersonDto.builder().key(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .address(person.getAddress())
                .gender(person.getGender())
                .enabled(person.getEnabled())
                .build();

    }

    public static Person personDtoMapper(PersonDto personDto) {
        return new Person(personDto.getKey(), personDto.getFirstName(), personDto.getLastName(), personDto.getAddress(),
                personDto.getGender(), personDto.getEnabled());
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
    
    public static UserDto userMapper(User user) {
        return UserDto.builder()
                      .key(user.getId())
                      .username(user.getUsername())
                      .fullName(user.getFullName())
                      .accountNonLocker(user.getAccountNonLocker())
                      .accountNotExpired(user.getAccountNotExpired())
                      .credentialsNonExpired(user.getCredentialsNonExpired())
                      .password(user.getPassword())
                      .enabled(user.isEnabled())
                      .permissions(user.getPermissions())
                      .build();
    }
    
    public static User userDtoMapper(UserDto userDto) {
        return User.builder()
                   .id(userDto.getKey())
                   .userName(userDto.getUsername())
                   .fullName(userDto.getFullName())
                   .accountNotExpired(userDto.getAccountNotExpired())
                   .accountNonLocker(userDto.getAccountNonLocker())
                   .credentialsNonExpired(userDto.getCredentialsNonExpired())
                   .password(userDto.getPassword())
                   .enabled(userDto.getEnabled())
                   .permissions(userDto.getPermissions())
                   .build();
    }
}
