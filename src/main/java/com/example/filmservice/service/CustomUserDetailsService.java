package com.example.filmservice.service;

import com.example.filmservice.model.AppUser; // Vi har bytt från User till AppUser
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User; // Direkt användning av Spring Security User
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Hämta användaren från UserService istället för direkt från repository
        AppUser appUser = userService.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Returnera en UserDetails-objekt, som är det Spring Security använder
        return new User(appUser.getUsername(), appUser.getPassword(), new ArrayList<>());
    }
}