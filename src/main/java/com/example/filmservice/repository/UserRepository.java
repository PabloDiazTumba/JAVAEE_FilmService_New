package com.example.filmservice.repository;

import com.example.filmservice.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    // Hämta användare baserat på användarnamn
    Optional<AppUser> findByUsername(String username);
}