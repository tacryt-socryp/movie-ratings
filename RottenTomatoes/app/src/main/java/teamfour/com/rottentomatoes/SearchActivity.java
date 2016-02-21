package teamfour.com.rottentomatoes;

import retrofit2.Retrofit;
import services.*;
import otto.*;

import android.os.Bundle;
import android.widget.EditText;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;



/**
 * Created by wbtho on 2/20/2016.
 */
public class SearchActivity extends BusSubscriberActivity {

    APIServiceInterface service;
    public final String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    public final String API_BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0.json?apikey=[" + API_KEY + "]";
    public final String ENDPOINT = "http://api.rottentomatoes.com";
    public static List<Movie> movieList;

    private Call<List<Movie>> call;



    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_search);
    }

    public void requestData(View view) {
        EditText query = (EditText) findViewById(R.id.SearchQuery);
        String search = query.toString();
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(API_BASE_URL + "&q=" + search)
                .build();
        RottenTomatoesInterface api = adapter.create(RottenTomatoesInterface.class);
        api.getSearch("Test", new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                movieList = response.body();
                setContentView(R.layout.activity_search_list);

            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
            }
        });
        System.out.println(movieList);
    }


}
