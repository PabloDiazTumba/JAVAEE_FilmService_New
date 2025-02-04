package com.example.filmservice.service;

import com.example.filmservice.model.Film;
import com.example.filmservice.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    // Hämta alla filmer sorterade efter betyg (högst först)
    public List<Film> getAllFilmsSortedByRating() {
        return filmRepository.findAllByOrderByRatingDesc();
    }

    // 1. Hämta alla filmer
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    // 2. Hämta en specifik film med hjälp av dess ID
    public Optional<Film> getFilmById(Long id) {
        return filmRepository.findById(id);
    }

    // 3. Spara en film (används både för att skapa och uppdatera filmer)
    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }

    // 4. Ta bort en film med hjälp av dess ID
    public void deleteFilm(Long id) {
        filmRepository.deleteById(id);
    }
}