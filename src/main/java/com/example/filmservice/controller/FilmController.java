package com.example.filmservice.controller;

import com.example.filmservice.service.TmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")  // Lägg till bas-URL här
public class FilmController {

    private final TmdbService tmdbService;

    @Autowired
    public FilmController(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    // Hämta de mest populära filmerna
    @GetMapping("/popular")
    public ResponseEntity<List<Map<String, Object>>> getPopularMovies() {
        List<Map<String, Object>> popularMovies = tmdbService.getPopularMoviesSortedByRating();
        return ResponseEntity.ok(popularMovies);
    }

    // Hämta alla filmer
    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAllFilms() {
        List<Map<String, Object>> allFilms = tmdbService.getAllMovies();
        return ResponseEntity.ok(allFilms);
    }

    // Sök filmer baserat på en query
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchMovies(@RequestParam String query) {
        Map<String, Object> searchResults = tmdbService.searchMovies(query);
        return ResponseEntity.ok(searchResults);
    }
}