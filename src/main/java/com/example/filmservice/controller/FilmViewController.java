package com.example.filmservice.controller;

import com.example.filmservice.service.TmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FilmViewController {

    private final TmdbService tmdbService;

    @Autowired
    public FilmViewController(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @GetMapping("/popular-films")
    public String getPopularFilms(Model model) {
        // Hämta populära filmer från TmdbService
        model.addAttribute("films", tmdbService.getPopularMoviesSortedByRating());
        return "popular-films";  // Returnera Thymeleaf-vyn
    }
}