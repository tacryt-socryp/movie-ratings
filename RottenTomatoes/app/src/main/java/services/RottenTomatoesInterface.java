package services;

import java.util.ArrayList;

import models.MovieModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wbtho on 2/20/2016.
 */
public interface RottenTomatoesInterface {
    String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    int resultsPerPage = 20;
    // &page_limit={results-per-page}&page={page-number}

    // @GET("v1.0.json/movies?apikey=" + API_KEY + "&page_limit=" + resultsPerPage)
//    Call<ArrayList<MovieModel>> getSearch(
//            @Query("q") String searchQuery,
//            @Query("page") String pageNumber,
//            @Query("page_limit") String pageLimit,
//            @Query("apikey") String apiKey
//    );
    @GET("v1.0.json/movies?q={q}" + "&page_limit=" + resultsPerPage + "&page={page}" + "&apikey=" + API_KEY)
    Call<ArrayList<MovieModel>> getSearch(
            @Query("q") String searchQuery,
            @Query("page") String pageNumber
    );

    @GET("v1.0/lists/dvds/new_releases.json?apikey=" + API_KEY)
    Call<ArrayList<MovieModel>> getNewReleases();

    @GET("v1.0/lists/movies/in_theatres.json?apikey=" + API_KEY)
    Call<ArrayList<MovieModel>> getInTheatres();
}
