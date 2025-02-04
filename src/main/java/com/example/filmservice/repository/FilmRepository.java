package com.example.filmservice.repository;

import com.example.filmservice.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    // Hämta alla filmer sorterade efter betyg, högsta först
    List<Film> findAllByOrderByRatingDesc();
}