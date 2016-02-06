package services;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import models.UserModel;

/**
 * Created by logan on 2/6/16.
 */
public class APIService {

    String baseUrl = "http://10.0.2.2:10010/"; // access the host computer. this expects the server to be running!

    public void createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIServiceInterface service = retrofit.create(APIServiceInterface.class);
        /*service.createUser(new UserModel("user", "pass")).enqueue(
                new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        Log.d("serviceCall", response.toString());
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Log.d("serviceCall", t.toString());
                    }
                }
        );*/
        service.getUser("user", "pass").enqueue(
                new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        Log.d("serviceCall", "got a response!");
                        // Log.d("serviceCall", response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Log.d("serviceCall", "got a failure!");
                        // Log.d("serviceCall", t.toString());
                    }
                }
        );
    }
}
