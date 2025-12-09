package com.thidas.tmdbgo.client;

import com.thidas.tmdbgo.model.TmdbResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TmdbClient {

    private final RestClient restClient;
    private final TmdbProperties tmdbProperties;

    public TmdbClient(RestClient.Builder builder, TmdbProperties tmdbProperties) {
        this.tmdbProperties = tmdbProperties;

        this.restClient = builder
                .baseUrl(tmdbProperties.getBaseUrl())
                .build();
    }

    public TmdbResponse searchMovies(String query) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("query", query)
                        .queryParam("api_key", tmdbProperties.getApiKey())
                        .build())
                .retrieve()
                .body(TmdbResponse.class);
    }

}
