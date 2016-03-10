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
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import models.MovieListModel;
import models.MovieModel;
import models.MovieTitlesModel;
import models.UserModel;
import teamfour.com.rottentomatoes.MovieActivity;
import teamfour.com.rottentomatoes.R;
import teamfour.com.rottentomatoes.UserActivity;
import views.MovieListAdapter;
import services.*;

/**
 * Created by Jeremy on 3/8/16.
 */
public class RecommendationActivity extends UserActivity {

    APIServiceInterface recService;
    RottenTomatoesInterface tomatoService;
    boolean isFirstTime = true;
    private UserModel currentUser;
    List<MovieModel> recommendedMovies;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        System.out.println("Got to recommend");
        super.onCreate(savedInstanceBundle);

        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_recommendation, null,false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
        currentUser = (UserModel) this.getIntent().getParcelableExtra("user");
        tomatoService = RottenTomatoesService.getService();
        recService = APIService.getService();
        recommendedMovies = new ArrayList<MovieModel>();
    }

    /**
     * Press search to receive a movie that fits your query
     * @param view
     */
    public void pressedRecommend(View view) {
        System.out.println("Recommend pressed");
        EditText query = (EditText) findViewById(R.id.RecommendationQuery);
        String search = query.getText().toString();

        // when user scrolls down to the bottom, call an event that iterates this number!
        Log.d("PRESSED Recommend", "recommend is " + search);
        MovieService.searchMovieTitlesToQuery(recService, search, "");
    }

    @Subscribe
    public void getMovieTitlesEvent(MovieTitlesModel list) {
        //hands in a string array to deal with the titles that are returned
        //should iterate through the list and call the searchMovies(movies)
        //this will take the strings and get the prominent data
        for (int x = 0; x < list.movieTitles.length; x++) {
            MovieService.searchMovies(tomatoService, list.movieTitles[x]);
        }
    }

    /**
     * Asynchronously receive list of movies upon successful movie search
     * @param list
     */
    @Subscribe
    public void getMoviesEvent(MovieListModel list) {
        //should add the first movie to recommendedMovies
        recommendedMovies.add(list.movies.get(0));
        System.out.println("Adding..." + list.movies.get(0));

        setupList(recommendedMovies);
    }


    public void setupList(List<MovieModel> list) {

        if (isFirstTime) {
            Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "Search Successful",
                    Toast.LENGTH_SHORT
            );
            toast.show();
            System.out.println("Getting to list view");
            final Activity self = this;
            ListView lv = (ListView) findViewById(R.id.listView);
            MovieListAdapter adapter = new MovieListAdapter(this, list);
            lv.setAdapter(adapter);
            //should this ListView be moved somewhere else in the code?????
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
        isFirstTime = false;
    }
}
