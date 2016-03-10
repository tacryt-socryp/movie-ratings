package teamfour.com.rottentomatoes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import models.MovieModel;
import models.RatingModel;
import models.RatingsModel;
import models.UserModel;
import otto.BusSubscriberActivity;
import services.APIServiceInterface;
import services.RatingService;
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

        TextView desc = (TextView) findViewById(R.id.description);
        desc.setText("Title: " + currentMovie.title + "\nReleased: " + currentMovie.year);

        new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute(currentMovie.poster);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap img = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                img = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return img;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    /**
     * rate movie button was pressed *
     * @param view
     */
    public void pressedRateMovie(View view) {
        EditText ratingNum = (EditText) findViewById(R.id.ratingNumber);
        EditText text = (EditText) findViewById(R.id.RatingText);
        if (ratingNum.getText() != null && ratingNum.getText().length() > 0) {
            RatingModel rating = new RatingModel(-1,
                    Integer.parseInt(ratingNum.getText().toString(), 10), text.getText().toString(),
                    currentMovie.title, currentUser.username, currentUser.profile.major
            );

            Log.d("ratingStuff", currentUser.profile.major);
            RatingService.createRating(service, rating);
        }
    }

    /**
     * get a rating that you just made *
     * @param ratingModel
     */
    @Subscribe
    public void getCreatedRating(RatingModel ratingModel) {
        RatingService.getRatings(service, currentMovie.title);
    }

    /**
     * get async ratings event from server *
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

        ListView lv = (ListView) findViewById(R.id.ratingListView);
        List<RatingModel> ratingList = new ArrayList<RatingModel>();
        if (ratings != null) {
            for (RatingModel rating : ratings) {
                ratingList.add(rating);
            }
        }
        lv.setAdapter(new RatingListAdapter(this, ratingList, ratingsModel.movieTitle));
        // modify the list items individually based on event);
    }
}