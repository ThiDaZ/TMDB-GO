package com.thidas.tmdbgo.service;

import com.thidas.tmdbgo.client.TmdbClient;
import com.thidas.tmdbgo.model.MovieDto;
import com.thidas.tmdbgo.model.TmdbResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Collections;

@Service
public class MovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
    private final TmdbClient tmdbClient;

    public MovieService(TmdbClient tmdbClient) {
        this.tmdbClient = tmdbClient;
    }

    @Cacheable("movies")
    public List<MovieDto> search(String query) {
//        LOGGER.info("Searching for movie: {}", query);

        TmdbResponse response = tmdbClient.searchMovies(query);

        if(response.getResults() == null){
            return Collections.emptyList();
        }
        return response.getResults();
    }

    public List<MovieDto> nowPlaying(){
//        LOGGER.info("Getting now play movies");

        TmdbResponse response = tmdbClient.nowPlayingMovies();
        if(response.getResults() == null){
            return Collections.emptyList();
        }
        return response.getResults();
    }

    public List<MovieDto> upcoming(){
//        LOGGER.info("Getting upcoming movies");

        TmdbResponse response = tmdbClient.upcomingMovies();
        if(response.getResults() == null){
            return Collections.emptyList();
        }
        return response.getResults();
    }

    public List<MovieDto> topMovies(){
        LOGGER.info("Getting top rated movies");

        TmdbResponse response = tmdbClient.topMovies();
        if(response.getResults() == null){
            return Collections.emptyList();
        }
        return response.getResults();
    }

    public List<MovieDto> popular(){
//        LOGGER.info("Getting popular movies list");

        TmdbResponse response = tmdbClient.popularMovies();
        if(response.getResults().isEmpty()){
            return Collections.emptyList();
        }
        return response.getResults();
    }
}
