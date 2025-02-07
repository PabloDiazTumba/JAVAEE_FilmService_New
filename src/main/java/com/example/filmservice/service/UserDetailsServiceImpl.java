package com.example.filmservice.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return User.withUsername(username)
                .password("$2a$10$E6.5F2jX.eQ7lW9/EkhE2eXQJdyM8E6O0OwG7JLkC7rG5OjK5q.Au") // BCrypt-hashat "password"
                .roles("USER")
                .build();
    }
}