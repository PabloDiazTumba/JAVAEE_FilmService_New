package com.example.filmservice.service;

import com.example.filmservice.model.AppUser;
import com.example.filmservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Hämta användaren från databasen
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Returnera en UserDetails-instans baserad på användaren från databasen
        return User.withUsername(appUser.getUsername())
                .password(appUser.getPassword()) // Använd det hashande lösenordet från databasen
                .roles("USER") // Lägg till roller om det behövs
                .build();
    }
}