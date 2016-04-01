package services;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.otto.Bus;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import models.*;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by logan on 2/10/16.
 */
public class UserService {


    // we use this to publish changes to other objects
    static protected Bus bus;
    static protected APIServiceInterface service = null;
    static protected String baseUrl = "http://10.0.2.2:10010/api/"; // access the host computer. this expects the server to be running!

    // initialize bus should occur before any of the other methods are called
    // initBus occurs in App, only needs to happen once
    public static void initBus(Bus newBus) {
        bus = newBus;
    }

    /**
     * createService creates a Retrofit service for interacting with the team's REST API
     * @return APIServiceInterface
     */
    public static APIServiceInterface getService() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            service = retrofit.create(APIServiceInterface.class);
        }

        return service;
    }

    private UserService() {}

    /**
     * errorConverter is a private helper function for converting the response body from a server call
     * to an error model when an error is received
     * @param errorBody
     * @return
     */
    private static ErrorModel errorConverter(ResponseBody errorBody) {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.readValue(errorBody.string(), ErrorModel.class);
        } catch (Exception e) {
            Log.d("errorConverting", e.toString());
        }

        return new ErrorModel("Incorrect error format returned.");
    }

    /**
     * Send in a generated service along with a valid UserModel, perform a server call
     * @param service
     * @param userModel
     */
    public static void createUser(APIServiceInterface service, UserModel userModel) {
        service.createUser(userModel).enqueue(
                new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccess()) {
                            bus.post(response.body());
                        } else {
                            ErrorModel em = errorConverter(response.errorBody());
                            bus.post(em);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                    }
                }
        );
    }

    /**
     * Send in a generated service along with a valid UserModel, perform a server call
     * @param service
     * @param userModel
     */
    public static void getUser(APIServiceInterface service, UserModel userModel) {
        service.getUser(userModel.username, userModel.password).enqueue(
                new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccess()) {
                            bus.post(response.body());
                        } else {
                            ErrorModel em = errorConverter(response.errorBody());
                            bus.post(em);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                    }
                }
        );
    }

    /**
     * Send in a generated service along with a valid UserModel, perform a server call
     * @param service
     * @param userModel
     */
    public static void updateUser(APIServiceInterface service, UserModel userModel) {
        service.updateUser(
                userModel.username,
                userModel.password,
                new ProfileModel(
                        userModel.profile.name,
                        userModel.profile.major,
                        userModel.profile.profileID
                )).enqueue(
                new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccess()) {
                            bus.post(response.body());
                        } else {
                            ErrorModel em = errorConverter(response.errorBody());
                            bus.post(em);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                    }
                }
        );
    }

    /**
     * Send in a generated service along with a valid UserModel, perform a server call
     * @param service
     * @param userModel
     */
    public static void deleteUser(APIServiceInterface service, UserModel userModel) {
        service.deleteUser(userModel.username, userModel.password).enqueue(
                new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccess()) {
                            bus.post(response.body());
                        } else {
                            ErrorModel em = errorConverter(response.errorBody());
                            bus.post(em);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                    }
                }
        );
    }


    /**
     * Send in a generated service along with a valid UserModel, perform a server call
     * @param service
     */
    public static void banOrUnbanUser(APIServiceInterface service, String username, boolean shouldBlock) {
        service.banOrUnbanUser(username, shouldBlock).enqueue(
                new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccess()) {
                            bus.post(response.body());
                        } else {
                            ErrorModel em = errorConverter(response.errorBody());
                            bus.post(em);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                    }
                }
        );
    }

    /**
     * Send in a generated service along with a valid UserModel, perform a server call
     * @param service
     */
    public static void viewUserList(APIServiceInterface service) {
        Log.d("USER SERVICE", "Viewing user list");
        service.viewUserList().enqueue(
                new Callback<UserListModel>() {
                    @Override
                    public void onResponse(Call<UserListModel> call, Response<UserListModel> response) {
                        Log.d("serviceCall", response.code() + ", " + response.message());
                        if (response.isSuccess()) {
                            bus.post(response.body());
                        } else {
                            ErrorModel em = errorConverter(response.errorBody());
                            bus.post(em);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserListModel> call, Throwable t) {
                    }
                }
        );
    }
}