package teamfour.com.rottentomatoes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.FrameLayout;

import com.squareup.otto.Subscribe;

import java.util.List;

import models.MovieListModel;
import models.MovieModel;
import models.RatingModel;
import models.UserModel;
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
public class SearchActivity extends UserActivity {

    RottenTomatoesInterface tomatoService;
    boolean isSearchActive = true;
    private UserModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        System.out.println("Got to search");
        super.onCreate(savedInstanceBundle);

        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_search, null,false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        currentUser = (UserModel) this.getIntent().getParcelableExtra("user");
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
        System.out.println("Made it to get Movies event");
        if (isSearchActive) {
            Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "Search Successful",
                    Toast.LENGTH_SHORT
            );
            toast.show();

            final Activity self = this;
            ListView lv= (ListView) findViewById(R.id.listView2);
            MovieListAdapter adapter = new MovieListAdapter(this, list.movies);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                    MovieModel item = (MovieModel) adapter.getItemAtPosition(position);
                    Log.d("movieModel", item.toString());

                    Intent intent = new Intent(self, MovieActivity.class);
                    MovieModel movieExtra = (MovieModel) adapter.getItemAtPosition(position);
                    intent.putExtra("movie", movieExtra);
                    intent.putExtra("ratings", movieExtra.ratings);
                    intent.putExtra("user", currentUser);
                    startActivity(intent);

                }

            });
        }
    }
}