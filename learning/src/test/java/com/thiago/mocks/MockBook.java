package com.thiago.mocks;

import com.thiago.models.BookDto;
import com.thiago.models.entities.Book;

import java.math.BigDecimal;
import java.util.Date;

public class MockBook {
    
    public static BookDto mockBookDto() {
        return BookDto.builder()
                      .key(99L)
                      .title("test")
                      .author("test author")
                      .price(BigDecimal.valueOf(99.99))
                      .launch_date(new Date(2017-11-7))
                      .build();
    }
    
    public static Book mockBook() {
        return Book.builder()
                   .id(99L)
                   .title("test")
                   .author("test author")
                   .price(BigDecimal.valueOf(99.99))
                   .launch_date(new Date(2017-11-7))
                   .build();
    }
}
