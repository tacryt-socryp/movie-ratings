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
    Call<Object> createUser(@Body UserModel user);

    @GET("users/{username}")
    Call<Object> getUser(@Path("username") String username, @Header("Password") String password);

    @PUT("users/{username}")
    Call<Object> updateUser(@Path("username") String username, @Header("password") String password);

    @DELETE("users/{username}")
    Call<Object> deleteUser(@Path("username") String username, @Header("password") String password);

}

