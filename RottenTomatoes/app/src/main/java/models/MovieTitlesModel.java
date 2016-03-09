package models;

import android.graphics.Movie;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jeremy on 3/8/16.
 */
public class MovieTitlesModel {

    @JsonProperty("movieTitleID")
    public int movieTitleID;

    @JsonProperty("movieTitles")
    public String[] movieTitles = new String[10];

    public MovieTitlesModel(int id, String[] movies) {
        movieTitleID = id;
        movieTitles = movies;
    }

    public MovieTitlesModel() {

    }
}
