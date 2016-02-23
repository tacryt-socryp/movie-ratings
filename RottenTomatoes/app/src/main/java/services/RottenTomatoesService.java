package services;

import com.squareup.otto.Bus;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


/**
 * Created by wbtho on 2/20/2016.
 */
public class RottenTomatoesService {
    private static final String API_BASE_URL = "http://www.omdbapi.com/";
    //private static final String API_BASE_URL = "http://www.omdbapi.com/?";
    //private static final String API_BASE_URL = "http://api.rottentomatoes.com/api/public/";
    private static RottenTomatoesInterface service = null;
    static protected Bus bus;

    // initialize bus should occur before any of the other methods are called
    // initBus occurs in App, only needs to happen once
    public static void initBus(Bus newBus) {
        bus = newBus;
    }

    static public RottenTomatoesInterface getService() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            service = retrofit.create(RottenTomatoesInterface.class);
            return service;
        } else {
            return service;
        }
    }
}
