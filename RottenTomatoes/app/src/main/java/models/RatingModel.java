package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by logan on 2/27/16.
 */
public class RatingModel {

    @JsonProperty("ratingID")
    public int ratingID;

    @JsonProperty("rating")
    public int rating;

    @JsonProperty("text")
    public String text;

    @JsonProperty("movieTitle")
    public String movieTitle;

    @JsonProperty("user")
    public String user;

    /**
     * set up a rating model
     * @param rID
     * @param r
     * @param t
     * @param mT
     * @param u
     */
    public RatingModel(int rID, int r, String t, String mT, String u) {
        ratingID = rID;
        rating = r;
        text = t;
        movieTitle = mT;
        user = u;
    }

    /**
     * empty init for Jackson compatibility
     */
    public RatingModel() {}
}
