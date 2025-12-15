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

    private static final Logger logger = LoggerFactory.getLogger(MovieCommand.class);
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

        return renderMovieTable(movies);
    }

    @ShellMethod(key = "now-playing", value = "Showing now playing movies in theaters")
    public String nowPlaying(){

        List<MovieDto> movies = movieService.nowPlaying();

        if(movies.isEmpty()){
            return "\uD83D\uDEAB No results found";
        }
        return renderMovieTable(movies);
    }

    @ShellMethod(key = "upcoming-movies", value = "Showing new upcoming movies")
    public String upcoming(){
        List<MovieDto> movies = movieService.upcoming();

        if(movies.isEmpty()){
            return "\uD83D\uDEAB No results found";
        }
        return renderMovieTable(movies);
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

    private String renderMovieTable(List<MovieDto> movies) {
        StringBuilder sb = new StringBuilder();
        String header = String.format("| %-40s | %-10s | %-8s |%n", "TITLE", "RELEASED", "RATING");
        String separator = "+------------------------------------------+------------+----------+\n";

        sb.append("\n");
        sb.append(separator);
        sb.append(header);
        sb.append(separator);

        for (MovieDto movie : movies) {
            String title = truncate(movie.getTitle(), 38);
            String date = (movie.getRelease_date() != null) ? movie.getRelease_date() : "N/A";
            String rating = String.format("⭐ %.1f", movie.getVote_average());

            sb.append(String.format("| %-40s | %-10s | %-7s |%n", title, date, rating));
        }

        sb.append(separator);
        return sb.toString();
    }

}
