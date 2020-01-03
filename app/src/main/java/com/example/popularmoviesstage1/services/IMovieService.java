package com.example.popularmoviesstage1.services;

import com.example.popularmoviesstage1.model.Movie;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public interface IMovieService {

    String getMoviePosterUrl(Movie movie);

    URL getMovieURL(String sortBy) throws MalformedURLException;

    List<Movie> getMoviesFromJSON(String json) throws JSONException;

    HttpURLConnection getConnection(URL movieAPiURL) throws IOException;
}
