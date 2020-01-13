package com.example.popularmoviesstage1.services;

import android.os.AsyncTask;
import android.util.Log;

import com.example.popularmoviesstage1.model.Movie;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MovieAsyncTask extends AsyncTask<String, Void, List<Movie>> {

    private final String TAG = MovieAsyncTask.class.getName();

    private final MovieService movieService;
    private final OnTaskCompleted onTaskCompleted;

    public MovieAsyncTask(MovieService movieService, OnTaskCompleted onTaskCompleted) {
        super();
        this.movieService = movieService;
        this.onTaskCompleted = onTaskCompleted;
    }

    @Override
    protected List<Movie> doInBackground(String... strings) {
        try {
            URL url = movieService.getMovieURL(strings[0]);

            HttpURLConnection connection = movieService.getConnection(url);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();

            if(inputStream == null){
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String jsonText;
            while ((jsonText = reader.readLine()) != null){
                stringBuilder.append(jsonText);
            }


            if(stringBuilder.length() == 0){
                return  null;
            }

            reader.close();
            connection.disconnect();

            return movieService.getMoviesFromJSON(stringBuilder.toString());
        } catch (MalformedURLException e) {
           Log.e(TAG, e.getMessage());
           return null;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
        onTaskCompleted.onTaskCompleted(movies);
    }
}
