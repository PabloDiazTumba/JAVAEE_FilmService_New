package com.example.filmservice.service;

import com.example.filmservice.model.AppUser;
import com.example.filmservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Lägg till PasswordEncoder
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Lägg till PasswordEncoder

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // Injektera PasswordEncoder
    }

    // Skapa eller uppdatera en användare
    public AppUser createUser(AppUser user) {
        // Logga innan användaren sparas
        System.out.println("Creating user: " + user.getUsername());

        // Hasha lösenordet innan användaren sparas
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hasha lösenordet

        return userRepository.save(user); // Spara användaren i databasen
    }

    // Hämta en användare baserat på ID
    public Optional<AppUser> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Hämta en användare baserat på användarnamn
    public Optional<AppUser> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Ta bort en användare baserat på ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}