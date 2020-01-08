package com.example.popularmoviesstage1.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesstage1.R;
import com.example.popularmoviesstage1.model.Movie;
import com.example.popularmoviesstage1.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter  extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    List<Movie> movies;

    public MovieListAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.image_view_layout, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        holder.bind(Constants.IMAGE_BASE_URL + movies.get(position).getPosterPath());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: CREATE DETAIL VIEW
            }
        });
    }

    @Override
    public int getItemCount() {
        if (movies == null)
            return 0;

        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            imageView.setAdjustViewBounds(true);
        }

        void bind(String moviePath) {
            Picasso.get().load(moviePath).into(imageView);
        }
    }
}
