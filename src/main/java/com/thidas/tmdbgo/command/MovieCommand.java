package com.thidas.tmdbgo.command;

import com.thidas.tmdbgo.model.MovieDto;
import com.thidas.tmdbgo.model.MovieType;
import com.thidas.tmdbgo.service.MovieService;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
public class MovieCommand {

    private final MovieService movieService;
    private final ConcurrentMapCacheManager cacheManager;

    public MovieCommand(MovieService movieService, ConcurrentMapCacheManager cacheManager) {
        this.movieService = movieService;
        this.cacheManager = cacheManager;
    }

    @ShellMethod(key = "tmdb-app", value="Main entry point for TMDB movie lists")
    public String tmdbApp(
            @ShellOption(value = "--type", defaultValue = ShellOption.NULL, help = "Type of list: PLAYING, POPULAR, TOP, UPCOMING")
            MovieType type,

            @ShellOption(value = "--search",defaultValue = ShellOption.NULL, help = "Search for a movie title")
            String searchQuery
    ){

        if(type == null && searchQuery ==null){
            return "\uD83D\uDEAB Error: you must provie either --type or --search.";
        }

        if(type !=null && searchQuery != null ){
            return "\uD83D\uDEAB Error: Please use only one option at a time.";
        }

        if(searchQuery !=null){
            List<MovieDto> movies = movieService.search(searchQuery);
            if(movies.isEmpty()){
                return "\uD83D\uDEAB No results found for "+ searchQuery;
            }
            return renderMovieTable(movies);
        }

        List<MovieDto> movies = switch (type){
            case TOP -> movieService.topMovies();
            case PLAYING -> movieService.nowPlaying();
            case POPULAR -> movieService.popular();
            case UPCOMING -> movieService.upcoming();
        };

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
