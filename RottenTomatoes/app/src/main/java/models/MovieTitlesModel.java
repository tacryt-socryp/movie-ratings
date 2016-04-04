package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

/**
 * Created by Jeremy on 3/8/16.
 */
public class MovieTitlesModel {


    @JsonProperty("movieTitles")
    public String[] movieTitles;

    public MovieTitlesModel(String[] movies) {
        if(movies == null) {
            this.movieTitles = new String[0];
        } else {
            this.movieTitles = Arrays.copyOf(movies, movies.length);
        }
    }

    public MovieTitlesModel() {

    }
}
