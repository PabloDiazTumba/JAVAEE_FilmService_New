package com.example.filmservice.controller;

import com.example.filmservice.service.TmdbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TmdbService tmdbService;

    @Test
    @WithMockUser // Mocka en autentiserad användare
    public void testErrorEndpoint() throws Exception {
        mockMvc.perform(get("/api/movies/test-error"))
                .andExpect(status().isBadRequest()) // Förvänta sig statuskod 400
                .andExpect(jsonPath("$.message").value("Detta är ett testfel!")) // Förvänta sig rätt felmeddelande
                .andExpect(jsonPath("$.details").value("uri=/api/movies/test-error")); // Förvänta sig rätt detaljer
    }

    @Test
    @WithMockUser // Mocka en autentiserad användare
    public void testGetPopularMovies() throws Exception {
        // Mocka TmdbService för att returnera en lista med populära filmer
        when(tmdbService.getPopularMoviesSortedByRating()).thenReturn(List.of(
                Map.of("title", "Film 1", "rating", 8.5),
                Map.of("title", "Film 2", "rating", 7.8)
        ));

        // Testa /api/movies/popular
        mockMvc.perform(get("/api/movies/popular"))
                .andExpect(status().isOk()) // Förvänta sig statuskod 200
                .andExpect(jsonPath("$[0].title").value("Film 1")) // Förvänta sig rätt titel för första filmen
                .andExpect(jsonPath("$[0].rating").value(8.5)) // Förvänta sig rätt betyg för första filmen
                .andExpect(jsonPath("$[1].title").value("Film 2")) // Förvänta sig rätt titel för andra filmen
                .andExpect(jsonPath("$[1].rating").value(7.8)); // Förvänta sig rätt betyg för andra filmen
    }

    @Test
    @WithMockUser // Mocka en autentiserad användare
    public void testGetAllFilms() throws Exception {
        // Mocka TmdbService för att returnera en lista med alla filmer
        when(tmdbService.getAllMovies()).thenReturn(List.of(
                Map.of("title", "Film 1", "director", "Regissör 1"),
                Map.of("title", "Film 2", "director", "Regissör 2")
        ));

        // Testa /api/movies/all
        mockMvc.perform(get("/api/movies/all"))
                .andExpect(status().isOk()) // Förvänta sig statuskod 200
                .andExpect(jsonPath("$[0].title").value("Film 1")) // Förvänta sig rätt titel för första filmen
                .andExpect(jsonPath("$[0].director").value("Regissör 1")) // Förvänta sig rätt regissör för första filmen
                .andExpect(jsonPath("$[1].title").value("Film 2")) // Förvänta sig rätt titel för andra filmen
                .andExpect(jsonPath("$[1].director").value("Regissör 2")); // Förvänta sig rätt regissör för andra filmen
    }

    @Test
    @WithMockUser // Mocka en autentiserad användare
    public void testSearchMovies() throws Exception {
        // Mocka TmdbService för att returnera sökresultat
        when(tmdbService.searchMovies("Film")).thenReturn(Map.of(
                "results", List.of(
                        Map.of("title", "Film 1", "overview", "Beskrivning 1"),
                        Map.of("title", "Film 2", "overview", "Beskrivning 2")
                )
        ));

        // Testa /api/movies/search?query=Film
        mockMvc.perform(get("/api/movies/search").param("query", "Film"))
                .andExpect(status().isOk()) // Förvänta sig statuskod 200
                .andExpect(jsonPath("$.results[0].title").value("Film 1")) // Förvänta sig rätt titel för första sökresultatet
                .andExpect(jsonPath("$.results[0].overview").value("Beskrivning 1")) // Förvänta sig rätt beskrivning för första sökresultatet
                .andExpect(jsonPath("$.results[1].title").value("Film 2")) // Förvänta sig rätt titel för andra sökresultatet
                .andExpect(jsonPath("$.results[1].overview").value("Beskrivning 2")); // Förvänta sig rätt beskrivning för andra sökresultatet
    }
}