package com.thidas.tmdbgo.client;

import com.thidas.tmdbgo.model.TmdbResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TmdbClient {

    private final RestClient restClient;

    public TmdbClient(RestClient.Builder builder, TmdbProperties tmdbProperties) {
        this.restClient = builder
                .baseUrl(tmdbProperties.getBaseUrl())
                .defaultHeader("Authorization", "Bearer " + tmdbProperties.getApiKey())
                .build();
    }

    public TmdbResponse searchMovies(String query) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .body(TmdbResponse.class);
    }

    public TmdbResponse nowPlayingMovies() {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/now_playing")
                        .build())
                .retrieve()
                .body(TmdbResponse.class);
    }

    public TmdbResponse upcomingMovies() {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/upcoming")
                        .build())
                .retrieve()
                .body(TmdbResponse.class);
    }

    public TmdbResponse topMovies() {
        return restClient.get().uri(uriBuilder -> uriBuilder
                        .path("/movie/top_rated")
                        .build())
                .retrieve()
                .body(TmdbResponse.class);
    }

}
