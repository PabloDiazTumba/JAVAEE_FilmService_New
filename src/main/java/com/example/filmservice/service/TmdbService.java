package com.example.filmservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class TmdbService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // Hämta och sortera populära filmer från TMDB efter betyg
    public List<Map<String, Object>> getPopularMoviesSortedByRating() {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/movie/popular")
                    .queryParam("api_key", apiKey)
                    .toUriString();

            // Hämta filmer från TMDB
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            List<Map<String, Object>> movies = (List<Map<String, Object>>) response.get("results");

            // Sortera filmerna efter betyg (vote_average)
            if (movies != null) {
                movies.sort(Comparator.comparingDouble(m -> (Double) m.get("vote_average")));
                Collections.reverse(movies); // Högsta betyg först
            }

            return movies;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // Returnera en tom lista vid fel
        }
    }

    // Hämta alla filmer
    public List<Map<String, Object>> getAllMovies() {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/movie/popular") // Justera vid behov
                    .queryParam("api_key", apiKey)
                    .toUriString();

            // Hämta filmer från TMDB
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            return (List<Map<String, Object>>) response.get("results");
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // Returnera en tom lista vid fel
        }
    }

    // Sök filmer
    public Map<String, Object> searchMovies(String query) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/search/movie")
                    .queryParam("api_key", apiKey)
                    .queryParam("query", query)
                    .toUriString();

            // Hämta sökresultat från TMDB
            return restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap(); // Returnera en tom karta vid fel
        }
    }
}