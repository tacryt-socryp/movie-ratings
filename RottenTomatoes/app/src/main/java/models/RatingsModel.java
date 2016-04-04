package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by logan on 2/27/16.
 */
public class RatingsModel {

    /**
     * movieTitle
     */
    @JsonProperty("movieTitle")
    public String movieTitle;

    /**
     * ratings
     */
    @JsonProperty("ratings")
    public RatingModel[] ratings;

    /**
     * empty init for Jackson compatibility
     */
    public RatingsModel() {}
}
