package services;

import java.util.List;

import models.*;

import retrofit2.Call;
import retrofit2.http.*;


/**
 * Created by logan on 2/6/16.
 */
public interface APIServiceInterface {

    // NONE OF THESE FUNCTIONS SHOULD EVER BE CALLED DIRECTLY

    /**
     * createUser sends a UserModel to the REST API, and creates a new user in the database.
     * @param user
     * @return
     */
    @POST("users")
    Call<UserModel> createUser(@Body UserModel user);

    /**
     * getUser sends the username and password of a user, and receives the full user model back if the data is correct
     * otherwise, receives an error
     * @param username
     * @param password
     * @return
     */
    @GET("users/{username}")
    Call<UserModel> getUser(@Path("username") String username, @Header("Password") String password);

    /**
     * updateUser allows people to send the username and password of a user along with a modified profile model for the user
     * updateUser only allows for the profile of the user to change, not the username or password.
     * @param username
     * @param password
     * @param profile
     * @return
     */
    @PUT("users/{username}")
    Call<UserModel> updateUser(@Path("username") String username, @Header("password") String password, @Body ProfileModel profile);

    /**
     * deleteUser allows someone to delete a user
     * @param username
     * @param password
     * @return
     */
    @DELETE("users/{username}")
    Call<UserModel> deleteUser(@Path("username") String username, @Header("password") String password);


    /**
     * get list of movie ratings for a movie title
     * @param movieTitle
     * @return
     */
    @GET("ratings/{movieTitle}")
    Call<RatingsModel> getRatings(@Path("movieTitle") String movieTitle);


    /**
     * Create a rating on the server from a rating model
     * @param rating
     * @return
     */
    @POST("ratings")
    Call<RatingModel> createRating(@Body RatingModel rating);

}

