package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jeremy on 3/8/16.
 */
public class MovieTitlesModel {


    @JsonProperty("movieTitles")
    public String[] movieTitles;

    public MovieTitlesModel(String[] movies) {
        movieTitles = movies;
    }

    public MovieTitlesModel() {

    }
}
