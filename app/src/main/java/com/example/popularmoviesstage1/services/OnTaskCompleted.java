package com.example.popularmoviesstage1.services;

import com.example.popularmoviesstage1.model.Movie;

import java.util.List;

public interface OnTaskCompleted {
    void onTaskCompleted(List<Movie> movieList);
}
