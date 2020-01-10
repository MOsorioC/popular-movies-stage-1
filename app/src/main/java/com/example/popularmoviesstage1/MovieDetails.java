package com.example.popularmoviesstage1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.popularmoviesstage1.model.Movie;
import com.example.popularmoviesstage1.utils.Constants;
import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

    private ImageView imageViewPoster;
    private RatingBar ratingBar;
    private TextView averageTextView;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView dateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        this.imageViewPoster = findViewById(R.id.img_poster);
        this.ratingBar = findViewById(R.id.rating);
        this.averageTextView = findViewById(R.id.tv_average);
        this.titleTextView = findViewById(R.id.tv_title);
        this.descriptionTextView = findViewById(R.id.tv_description);
        this.dateTextView = findViewById(R.id.tv_date);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(getString(R.string.movie_detail));
        if (movie != null) {
            inflateView(movie);
        }
    }

    private void inflateView(Movie movie) {
        setTitle(movie.getTitle());
        Picasso.get().load(Constants.IMAGE_BASE_URL + movie.getPosterPath()).into(imageViewPoster);
        titleTextView.setText(movie.getTitle());
        descriptionTextView.setText(movie.getOverview());
        dateTextView.setText(movie.getReleaseDate());

        float vote = (float) (movie.getVoteAverage() / 2);
        ratingBar.setIsIndicator(true);
        ratingBar.setStepSize(0.1f);
        ratingBar.setRating(vote);

        String average = movie.getVoteAverage() + "/10";
        averageTextView.setText(average);

    }
}
