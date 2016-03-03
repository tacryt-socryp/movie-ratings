package teamfour.com.rottentomatoes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import models.MovieModel;
import models.RatingModel;
import models.RatingsModel;
import models.UserModel;
import otto.BusSubscriberActivity;
import services.APIServiceInterface;
import services.MovieService;
import services.RatingService;
import services.UserService;
import views.MovieListAdapter;
import views.RatingListAdapter;

/**
 * Created by logan on 2/27/16.
 */
public class MovieActivity extends BusSubscriberActivity {

    APIServiceInterface service;
    MovieModel currentMovie;
    UserModel currentUser;
    RatingModel[] ratings;

    /**
     * initialize view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        service = RatingService.getService();
        Parcelable[] parcelableRatings = this.getIntent().getParcelableArrayExtra("ratings");
        if (parcelableRatings != null) {
            ratings = new RatingModel[parcelableRatings.length];
            for(int i = 0; i < parcelableRatings.length; i++) {
                ratings[i] = (RatingModel) parcelableRatings[i];
            }
        }
        currentUser = (UserModel) this.getIntent().getParcelableExtra("user");
        currentMovie = (MovieModel) this.getIntent().getParcelableExtra("movie");
        RatingService.getRatings(service, currentMovie.title);
    }

    /**
     * rate movie button was pressed
     * @param view
     */
    public void pressedRateMovie(View view) {
        EditText ratingNum = (EditText) findViewById(R.id.ratingNumber);
        EditText text = (EditText) findViewById(R.id.RatingText);
        if (ratingNum.getText() != null && ratingNum.getText().length() > 0) {
            RatingModel rating = new RatingModel(-1,
                    Integer.parseInt(ratingNum.getText().toString(), 10), text.getText().toString(),
                    currentMovie.title, currentUser.username
            );

            RatingService.createRating(service, rating);
        }
    }

    /**
     * get a rating that you just made
     * @param ratingModel
     */
    @Subscribe
    public void getCreatedRating(RatingModel ratingModel) {
        RatingService.getRatings(service, currentMovie.title);
    }

    /**
     * get async ratings event from server
     * @param ratingsModel
     */
    @Subscribe
    public void getRatingsEvent(RatingsModel ratingsModel) {
        if (ratingsModel.movieTitle.equals(currentMovie.title)) {
            ratings = ratingsModel.ratings;
        }

        Toast toast = Toast.makeText(
                this.getApplicationContext(),
                "get ratings Successful",
                Toast.LENGTH_SHORT
        );
        toast.show();

        ListView lv= (ListView) findViewById(R.id.ratingListView);
        List<RatingModel> ratingList = new ArrayList<RatingModel>();
        if (ratings != null) {
            for (RatingModel rating: ratings) {
                ratingList.add(rating);
            }
        }
        lv.setAdapter(new RatingListAdapter(this, ratingList, ratingsModel.movieTitle));
        // modify the list items individually based on events
    }

}
