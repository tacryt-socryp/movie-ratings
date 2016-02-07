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

    String baseUrl = "http://10.0.2.2:10010/api/"; // access the host computer. this expects the server to be running!

    public void createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIServiceInterface service = retrofit.create(APIServiceInterface.class);

        /*service.deleteUser("user", "password2").enqueue(
                new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (!response.isSuccess() && response.errorBody() != null) {
                            try {
                                Log.d("serviceCall", response.errorBody().string());
                            } catch(java.io.IOException e) {}
                        }
                        Log.d("serviceCall", String.valueOf(response.code()) + ", " + response.message());
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.d("serviceCall", t.toString());
                        Log.d("serviceCall", t.getMessage());
                    }
                }
        );*/

        /*service.createUser(new UserModel("user", "password1")).enqueue(
                new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (!response.isSuccess() && response.errorBody() != null) {
                            try {
                                Log.d("serviceCall", response.errorBody().string());
                            } catch (java.io.IOException e) {
                            }
                        }
                        Log.d("serviceCall", String.valueOf(response.code()) + ", " + response.message());
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.d("serviceCall", t.toString());
                    }
                }
        );*/

        /*service.updateUser("user", "password1").enqueue(
                new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (!response.isSuccess() && response.errorBody() != null) {
                            try {
                                Log.d("serviceCall", response.errorBody().string());
                            } catch (java.io.IOException e) {
                            }
                        }
                        Log.d("serviceCall", String.valueOf(response.code()) + ", " + response.message());
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.d("serviceCall", t.toString());
                    }
                }
        );*/

        service.getUser("user", "password1").enqueue(
                new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (!response.isSuccess() && response.errorBody() != null) {
                            try {
                                Log.d("serviceCall", response.errorBody().string());
                            } catch(java.io.IOException e) {}
                        } else if (response.body() != null) {
                            Log.d("serviceCall", response.body().toString());
                        }
                        Log.d("serviceCall", String.valueOf(response.code()) + ", " + response.message());
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.d("serviceCall", "got a failure!");
                        // Log.d("serviceCall", t.toString());
                    }
                }
        );
    }
}
