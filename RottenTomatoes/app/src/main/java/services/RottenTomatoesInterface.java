package services;

import java.util.List;

import javax.security.auth.callback.Callback;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import teamfour.com.rottentomatoes.Movie;

/**
 * Created by wbtho on 2/20/2016.
 */
public interface RottenTomatoesInterface {
    @GET("movies")
    void getSearch(String query, retrofit2.Callback<List<Movie>> callback);
    //Call<List<Movies>> getSearch(@Path("title") String title);
}
