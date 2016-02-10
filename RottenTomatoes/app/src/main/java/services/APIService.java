package services;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import com.squareup.otto.Bus;

import models.UserModel;

/**
 * Created by logan on 2/6/16.
 */
public class APIService {

    // we use this to publish changes to other objects
    static protected Bus bus;
    static protected String baseUrl = "http://10.0.2.2:10010/api/"; // access the host computer. this expects the server to be running!

    // initialize bus should occur before any of the other methods are called
    // initBus occurs in App, only needs to happen once
    public static void initBus(Bus newBus) {
        bus = newBus;
    }

    public static APIServiceInterface createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIServiceInterface service = retrofit.create(APIServiceInterface.class);
        return service;
    }

}
