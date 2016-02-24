package services;

import models.MovieModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wbtho on 2/20/2016.
 */
public interface RottenTomatoesInterface {

    /**
     * Get search method to connect with omdb api
     * @param searchQuery
     * @return
     */
    @GET("?")
    Call<MovieModel> getSearch(
            @Query("t") String searchQuery
    );
}
