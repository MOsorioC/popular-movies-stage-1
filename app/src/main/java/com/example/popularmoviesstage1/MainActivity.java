package com.example.popularmoviesstage1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.popularmoviesstage1.adapters.MovieListAdapter;
import com.example.popularmoviesstage1.model.Movie;
import com.example.popularmoviesstage1.services.MovieAsyncTask;
import com.example.popularmoviesstage1.services.MovieService;
import com.example.popularmoviesstage1.services.OnTaskCompleted;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int NUM_OF_COLUMNS = 2;
    private final String POPULAR_QUERY = "popular";
    private final String TOP_RATED_QUERY = "top_rated";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnTaskCompleted onTaskCompleted;
    private MovieService movieService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mLayoutManager = new GridLayoutManager(this, NUM_OF_COLUMNS);
        this.mRecyclerView = findViewById(R.id.recycler_movies);
        mRecyclerView.setLayoutManager(mLayoutManager);
        movieService = new MovieService("2cbcc01e92b0af5a59142efe75cfe7fb");

        this.onTaskCompleted = new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(List<Movie> movieList) {
                mRecyclerView.setAdapter(new MovieListAdapter(movieList));
            }
        };

        new MovieAsyncTask(movieService, "2cbcc01e92b0af5a59142efe75cfe7fb", onTaskCompleted).execute(POPULAR_QUERY);
    }
}
