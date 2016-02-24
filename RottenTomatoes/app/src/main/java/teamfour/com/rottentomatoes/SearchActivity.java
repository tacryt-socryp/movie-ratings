package teamfour.com.rottentomatoes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import models.MovieModel;
import otto.BusSubscriberActivity;
import services.MovieService;
import services.RottenTomatoesInterface;
import services.RottenTomatoesService;

/**
 * Created by wbtho on 2/20/2016.
 */
public class SearchActivity extends BusSubscriberActivity {

    RottenTomatoesInterface service;
    boolean isSearchActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_search);

        service = RottenTomatoesService.getService();
    }

    // TODO: On pause, make this inactive

    /**
     * Press search to receive a movie that fits your query
     * @param view
     */
    public void pressedSearch(View view) {
        EditText query = (EditText) findViewById(R.id.SearchQuery);
        String search = query.getText().toString();

        // when user scrolls down to the bottom, call an event that iterates this number!
        Log.d("PRESSED SEARCH", "search is " + search);
        MovieService.searchMovies(service, search);
    }

    /**
     * Asynchronously receive list of movies upon successful movie search
     * @param movie
     */
    @Subscribe
    public void getMoviesEvent(MovieModel movie) {
        if (isSearchActive) {
            Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "Search Successful",
                    Toast.LENGTH_SHORT
            );
            toast.show();

            TextView results = (TextView) findViewById(R.id.searchresult);
            results.setText("Title: " + movie.getTitle() + "\n" + "Year Released: " + movie.getYear()
            + "\n" + "Genre: " + movie.getGenre());
        }
    }
}