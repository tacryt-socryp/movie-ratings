package services;

import android.util.Log;

import models.MovieModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by logan on 2/21/16.
 */
public class MovieService extends RottenTomatoesService {

    //static String API_KEY = "yedukp76ffytfuy24zsqk7f5";

    /**
     * Send in a generated service along with a valid UserModel, perform a server call
     * @param service
     * @param searchTitle
     */
    public static void searchMovies(RottenTomatoesInterface service, String searchTitle) {
        Log.d("IN MOVIE SERVICE", "searching for movies...");
        Log.d("SEARCHING FOR", searchTitle);
        service.getSearch(searchTitle).enqueue(
                //new Callback<ArrayList<MovieModel>>() {
                new Callback<MovieModel>() {
                    @Override
                    //public void onResponse(Call<ArrayList<MovieModel>> call, Response<ArrayList<MovieModel>> response) {
                    public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                        Log.d("tomatoesCall", String.valueOf(response.code()) + ", " + response.message());
                        if (response.isSuccess()) {
                            bus.post(response.body());
                            Log.d("tomatoesCall", response.body().toString());
                        } else {
                            Log.d("tomatoesCall", response.errorBody().toString());
                            // TODO: If you want to see error messages, figure out the format rotten tomatoes is sending back and make an error converter.
                        }
                    }

                    @Override
                    //public void onFailure(Call<ArrayList<MovieModel>> call, Throwable t) {
                    public void onFailure(Call<MovieModel> call, Throwable t) {
                        Log.d("serviceCall", "got a failure!");
                        Log.d("serviceCall", t.toString());
                        Log.d("serviceCall", t.getMessage());
                    }
                }
        );
    }
}
