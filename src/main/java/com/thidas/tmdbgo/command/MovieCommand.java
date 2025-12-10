package com.thidas.tmdbgo.command;

import com.thidas.tmdbgo.model.MovieDto;
import com.thidas.tmdbgo.service.MovieService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
public class MovieCommand {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);
    private final MovieService movieService;

    public MovieCommand(MovieService movieService) {
        this.movieService = movieService;
    }

    @ShellMethod(key = "search", value = "Search for a movie")
    public String search(@ShellOption(defaultValue = "Star Wars")String title){
        List<MovieDto> movies = movieService.search(title);

        if(movies.isEmpty()){
            return "No results found for "+ title;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Found ").append(movies.size()).append(" movies: \n ");
        sb.append("------------------------------------------------ \n ");

        for(MovieDto movie : movies){
            String year = movie.getRelease_date() != null && movie.getRelease_date().length() >= 4
                    ? movie.getRelease_date().substring(0, 4)
                    : "N/A";
            sb.append(String.format(" \uD83C\uDFA5 %-30s | \uD83D\uDCC5 %s | ⭐ %.1f \n ",
                    truncate(movie.getOriginal_title(), 30),
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

}
