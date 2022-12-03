package com.example.blinge.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel // annotation indicates class is Parcelable
public class Movies {

    // fields must be public for parceler
    public String backdropPath;
    public String posterPath;
    public String title;
    public String overview;
    public Double voteAverage;

    // no argument, empty constructor required for Parceler
    public Movies() {}

    public Movies(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        backdropPath = jsonObject.getString("backdrop_path");
        voteAverage = jsonObject.getDouble("vote_average");
    }

    public static List<Movies> fromJsonArray(JSONArray moviesJsonArray) throws JSONException {
        List<Movies> movie = new ArrayList<>();
        for (int i = 0; i < moviesJsonArray.length(); i++){
            movie.add(new Movies(moviesJsonArray.getJSONObject(i)));
        }
        return movie;
    }

    public String getPosterPath() {
        return String.format("http://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("http://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

}
