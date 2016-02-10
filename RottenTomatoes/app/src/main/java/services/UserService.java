package services;

import android.util.Log;

import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import models.*;

/**
 * Created by logan on 2/10/16.
 */
public class UserService extends APIService {

    private static UserModel userConverter(Object body) {
        return new Gson().fromJson(body.toString(), UserModel.class);
    }

    private static ErrorModel errorConverter(ResponseBody errorBody) {
        String message = "";
        try {
            message = errorBody.string();
        } catch(java.io.IOException e) {}
        return new Gson().fromJson(message, ErrorModel.class);
    }

    public static void createUser(APIServiceInterface service, UserModel userModel) {
        service.createUser(userModel).enqueue(
                new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Log.d("serviceCall", String.valueOf(response.code()) + ", " + response.message());
                        if (response.isSuccess()) {
                            UserModel um = userConverter(response.body());
                            bus.post(um);
                        } else {
                            ErrorModel em = errorConverter(response.errorBody());
                            bus.post(em);
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.d("serviceCall", "got a failure!");
                        Log.d("serviceCall", t.toString());
                        Log.d("serviceCall", t.getMessage());
                    }
                }
        );
    }

    public static void getUser(APIServiceInterface service, UserModel userModel) {
        service.getUser(userModel.username, userModel.password).enqueue(
                new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Log.d("serviceCall", String.valueOf(response.code()) + ", " + response.message());
                        if (response.isSuccess()) {
                            UserModel um = userConverter(response.body());
                            bus.post(um);
                        } else {
                            ErrorModel em = errorConverter(response.errorBody());
                            bus.post(em);
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.d("serviceCall", "got a failure!");
                        Log.d("serviceCall", t.toString());
                        Log.d("serviceCall", t.getMessage());
                    }
                }
        );
    }

    public static void updateUser(APIServiceInterface service, UserModel userModel) {
        service.updateUser(userModel.username, userModel.password).enqueue(
                new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Log.d("serviceCall", String.valueOf(response.code()) + ", " + response.message());
                        if (response.isSuccess()) {
                            UserModel um = userConverter(response.body());
                            bus.post(um);
                        } else {
                            ErrorModel em = errorConverter(response.errorBody());
                            bus.post(em);
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.d("serviceCall", "got a failure!");
                        Log.d("serviceCall", t.toString());
                        Log.d("serviceCall", t.getMessage());
                    }
                }
        );
    }

    public static void deleteUser(APIServiceInterface service, UserModel userModel) {
        service.deleteUser(userModel.username, userModel.password).enqueue(
                new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Log.d("serviceCall", String.valueOf(response.code()) + ", " + response.message());
                        if (response.isSuccess()) {
                            UserModel um = userConverter(response.body());
                            bus.post(um);
                        } else {
                            ErrorModel em = errorConverter(response.errorBody());
                            bus.post(em);
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.d("serviceCall", "got a failure!");
                        Log.d("serviceCall", t.toString());
                        Log.d("serviceCall", t.getMessage());
                    }
                }
        );
    }
}