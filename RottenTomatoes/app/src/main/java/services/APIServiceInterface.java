package services;

import models.MovieTitlesModel;
import models.ProfileModel;
import models.RatingModel;
import models.RatingsModel;
import models.UserListModel;
import models.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by logan on 2/6/16.
 */
public interface APIServiceInterface {

    /**
     * USERNAME
     */
    String USERNAME = "username";

    /**
     * createUser sends a UserModel to the REST API, and creates a new user in the database.
     * @param user user
     * @return user
     */
    @POST("users")
    Call<UserModel> createUser(@Body UserModel user);

    /**
     * getUser sends the username and password of a user, and receives the full user model back if the data is correct
     * otherwise, receives an error
     * @param username username
     * @param password password
     * @return user
     */
    @GET("users/{username}")
    Call<UserModel> getUser(@Path(USERNAME) String username, @Header("Password") String password);

    /**
     * updateUser allows people to send the username and password of a user along with a modified profile model for the user
     * updateUser only allows for the profile of the user to change, not the username or password.
     * @param username username
     * @param password password
     * @param profile profile
     * @return user
     */
    @PUT("users/{username}")
    Call<UserModel> updateUser(@Path(USERNAME) String username, @Header("password") String password, @Body ProfileModel profile);

    /**
     * deleteUser allows someone to delete a user
     * @param username username
     * @param password password
     * @return users
     */
    @DELETE("users/{username}")
    Call<UserModel> deleteUser(@Path(USERNAME) String username, @Header("password") String password);


    /**
     * get list of movie ratings for a movie title
     * @param movieTitle movieTitle
     * @return movie ratings
     */
    @GET("ratings/{movieTitle}")
    Call<RatingsModel> getRatings(@Path("movieTitle") String movieTitle);


    /**
     * Create a rating on the server from a rating model
     * @param rating rating
     * @return rating
     */
    @POST("ratings")
    Call<RatingModel> createRating(@Body RatingModel rating);

    /**
     * Filters movies by movie title
     * @param filterBy filterBy
     * @param other other
     * @return movie list
     */
    @GET("movie_titles/{filterBy}")
    Call<MovieTitlesModel> searchMovieTitlesToQuery(@Path("filterBy") String filterBy, @Query("other") String other);

    /**
     * Changes status of user
     * @param username username
     * @param shouldBan shouldBan
     * @return user
     */
    @GET("admin/ban/{username}")
    Call<UserModel> banOrUnbanUser(@Path(USERNAME) String username, @Query("shouldBan") Boolean shouldBan);

    /**
     * Returns user list
     * @return user list
     */
    @GET("admin/users")
    Call<UserListModel> viewUserList();
}

