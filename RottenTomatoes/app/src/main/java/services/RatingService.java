package services;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.ErrorModel;
import models.RatingModel;
import models.RatingsModel;
import models.UserModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by logan on 2/27/16.
 */
public class RatingService extends APIService {

    /**
     * errorConverter is a private helper function for converting the response body from a server call
     * to an error model when an error is received
     * @param errorBody
     * @return
     */
    private static ErrorModel errorConverter(ResponseBody errorBody) {
        ObjectMapper om = new ObjectMapper();
        try {
            ErrorModel em = om.readValue(errorBody.string(), ErrorModel.class);
            return em;
        } catch (Exception e) {
            Log.d("errorConverting", e.toString());
        }

        return new ErrorModel("Incorrect error format returned.");
    }

    /**
     * Send in a generated service along with a valid RatingModel, perform a server call to create a movie rating
     * @param service
     * @param ratingModel
     */
    public static void createRating(APIServiceInterface service, RatingModel ratingModel) {
        service.createRating(ratingModel).enqueue(
                new Callback<RatingModel>() {
                    @Override
                    public void onResponse(Call<RatingModel> call, Response<RatingModel> response) {
                        Log.d("serviceCall", String.valueOf(response.code()) + ", " + response.message());
                        if (response.isSuccess()) {
                            Log.d("serviceCall", response.body().toString());
                            bus.post(response.body());
                        } else {
                            ErrorModel em = errorConverter(response.errorBody());
                            bus.post(em);
                        }
                    }

                    @Override
                    public void onFailure(Call<RatingModel> call, Throwable t) {
                        Log.d("serviceCall", "got a failure!");
                        Log.d("serviceCall", t.toString());
                        Log.d("serviceCall", t.getMessage());
                    }
                }
        );
    }

    /**
     * Send in a generated service along with a movie title, perform a server call to get ratings for that movie
     * @param service
     * @param movieTitle
     */
    public static void getRatings(APIServiceInterface service, String movieTitle) {
        service.getRatings(movieTitle).enqueue(
                new Callback<RatingsModel>() {
                    @Override
                    public void onResponse(Call<RatingsModel> call, Response<RatingsModel> response) {
                        Log.d("serviceCall", String.valueOf(response.code()) + ", " + response.message());
                        if (response.isSuccess()) {
                            bus.post(response.body());
                        } else {
                            ErrorModel em = errorConverter(response.errorBody());
                            bus.post(em);
                        }
                    }

                    @Override
                    public void onFailure(Call<RatingsModel> call, Throwable t) {
                        Log.d("serviceCall", "got a failure!");
                        Log.d("serviceCall", t.toString());
                        Log.d("serviceCall", t.getMessage());
                    }
                }
        );
    }
}
