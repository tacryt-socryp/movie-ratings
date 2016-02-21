package services;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


/**
 * Created by wbtho on 2/20/2016.
 */
public class RottenTomatoesService{
    private final String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    private final String API_BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0.json?apikey=[" + API_KEY + "]";
    private Retrofit retrofit;
    private RottenTomatoesInterface service = retrofit.create(RottenTomatoesInterface.class);

    public RottenTomatoesService() {
        this.retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL).build();

    }
    private String getApiURL(String relative) {
        return API_BASE_URL + relative;
    }

}
