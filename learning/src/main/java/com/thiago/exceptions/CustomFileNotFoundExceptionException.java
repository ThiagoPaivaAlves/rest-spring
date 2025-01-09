package com.thiago.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomFileNotFoundExceptionException extends RuntimeException{
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    public CustomFileNotFoundExceptionException(String ex) {
        super(ex);
    }
    
    public CustomFileNotFoundExceptionException(String ex, Throwable cause) {
        super(ex, cause);
    }
}
