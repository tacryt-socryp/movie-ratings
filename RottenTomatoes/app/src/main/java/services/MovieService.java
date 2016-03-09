package services;

import android.util.Log;

import models.MovieListModel;
import models.MovieTitlesModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by logan on 2/21/16.
 */
public class MovieService extends RottenTomatoesService {

    /**
     * Send in a generated service along with a valid UserModel, perform a server call
     *
     * @param service
     * @param searchTitle
     */
    public static void searchMovies(RottenTomatoesInterface service, String searchTitle) {
        Log.d("IN MOVIE SERVICE", "searching for movies...");
        Log.d("SEARCHING FOR", searchTitle);
        service.getSearch(searchTitle).enqueue(
                new Callback<MovieListModel>() {
                    @Override
                    public void onResponse(Call<MovieListModel> call, Response<MovieListModel> response) {
                        Log.d("tomatoesCall", String.valueOf(response.code()) + ", " + response.message());
                        if (response.isSuccess()) {
                            bus.post(response.body());
                            Log.d("tomatoesCall", response.body().toString());
                        } else {
                            Log.d("tomatoesCall", response.errorBody().toString());
                        }
                    }

                    @Override
                    //public void onFailure(Call<MovieModel> call, Throwable t) {
                    //public void onFailure(Call<ArrayList<MovieModel>> call, Throwable t) {
                    public void onFailure(Call<MovieListModel> call, Throwable t) {
                        Log.d("serviceCall", "got a failure!");
                        Log.d("serviceCall", t.toString());
                        Log.d("serviceCall", t.getMessage());
                    }
                }
        );
    }

    public static void searchMovieTitlesToQuery(APIServiceInterface service, String filterBy, String other) {
        Log.d("IN MOVIE SERVICE", "searching for movies...");
        Log.d("SEARCHING FOR", filterBy);
        service.searchMovieTitlesToQuery(filterBy, other).enqueue(
                new Callback<MovieTitlesModel>() {
                    @Override
                    public void onResponse(Call<MovieTitlesModel> call, Response<MovieTitlesModel> response) {
                        Log.d("tomatoesCall", String.valueOf(response.code()) + ", " + response.message());
                        if (response.isSuccess()) {
                            bus.post(response.body());
                            Log.d("tomatoesCall", response.body().toString());
                        } else {
                            Log.d("tomatoesCall", response.errorBody().toString());
                        }
                    }

                    @Override
                    //public void onFailure(Call<MovieModel> call, Throwable t) {
                    //public void onFailure(Call<ArrayList<MovieModel>> call, Throwable t) {
                    public void onFailure(Call<MovieTitlesModel> call, Throwable t) {
                        Log.d("serviceCall", "got a failure!");
                        Log.d("serviceCall", t.toString());
                        Log.d("serviceCall", t.getMessage());
                    }
                });
    }
}
