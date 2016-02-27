package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by logan on 2/27/16.
 */
public class RatingsModel {

    @JsonProperty("movieTitle")
    public String movieTitle;

    @JsonProperty("ratings")
    public RatingModel[] ratings;

    /**
     * empty init for Jackson compatibility
     */
    public RatingsModel() {}
}
