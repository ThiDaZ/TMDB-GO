package com.thidas.tmdbgo;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.thidas.tmdbgo.client.TmdbClient;
import com.thidas.tmdbgo.model.MovieDto;
import com.thidas.tmdbgo.model.TmdbResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@WireMockTest(httpPort = 8081)
@ActiveProfiles("test")
public class TmdbClientTest {

    @Autowired
    private TmdbClient tmdbClient;

    @Test
    void shouldSearchMovies() {
     stubFor(get(urlPathEqualTo("/search/movie"))
             .withQueryParam("query", equalTo("Star Wars"))

             .willReturn(aResponse()
                     .withHeader("Content-Type", "application/json")
                     .withBody("""
                             {
                             "results": [
                                {
                                    "title": "Star Wars: The Force Awakens",
                                    "overview": "Twenty-year-old , a junior high school student, is transferred to the Force when a mysterious cyborg called the Rise Of Skywalker appears in his dreams.",
                                    "original_language": "en",
                                    "release_date": "2015-12-18",
                                    "vote_count": 10000,
                                    "vote_average": 8.6
                                }
                             ]}
                             """))
     );

     TmdbResponse response = tmdbClient.searchMovies("Star Wars");

     assertThat(response.getResults()).hasSize(1);
     MovieDto movie = response.getResults().getFirst();
     assertThat(movie.getTitle()).isEqualTo("Star Wars: The Force Awakens");
     assertThat(movie.getOverview()).isNotBlank();
     assertThat(movie.getRelease_date()).isEqualTo("2015-12-18");
     assertThat(movie.getVote_average()).isEqualTo(8.6);
    }

    @Test
    void shouldNowPlayingMovies(){
        stubFor(get(urlPathEqualTo("/movie/now_playing"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                "results":[
                                 {
                                      "original_language": "en",
                                      "overview": "After cracking the biggest case in Zootopia's history, rookie cops Judy Hopps and Nick Wilde find themselves on the twisting trail of a great mystery when Gary De’Snake arrives and turns the animal metropolis upside down. To crack the case, Judy and Nick must go undercover to unexpected new parts of town, where their growing partnership is tested like never before.",
                                      "release_date": "2025-11-26",
                                      "title": "Zootopia 2",
                                      "vote_average": 7.683,
                                      "vote_count": 562
                                 }
                                ]}
                                """))
        );

        TmdbResponse response = tmdbClient.nowPlayingMovies();

        assertThat(response.getResults()).hasSize(1);
        MovieDto movie = response.getResults().getFirst();
        assertThat(movie.getTitle()).isEqualTo("Zootopia 2");
        assertThat(movie.getOverview()).isNotBlank();
        assertThat(movie.getRelease_date()).isEqualTo("2025-11-26");
        assertThat(movie.getVote_average()).isEqualTo(7.683);
    }
}
