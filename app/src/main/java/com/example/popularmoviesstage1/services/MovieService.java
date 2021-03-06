package com.example.popularmoviesstage1.services;

import android.net.Uri;

import com.example.popularmoviesstage1.model.Movie;
import com.example.popularmoviesstage1.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieService implements IMovieService {

    private final String API_KEY;

    private final String SORT_BY_PARAM = "sort_by";
    private final String API_KEY_PARAM = "api_key";

    private final String KEY_RESULTS = "results";
    private final String KEY_ORIGINAL_TITLE = "original_title";
    private final String KEY_POSTER_PATH = "poster_path";
    private final String KEY_OVERVIEW = "overview";
    private final String KEY_VOTE_AVERAGE = "vote_average";
    private final String KEY_RELEASE_DATE = "release_date";

    public MovieService(String apiKey) {
        this.API_KEY = apiKey;
    }

    @Override
    public String getMoviePosterUrl(Movie movie) {
        return String.format("%s/%s", Constants.IMAGE_BASE_URL, movie.getPosterPath());
    }

    @Override
    public URL getMovieURL(String sortBy) throws MalformedURLException {
        Uri uri = Uri.parse(Constants.BASE_URL).buildUpon()
                .appendQueryParameter(SORT_BY_PARAM, sortBy)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        return  new URL(uri.toString());

    }

    @Override
    public List<Movie> getMoviesFromJSON(String json) throws JSONException {
        //get array from results
        JSONObject moviesJson = new JSONObject(json);
        JSONArray resultsArray = moviesJson.getJSONArray(KEY_RESULTS);

        // Create list of Movie objects that stores data from the JSON string
        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < resultsArray.length(); i++) {
            // Initialize each object before it can be used
            Movie movie = new Movie();

            /*
             * The response contain null values like a string before to parse the json object
             * this validate that the object index is not null
             */
            if (resultsArray.getString(i).equals("null")) {
                continue;
            }


            JSONObject movieInfo = resultsArray.getJSONObject(i);

            movie.setTitle(movieInfo.has(KEY_ORIGINAL_TITLE) ? movieInfo.getString(KEY_ORIGINAL_TITLE) : "");
            movie.setPosterPath(movieInfo.has(KEY_POSTER_PATH) ? movieInfo.getString(KEY_POSTER_PATH) : "");
            movie.setOverview(movieInfo.has(KEY_OVERVIEW) ? movieInfo.getString(KEY_OVERVIEW) : "");
            movie.setVoteAverage(movieInfo.has(KEY_VOTE_AVERAGE) ? movieInfo.getDouble(KEY_VOTE_AVERAGE) : 0d);
            movie.setReleaseDate(movieInfo.has(KEY_RELEASE_DATE) ? movieInfo.getString(KEY_RELEASE_DATE) : "");

            // The response for the poster path can be a null string {@link https://developers.themoviedb.org/3/discover/movie-discover}
            if (movie.getPosterPath() == null || movie.getPosterPath().equals("null")) {
                continue;
            }

            movies.add(movie);
        }

        return movies;
    }

    @Override
    public HttpURLConnection getConnection(URL movieAPiURL) throws IOException {
        return (HttpURLConnection) movieAPiURL.openConnection();
    }
}
