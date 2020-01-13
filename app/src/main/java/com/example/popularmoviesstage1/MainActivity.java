package com.example.popularmoviesstage1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.popularmoviesstage1.adapters.MovieListAdapter;
import com.example.popularmoviesstage1.model.Movie;
import com.example.popularmoviesstage1.services.MovieAsyncTask;
import com.example.popularmoviesstage1.services.MovieService;
import com.example.popularmoviesstage1.services.OnTaskCompleted;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private final int NUM_OF_COLUMNS = 2;
    private final String POPULAR_QUERY = "popularity.desc";
    private final String TOP_RATED_QUERY = "vote_average.desc";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnTaskCompleted onTaskCompleted;
    private MovieService movieService;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mLayoutManager = new GridLayoutManager(this, NUM_OF_COLUMNS);
        this.mRecyclerView = findViewById(R.id.recycler_movies);
        mRecyclerView.setLayoutManager(mLayoutManager);
        movieService = new MovieService("");
        this.context = this;

        this.onTaskCompleted = new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(List<Movie> movieList) {
                mRecyclerView.setAdapter(new MovieListAdapter(context, movieList));
            }
        };

        if (isConnected()) {
            new MovieAsyncTask(movieService, onTaskCompleted).execute(POPULAR_QUERY);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                if (isConnected()) {
                    new MovieAsyncTask(movieService, onTaskCompleted).execute(POPULAR_QUERY);
                }
                return true;
            case R.id.top_rated:
                if (isConnected()) {
                    new MovieAsyncTask(movieService, onTaskCompleted).execute(TOP_RATED_QUERY);
                }
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Determine if the user has internet connection
     * {http://developer.android.com/training/monitoring-device-state/connectivity-status-type}
     *
     * @return true if the user has internet connection
     */
    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();
        }

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }
}
