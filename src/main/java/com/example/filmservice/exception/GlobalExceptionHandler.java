package com.example.filmservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // Markerar denna klass som global felhanterare
public class GlobalExceptionHandler {

    // Hantera alla generella undantag
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        // Skapa ett felmeddelande som ska returneras till klienten
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", new Date()); // Tidpunkt då felet inträffade
        errorDetails.put("message", ex.getMessage()); // Felmeddelandet
        errorDetails.put("details", request.getDescription(false)); // Ytterligare detaljer

        // Returnera ett ResponseEntity med felmeddelandet och HTTP-statuskod 500 (Internal Server Error)
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Hantera CustomException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ex, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", new Date());
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("details", request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST); // Använd BAD_REQUEST (400)
    }
}