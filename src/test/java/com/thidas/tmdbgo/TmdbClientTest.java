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
             .withQueryParam("api_key", matching(".*"))
             .willReturn(aResponse()
                     .withHeader("Content-Type", "application/json")
                     .withBody("""
                             {
                             "results": [
                                {
                                    "original_title": "Star Wars: The Force Awakens",
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
        assertThat(movie.getOriginal_title()).isEqualTo("Star Wars: The Force Awakens");
        assertThat(movie.getOverview()).isNotBlank();
        assertThat(movie.getRelease_date()).isEqualTo("2015-12-18");
        assertThat(movie.getVote_average()).isEqualTo(8.6);
    }

}
