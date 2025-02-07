package com.example.filmservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // Använd BAD_REQUEST (400) som standardstatuskod
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}