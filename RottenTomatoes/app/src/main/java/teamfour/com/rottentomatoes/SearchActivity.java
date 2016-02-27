package teamfour.com.rottentomatoes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.List;

import models.MovieListModel;
import models.MovieModel;
import models.RatingModel;
import otto.BusSubscriberActivity;
import services.APIService;
import services.APIServiceInterface;
import services.MovieService;
import services.RatingService;
import services.RottenTomatoesInterface;
import services.RottenTomatoesService;
import views.MovieListAdapter;

/**
 * Created by wbtho on 2/20/2016.
 */
public class SearchActivity extends BusSubscriberActivity {

    RottenTomatoesInterface tomatoService;
    boolean isSearchActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_search);

        tomatoService = RottenTomatoesService.getService();
    }

    /**
     * Press search to receive a movie that fits your query
     * @param view
     */
    public void pressedSearch(View view) {
        EditText query = (EditText) findViewById(R.id.SearchQuery);
        String search = query.getText().toString();

        // when user scrolls down to the bottom, call an event that iterates this number!
        Log.d("PRESSED SEARCH", "search is " + search);
        MovieService.searchMovies(tomatoService, search);
    }

    /**
     * Asynchronously receive list of movies upon successful movie search
     * @param list
     */
    @Subscribe
    public void getMoviesEvent(MovieListModel list) {
        if (isSearchActive) {
            Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "Search Successful",
                    Toast.LENGTH_SHORT
            );
            toast.show();

            ListView lv= (ListView) findViewById(R.id.listView2);
            lv.setAdapter(new MovieListAdapter(this, list.movies));
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                    MovieModel item = (MovieModel) adapter.getItemAtPosition(position);
                    Log.d("movieModel", item.toString());

                    // Intent intent = new Intent(Activity.this, destinationActivity.class);
                    // startActivity(intent);

                }

            });
        }
    }


    public void onPressMovieItem(View view) {


    }
}