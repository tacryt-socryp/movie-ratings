package teamfour.com.rottentomatoes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.MovieListModel;
import models.MovieModel;
import models.MovieTitlesModel;
import models.UserModel;
import services.APIService;
import services.APIServiceInterface;
import services.MovieService;
import services.RottenTomatoesInterface;
import services.RottenTomatoesService;
import views.MovieListAdapter;

/**
 * Created by Jeremy on 3/8/16.
 */
public class RecommendationActivity extends UserActivity {

    APIServiceInterface recService;
    RottenTomatoesInterface tomatoService;
    boolean isFirstTime = true;
    private UserModel currentUser;
    List<MovieModel> recommendedMovies;
    Map<String, Integer> titleToPosition;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {


        super.onCreate(savedInstanceBundle);

        final FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        // inflate the custom activity layout
        final LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View activityView = layoutInflater.inflate(R.layout.activity_recommendation, null,false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        currentUser = (UserModel) this.getIntent().getParcelableExtra("user");
        tomatoService = RottenTomatoesService.getService();
        recService = APIService.getService();
        recommendedMovies = new ArrayList<MovieModel>();
        titleToPosition = new HashMap<>();
    }

    /**
     * Press search to receive a movie that fits your query
     * @param view
     */
    public void pressedRecommend(View view) {
        recommendedMovies = new ArrayList<MovieModel>();
        setupList(recommendedMovies);
        final EditText query = (EditText) findViewById(R.id.RecommendationQuery);
        final String search = query.getText().toString();

        final EditText otherField = (EditText) findViewById(R.id.OtherQuery);
        final String other = otherField.getText().toString();

        // when user scrolls down to the bottom, call an event that iterates this number!
        MovieService.searchMovieTitlesToQuery(recService, search, other);
    }

    /**
     *         //hands in a string array to deal with the titles that are returned
     //should iterate through the list and call the searchMovies(movies)
     //this will take the strings and get the prominent data
     * @param list
     */
    @Subscribe
    public void getMovieTitlesEvent(MovieTitlesModel list) {


        for (String movieTitle : list.movieTitles) {
            final MovieModel m = new MovieModel();
            m.title = movieTitle;
            recommendedMovies.add(m);
            titleToPosition.put(movieTitle, recommendedMovies.size() - 1);
            MovieService.searchMovies(tomatoService, movieTitle);
        }
    }

    /**
     * Asynchronously receive list of movies upon successful movie search
     * @param list
     */
    @Subscribe
    public void getMoviesEvent(MovieListModel list) {
        //should add the first movie to recommendedMovies
        if (list.movies.size() > 0) {
            final MovieModel newMovie = list.movies.get(0);
            if (titleToPosition.containsKey(newMovie.title)) {
                final int position = titleToPosition.get(newMovie.title);
                recommendedMovies.set(position, newMovie);
            }
        }

        setupList(recommendedMovies);
    }

    /**
     * use a list of movies to setup a list view
     * @param list
     */
    public void setupList(List<MovieModel> list) {
        final ListView lv = (ListView) findViewById(R.id.listView);

        if (isFirstTime) {
            final Activity self = this;

            //should this ListView be moved somewhere else in the code?????
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                    final MovieModel item = (MovieModel) adapter.getItemAtPosition(position);

                    final Intent intent = new Intent(self, MovieActivity.class);
                    final MovieModel movieExtra = (MovieModel) adapter.getItemAtPosition(position);
                    intent.putExtra("movie", movieExtra);
                    intent.putExtra("ratings", movieExtra.ratings);
                    intent.putExtra("user", currentUser);
                    startActivity(intent);

                }

            });
            isFirstTime = false;
        }

        final MovieListAdapter adapter = new MovieListAdapter(this, list);
        lv.setAdapter(adapter);

    }
}
