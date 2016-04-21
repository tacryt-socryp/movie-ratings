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
import android.widget.RadioButton;

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

    /**
     * APIServiceInterface
     */
    private APIServiceInterface recService;
    /**
     * RottenTomatoesInterface
     */
    private RottenTomatoesInterface tomatoService;
    /**
     * isFirstTime
     */
    private boolean isFirstTime = true;
    /**
     * currentUser
     */
    private UserModel currentUser;
    /**
     * List of recommendedMovies
     */
    private List<MovieModel> recommendedMovies;

    /**
     *
     * @return RecommendedMovies
     */
    public final List<MovieModel> getRecommendedMovies() {
        return this.recommendedMovies;
    }

    /**
     * titleToPosition map
     */
    private Map<String, Integer> titleToPosition;

    /**
     *
     * @return mapTitlePosition
     */
    public final Map<String, Integer> getTitleToPosition() {
        return this.titleToPosition;
    }

    @Override
    protected final void onCreate(Bundle savedInstanceBundle) {


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


        ((RadioButton) findViewById(R.id.radio_overview)).setChecked(true);
        ((EditText) findViewById(R.id.OtherQuery)).setText(currentUser.profile.major);
        ((EditText) findViewById(R.id.OtherQuery)).setVisibility(View.INVISIBLE);
    }

    /**
     * Press search to receive a movie that fits your query
     * @param view view
     */
    public final void pressedRecommend(View view) {
        recommendedMovies = new ArrayList<MovieModel>();
        setupList(recommendedMovies);
        boolean isOverview = ((RadioButton) findViewById(R.id.radio_overview)).isChecked();
        final String recommendationMode = isOverview ? "overview" : "major";

        final EditText otherField = (EditText) findViewById(R.id.OtherQuery);
        final String other = otherField.getText().toString();

        // when user scrolls down to the bottom, call an event that iterates this number!
        MovieService.searchMovieTitlesToQuery(recService, recommendationMode, other);
    }

    /**
     *         //hands in a string array to deal with the titles that are returned
     //should iterate through the list and call the searchMovies(movies)
     //this will take the strings and get the prominent data
     * @param list list
     */
    @Subscribe
    public final void getMovieTitlesEvent(MovieTitlesModel list) {
        this.addToMovieTitle(list);
    }

    /**
     * add to movie title
     * @param list list
     */
    public void addToMovieTitle(MovieTitlesModel list) {
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
     * @param list list
     */
    @Subscribe
    public final void getMoviesEvent(MovieListModel list) {
        this.addToRecommendedMovies(list);
        this.setupList(recommendedMovies);
    }

    /**
     * add to recommended movies
     * @param list list
     */
    public void addToRecommendedMovies(MovieListModel list) {
        //should add the first movie to recommendedMovies
        if (list.movies.size() > 0) {
            final MovieModel newMovie = list.movies.get(0);
            if (titleToPosition.containsKey(newMovie.title)) {
                final int position = titleToPosition.get(newMovie.title);
                recommendedMovies.set(position, newMovie);
            }
        }
    }

    /**
     * use a list of movies to setup a list view
     * @param list list
     */
    public final void setupList(List<MovieModel> list) {
        final ListView lv = (ListView) findViewById(R.id.listView);

        if (isFirstTime) {
            final Activity self = this;

            //should this ListView be moved somewhere else in the code?????
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        final EditText otherField = (EditText) findViewById(R.id.OtherQuery);

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_overview:
                if (checked) {
                    otherField.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.radio_major:
                if (checked) {
                    otherField.setVisibility(View.VISIBLE);

                }
                break;
        }
    }
}
