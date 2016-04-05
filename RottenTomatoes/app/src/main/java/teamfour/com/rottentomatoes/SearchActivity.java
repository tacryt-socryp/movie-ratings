package teamfour.com.rottentomatoes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import java.util.List;

import models.MovieListModel;
import models.MovieModel;
import models.UserModel;
import services.MovieService;
import services.RottenTomatoesInterface;
import services.RottenTomatoesService;
import views.MovieListAdapter;

/**
 * Created by wbtho on 2/20/2016.
 */
public class SearchActivity extends UserActivity {
    /**
     * RottenTomatoesInterface
     */
    private RottenTomatoesInterface tomatoService;
    /**
     * isSearchActive
     */
    private boolean isSearchActive = true;
    /**
     * currentUser
     */
    private UserModel currentUser;

    /**
     * Needed this for JUnit test
     * movies
     */
    public List<MovieModel> movies;

    /**
     * Needed this for JUnit test
     * gotMovies
     */
    public boolean gotMovies = false;

    @Override
    protected final void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);

        final FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        // inflate the custom activity layout
        final LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View activityView = layoutInflater.inflate(R.layout.activity_search, null, false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        currentUser = (UserModel) this.getIntent().getParcelableExtra("user");
        tomatoService = RottenTomatoesService.getService();
    }

    /**
     * Press search to receive a movie that fits your query
     * @param view view
     */
    public final void pressedSearch(View view) {
        final EditText query = (EditText) findViewById(R.id.SearchQuery);
        final String search = query.getText().toString();

        // when user scrolls down to the bottom, call an event that iterates this number!
        MovieService.searchMovies(tomatoService, search);
    }

    /**
     * Created for testing
     * @param search search
     */
    public final void getSearch(String search) {
        MovieService.searchMovies(tomatoService, search);
    }

    /**
     * Asynchronously receive list of movies upon successful movie search
     * @param list list
     */
    @Subscribe
    public final void getMoviesEvent(MovieListModel list) {
        if (isSearchActive) {
            this.gotMovies = true;
            this.movies = list.movies;
            final Activity self = this;
            final ListView lv= (ListView) findViewById(R.id.listView2);
            final MovieListAdapter adapter = new MovieListAdapter(this, list.movies);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                    final MovieModel item = (MovieModel) adapter.getItemAtPosition(position);
                    Log.d("movieModel", item.toString());
                    final Intent intent = new Intent(self, MovieActivity.class);
                    final MovieModel movieExtra = (MovieModel) adapter.getItemAtPosition(position);
                    intent.putExtra("movie", movieExtra);
                    intent.putExtra("ratings", movieExtra.ratings);
                    intent.putExtra("user", currentUser);
                    startActivity(intent);
                }

            });
        }
    }

    /**
     * Created for testing
     * @return List<MovieModel> movies
     */
    public final List<MovieModel> getMovies() {
        return movies;
    }
}