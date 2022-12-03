package com.example.blinge.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.blinge.MovieDetailActivity;
import com.example.blinge.databinding.ItemMovieBinding;
import com.example.blinge.models.Movies;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context;
    List<Movies> movies;
    private ItemMovieBinding itemMovieBinding;
    private static final String TAG = "MovieAdapter";


    public MovieAdapter(Context context, List<Movies> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        itemMovieBinding = ItemMovieBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemMovieBinding);
    }

    //Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder" + position);
        Movies movie = movies.get(position);
        holder.bind(movie);
    }

    // return the total count of items
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemMovieBinding itemMovieBinding;

        public ViewHolder(@NonNull ItemMovieBinding itemMovieBinding) {
            super(itemMovieBinding.getRoot());
            this.itemMovieBinding = itemMovieBinding;
            itemView.setOnClickListener(this); // attach a click listener to the entire row view
        }

        public void bind(Movies movie) {
            itemMovieBinding.tvTitle.setText(movie.getTitle());
            itemMovieBinding.tvOverview.setText(movie.getOverview());
            int radius = 25; // corner radius, higher value = more rounded
            String imageUrl;
            // if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // then imageUrl = back drop image
                imageUrl = movie.getBackdropPath();
            } else {
                // else imageUrl = poster image
                imageUrl = movie.getPosterPath();
            }
            Glide.with(context)
                        .load(imageUrl)
                        .transform(new RoundedCorners(radius))
                        .into(itemMovieBinding.ivPoster);

        }

        @Override
        public void onClick(View view) { // handles the row being clicked
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Movies movie = movies.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetailActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movies.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);
            }
        }
    }
}