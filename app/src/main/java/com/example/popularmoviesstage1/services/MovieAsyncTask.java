package com.example.popularmoviesstage1.services;

import android.os.AsyncTask;

import com.example.popularmoviesstage1.model.Movie;

public class MovieAsyncTask extends AsyncTask<String, Void, Movie[]> {

    MovieService movieService;
    private final String API_KEY;

    public MovieAsyncTask(MovieService movieService, String apiKey) {
        this.movieService = movieService;
        this.API_KEY = apiKey;
    }

    @Override
    protected Movie[] doInBackground(String... strings) {
        return new Movie[0];
    }
}
