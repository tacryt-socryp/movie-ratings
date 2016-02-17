package services;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import models.*;

/**
 * Created by logan on 2/10/16.
 */
public class UserService extends APIService {

    /*private static ErrorModel errorConverter(ResponseBody errorBody) {
        String message = "";
        try {
            message = errorBody.string();
        } catch(java.io.IOException e) {}
        return new Gson().fromJson(message, ErrorModel.class);
    }*/

    public static void createUser(APIServiceInterface service, UserModel userModel) {
        service.createUser(userModel).enqueue(
                new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        Log.d("serviceCall", String.valueOf(response.code()) + ", " + response.message());
                        if (response.isSuccess()) {
                            Log.d("serviceCall", response.body().toString());
                            // UserModel um = userConverter(response.body());
                            bus.post(response.body());
                        } else {
                            ObjectMapper om = new ObjectMapper();
                            try {
                                ErrorModel em = om.readValue(response.errorBody().toString(), ErrorModel.class);
                                bus.post(em);
                            } catch (Exception e) {
                                Log.d("stuff", e.toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Log.d("serviceCall", "got a failure!");
                        Log.d("serviceCall", t.toString());
                        Log.d("serviceCall", t.getMessage());
                    }
                }
        );
    }

    public static void getUser(APIServiceInterface service, UserModel userModel) {
        service.getUser(userModel.username, userModel.password).enqueue(
                new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        Log.d("serviceCall", String.valueOf(response.code()) + ", " + response.message());
                        if (response.isSuccess()) {
                            bus.post(response.body());
                        } else {
//                            ErrorModel em = errorConverter(response.errorBody());
//                            bus.post(em);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Log.d("serviceCall", "got a failure!");
                        Log.d("serviceCall", t.toString());
                        Log.d("serviceCall", t.getMessage());
                    }
                }
        );
    }

    public static void updateUser(APIServiceInterface service, UserModel userModel) {
        service.updateUser(
                userModel.username,
                userModel.password,
                new ProfileModel(
                        userModel.profile.name,
                        userModel.profile.profileID
                )).enqueue(
                new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        Log.d("serviceCall", String.valueOf(response.code()) + ", " + response.message());
                        if (response.isSuccess()) {
                            bus.post(response.body());
                        } else {
//                            ErrorModel em = errorConverter(response.errorBody());
//                            bus.post(em);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Log.d("serviceCall", "got a failure!");
                        Log.d("serviceCall", t.toString());
                        Log.d("serviceCall", t.getMessage());
                    }
                }
        );
    }

    public static void deleteUser(APIServiceInterface service, UserModel userModel) {
        service.deleteUser(userModel.username, userModel.password).enqueue(
                new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        Log.d("serviceCall", String.valueOf(response.code()) + ", " + response.message());
                        if (response.isSuccess()) {
                            bus.post(response.body());
                        } else {
//                            ErrorModel em = errorConverter(response.errorBody());
//                            bus.post(em);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Log.d("serviceCall", "got a failure!");
                        Log.d("serviceCall", t.toString());
                        Log.d("serviceCall", t.getMessage());
                    }
                }
        );
    }
}