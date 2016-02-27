package teamfour.com.rottentomatoes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.squareup.otto.Subscribe;

import models.MovieModel;
import models.RatingModel;
import models.RatingsModel;
import models.UserModel;
import otto.BusSubscriberActivity;
import services.APIServiceInterface;
import services.MovieService;
import services.RatingService;
import services.UserService;

/**
 * Created by logan on 2/27/16.
 */
public class MovieActivity extends BusSubscriberActivity {

    APIServiceInterface service;
    MovieModel currentMovie;
    UserModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        service = RatingService.getService();
        currentMovie = (MovieModel) this.getIntent().getParcelableExtra("movie");
    }

    public void pressedRateMovie(View view) {
        EditText ratingNum = (EditText) findViewById(R.id.ratingNumber);
        EditText text = (EditText) findViewById(R.id.RatingText);
        RatingModel rating = new RatingModel(-1,
                Integer.parseInt(ratingNum.getText().toString(), 10), text.getText().toString(),
                currentMovie.title, currentUser.username
        );
        
        RatingService.createRating(service, rating);
    }


    @Subscribe
    public void getRatingsEvent(RatingsModel ratings) {
        if (ratings.movieTitle.equals(currentMovie.title)) {
            currentMovie.ratings = ratings.ratings;
        }
        // modify the list items individually based on events
    }

}
