package services;

import java.util.List;

import models.UserModel;

import retrofit2.Call;
import retrofit2.http.*;


/**
 * Created by logan on 2/6/16.
 */
public interface APIServiceInterface {
    @POST("users")
    Call<UserModel> createUser(@Body UserModel user);

    @GET("users/{username}")
    Call<UserModel> getUser(@Path("username") String username, @Header("Password") String password);

    @FormUrlEncoded
    @POST("users/{username}")
    Call<UserModel> updateUser(@Path("username") String username, @Header("password") String password);

}

