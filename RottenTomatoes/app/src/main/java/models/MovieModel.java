package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by logan on 2/21/16.
 */
public class MovieModel {
    @JsonProperty("title")
    public String title;

    @JsonProperty("genre")
    public String genre;

    @JsonProperty("year")
    public int year;

    // TODO: implement Parcelable!

    public MovieModel() {}
}
