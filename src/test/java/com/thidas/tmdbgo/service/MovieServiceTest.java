package com.thidas.tmdbgo.service;

import com.thidas.tmdbgo.client.TmdbClient;
import com.thidas.tmdbgo.model.MovieDto;
import com.thidas.tmdbgo.model.TmdbResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private TmdbClient tmdbClient;

    @InjectMocks
    private MovieService movieService;

    private final MovieDto demoMovie = new MovieDto(
            "Star Wars",
            "A long time ago in a galaxy far far away...",
            "2015-12-18",
            8.6,
            23456,
            "en"
    );

    @Test
    void shouldReturnMovieTitle(){

        TmdbResponse mockResponse = new TmdbResponse(List.of(demoMovie));

        when(tmdbClient.searchMovies("Star Wars")).thenReturn(mockResponse);
        List<MovieDto> result = movieService.search("Star Wars");

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getTitle()).isEqualTo("Star Wars");
    }
}
