package com.thidas.tmdbgo.model;

import java.util.List;

public class TmdbResponse {
    List<MovieDto> results;

    public TmdbResponse() {
    }

    public TmdbResponse(List<MovieDto> results) {
        this.results = results;
    }

    public List<MovieDto> getResults() {
        return results;
    }

    public void setResults(List<MovieDto> results) {
        this.results = results;
    }
}
