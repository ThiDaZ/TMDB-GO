package com.thidas.tmdbgo.model;

import java.util.ArrayList;

public class TmdbResponse {
    ArrayList<MovieDto> results;

    public TmdbResponse() {
    }

    public TmdbResponse(ArrayList<MovieDto> results) {
        this.results = results;
    }

    public ArrayList<MovieDto> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieDto> results) {
        this.results = results;
    }
}
