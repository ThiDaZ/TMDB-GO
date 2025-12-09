package com.thidas.tmdbgo.service;

import com.thidas.tmdbgo.client.TmdbClient;
import com.thidas.tmdbgo.model.MovieDto;
import com.thidas.tmdbgo.model.TmdbResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class MovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
    private final TmdbClient tmdbClient;

    public MovieService(TmdbClient tmdbClient) {
        this.tmdbClient = tmdbClient;
    }

    public ArrayList<MovieDto> search(String query) {
        LOGGER.info("Searching for movie: {}", query);

        TmdbResponse response = tmdbClient.searchMovies(query);

        if(response.getResults() == null){
            return new ArrayList<>();
        }
        return response.getResults();
    }

}
