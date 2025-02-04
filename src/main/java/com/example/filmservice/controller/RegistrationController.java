package com.example.filmservice.controller;

import com.example.filmservice.model.AppUser;
import com.example.filmservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    // Registrera användare
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Validated @RequestBody AppUser user, BindingResult bindingResult) {
        // Logga indata
        System.out.println("Register request received for username: " + user.getUsername() + " and password: " + user.getPassword());

        // Validera om användarnamnet redan finns
        if (userService.getUserByUsername(user.getUsername()).isPresent()) {
            System.out.println("Username already exists!");
            return new ResponseEntity<>("Username already exists!", HttpStatus.BAD_REQUEST);
        }

        // Hantera valideringsfel
        if (bindingResult.hasErrors()) {
            System.out.println("Validation errors: " + bindingResult.getAllErrors());
            return new ResponseEntity<>("Invalid input data", HttpStatus.BAD_REQUEST);
        }

        // Logga innan användaren skapas
        System.out.println("Saving user: " + user.getUsername());
        // Spara användaren i databasen
        AppUser savedUser = userService.createUser(user);

        // Logga efter användaren har skapats
        System.out.println("User registered successfully!");
        return new ResponseEntity<>("User " + savedUser.getUsername() + " registered successfully!", HttpStatus.CREATED);
    }
}