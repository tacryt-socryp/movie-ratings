package services;

import com.squareup.otto.Bus;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


/**
 * Created by wbtho on 2/20/2016.
 */
public final class RottenTomatoesService {
    /**
     * API_BASE_URL
     */
    private static final String API_BASE_URL = "http://www.omdbapi.com/";
    /**
     * RottenTomatoesInterface
     */
    private static RottenTomatoesInterface service = null;
    /**
     * Bus
     */
    protected static Bus bus;

    /**
     * initialize bus should occur before any of the other methods are called
     * initBus occurs in App, only needs to happen once
     * @param newBus newBus
     */
    public static void initBus(Bus newBus) {
        bus = newBus;
    }

    /**
     * Gets service
     * @return RottenTomatoesInterface
     */
    public static RottenTomatoesInterface getService() {
        if (service == null) {
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            service = retrofit.create(RottenTomatoesInterface.class);
            return service;
        } else {
            return service;
        }
    }

    /**
     * Empty constructor
     */
    private RottenTomatoesService() {}
}
