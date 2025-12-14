package com.thidas.tmdbgo.command;

import com.thidas.tmdbgo.model.MovieDto;
import com.thidas.tmdbgo.service.MovieService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class MovieCommand {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);
    private final MovieService movieService;
    private final ConcurrentMapCacheManager cacheManager;

    public MovieCommand(MovieService movieService, ConcurrentMapCacheManager cacheManager) {
        this.movieService = movieService;
        this.cacheManager = cacheManager;
    }

    @ShellMethod(key = "search", value = "Search for a movie")
    public String search(String title){

        if (title.isBlank()){
            return "Enter Movie title to search!";
        }

        List<MovieDto> movies = movieService.search(title);

        if(movies.isEmpty()){
            return "\uD83D\uDEAB No results found for "+ title;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Found ").append(movies.size()).append(" movies: \n ");
        sb.append("------------------------------------------------ \n ");

        for(MovieDto movie : movies){
            String year = movie.getRelease_date() != null && movie.getRelease_date().length() >= 4
                    ? movie.getRelease_date().substring(0, 4)
                    : "N/A";
            sb.append(String.format(" \uD83C\uDFA5 %-30s | \uD83D\uDCC5 %s | ⭐ %.1f \n ",
                    truncate(movie.getTitle(), 30),
                    year,
                    movie.getVote_average()

            ));
        }
        return sb.toString();
    }

    private String truncate(String input, int width) {
        if (input.length() > width) {
            return input.substring(0, width - 3) + "...";
        }
        return input;
    }

    @ShellMethod(key = "clear-cache", value = "Clear the movie search cache")
    public String clearCache(){
        Cache movieCache = cacheManager.getCache("movies");
        if(movieCache != null){
            movieCache.clear();
            return "Cache cleared!";
        }
        return "Cache not found.";
    }

}
