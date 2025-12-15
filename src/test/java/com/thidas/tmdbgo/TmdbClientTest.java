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

    @Test
    void shouldUpcomingMovies(){
        stubFor(get(urlPathEqualTo("/movie/upcoming"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                "results": [
                                    {
                                      "original_language": "en",
                                      "overview": "Two sisters find an ancient vinyl that gives birth to bloodthirsty demons that run amok in a Los Angeles apartment building and thrusts them into a primal battle for survival as they face the most nightmarish version of family imaginable.",
                                      "release_date": "2023-04-12",
                                      "title": "Evil Dead Rise",
                                      "vote_average": 7,
                                      "vote_count": 207
                                    }]
                                }
                                """))
        );

        TmdbResponse response = tmdbClient.upcomingMovies();

        assertThat(response.getResults()).hasSize(1);
        MovieDto movie = response.getResults().getFirst();
        assertThat(movie.getTitle()).isEqualTo("Evil Dead Rise");
        assertThat(movie.getOverview()).isNotBlank();
        assertThat(movie.getRelease_date()).isEqualTo("2023-04-12");
        assertThat(movie.getVote_average()).isEqualTo(7);
    }

    @Test
    void shouldTopMovies(){
        stubFor(get(urlPathEqualTo("/movie/top_rated"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                "results": [
                                    {
                                      "original_language": "en",
                                      "overview": "Imprisoned in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
                                      "release_date": "1994-09-23",
                                      "title": "The Shawshank Redemption",
                                      "vote_average": 8.713,
                                      "vote_count": 29353
                                    }
                                ]}
                                """)
        ));

        TmdbResponse response = tmdbClient.topMovies();

        assertThat(response.getResults()).hasSize(1);
        MovieDto movie = response.getResults().getFirst();
        assertThat(movie.getTitle()).isEqualTo("The Shawshank Redemption");
        assertThat(movie.getOverview()).isNotBlank();
        assertThat(movie.getVote_count()).isEqualTo(29353);
        assertThat(movie.getVote_average()).isEqualTo(8.713);
    }

    @Test
    void shouldPoplarMovies(){
        stubFor(get(urlPathEqualTo("/movie/popular"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                "results": [
                                    {
                                      "original_language": "en",
                                      "overview": "Super-Hero partners Scott Lang and Hope van Dyne, along with with Hope's parents Janet van Dyne and Hank Pym, and Scott's daughter Cassie Lang, find themselves exploring the Quantum Realm, interacting with strange new creatures and embarking on an adventure that will push them beyond the limits of what they thought possible.",
                                      "release_date": "2023-02-15",
                                      "title": "Ant-Man and the Wasp: Quantumania",
                                      "vote_average": 6.5,
                                      "vote_count": 1886
                                    }]}
                                """)
                ));

        TmdbResponse response = tmdbClient.popularMovies();

        assertThat(response.getResults()).hasSize(1);
        MovieDto movie = response.getResults().getFirst();

        assertThat(movie.getTitle()).isEqualTo("Ant-Man and the Wasp: Quantumania");
        assertThat(movie.getOverview()).isNotBlank();
        assertThat(movie.getVote_average()).isEqualTo(6.5);
        assertThat(movie.getVote_count()).isEqualTo(1886);
    }
}
