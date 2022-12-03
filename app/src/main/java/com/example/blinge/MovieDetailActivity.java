package com.example.blinge;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.blinge.databinding.ActivityMovieDetailBinding;
import com.example.blinge.models.Movies;

import org.parceler.Parcels;

public class MovieDetailActivity extends AppCompatActivity {

    private ActivityMovieDetailBinding activityMovieDetailBinding;
    private static final String TAG = "MovieDetailActivity";
    Movies movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMovieDetailBinding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(activityMovieDetailBinding.getRoot());

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movies) Parcels.unwrap(getIntent().getParcelableExtra(Movies.class.getSimpleName()));
        Log.d(TAG, String.format("Showing details for '%s'", movie.getTitle()));

        activityMovieDetailBinding.tvTitle.setText(movie.getTitle());
        activityMovieDetailBinding.tvOverview.setText(movie.getOverview());
        int radius = 25; // corner radius, higher value = more rounded
        String imageUrl;
        // if phone is in landscape
        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // then imageUrl = back drop image
            imageUrl = movie.getBackdropPath();
        } else {
            // else imageUrl = poster image
            imageUrl = movie.getPosterPath();
        }
        Glide.with(getApplicationContext())
                .load(imageUrl)
                .transform(new RoundedCorners(radius))
                .into(activityMovieDetailBinding.ivPoster);
        // vote average is currently between 0 to 10, so convert it to be between 0 to 5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        activityMovieDetailBinding.rbVoteAverage.setRating(voteAverage/2.0f);
    }
}
