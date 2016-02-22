package teamfour.com.rottentomatoes;

import models.MovieModel;
import models.UserModel;
import retrofit2.Retrofit;
import services.*;
import otto.*;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by wbtho on 2/20/2016.
 */
public class SearchActivity extends BusSubscriberActivity {

    RottenTomatoesInterface service;
    boolean isSearchActive = true;
    int pageNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_search);

        service = RottenTomatoesService.getService();
    }

    // TODO: On pause, make this inactive

    public void pressedSearch(View view) {
        EditText query = (EditText) findViewById(R.id.SearchQuery);
        String search = query.toString();

        // when user scrolls down to the bottom, call an event that iterates this number!
        MovieService.searchMovies(service, search, String.valueOf(pageNumber));
    }

    /**
     * Asynchronously receive list of movies upon successful movie search
     * @param movies
     */
    @Subscribe
    public void getMoviesEvent(ArrayList<MovieModel> movies) {
        if (isSearchActive) {
            Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "Search Successful",
                    Toast.LENGTH_SHORT
            );
            toast.show();

            // TODO: set the list using movies
        }
    }



}
