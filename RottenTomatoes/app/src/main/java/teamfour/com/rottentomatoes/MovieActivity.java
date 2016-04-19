package teamfour.com.rottentomatoes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
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
public class MovieActivity extends UserActivity {

    /**
     * APIServiceInterface
     */
    private APIServiceInterface service;
    /**
     * MovieModel
     */
    private MovieModel currentMovie;
    /**
     * UserModel
     */
    private UserModel currentUser;
    /**
     * RatingModel array
     */
    private RatingModel[] ratings;

    /**
     * initialize view
     * @param savedInstanceState savedInstance
     */
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        // inflate the custom activity layout
        final LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View activityView = layoutInflater.inflate(R.layout.activity_movie, null,false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);

        service = RatingService.getService();
        final Parcelable[] parcelableRatings = this.getIntent().getParcelableArrayExtra("ratings");
        if (parcelableRatings != null) {
            ratings = new RatingModel[parcelableRatings.length];
            for(int i = 0; i < parcelableRatings.length; i++) {
                ratings[i] = (RatingModel) parcelableRatings[i];
            }
        }
        currentUser = (UserModel) this.getIntent().getParcelableExtra("user");
        currentMovie = (MovieModel) this.getIntent().getParcelableExtra("movie");
        RatingService.getRatings(service, currentMovie.title);

        final TextView desc = (TextView) findViewById(R.id.description);
        desc.setText("Title: " + currentMovie.title + "\nReleased: " + currentMovie.year);

        new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute(currentMovie.poster);
        welcomeText.setText("");
    }

    /**
     * rate movie button was pressed *
     * @param view view
     */
    public final void pressedRateMovie(View view) {
        final EditText ratingNum = (EditText) findViewById(R.id.ratingNumber);
        final EditText text = (EditText) findViewById(R.id.RatingText);

        if (ratingNum.getText() != null && ratingNum.getText().length() > 0) {
            final int integerRadix = 10;
            final RatingModel rating = new RatingModel(-1,
                    Integer.parseInt(ratingNum.getText().toString(), integerRadix), text.getText().toString(),
                    currentMovie.title, currentUser.username, currentUser.profile.major
            );

            Log.d("ratingStuff", currentUser.profile.major);
            RatingService.createRating(service, rating);
        }
    }

    /**
     * get a rating that you just made *
     * @param ratingModel ratingModel
     */
    @Subscribe
    public final void getCreatedRating(RatingModel ratingModel) {
        RatingService.getRatings(service, currentMovie.title);
    }

    /**
     * get async ratings event from server *
     * @param ratingsModel ratingsModel
     */
    @Subscribe
    public final void getRatingsEvent(RatingsModel ratingsModel) {
        if (ratingsModel.movieTitle.equals(currentMovie.title)) {
            ratings = ratingsModel.ratings;
        }

        final Toast toast = Toast.makeText(
                this.getApplicationContext(),
                "get ratings Successful",
                Toast.LENGTH_SHORT
        );
        toast.show();

        final ListView lv = (ListView) findViewById(R.id.ratingListView);
        final List<RatingModel> ratingList = new ArrayList<RatingModel>();
        if (ratings != null) {
            for (RatingModel rating : ratings) {
                ratingList.add(rating);
            }
        }
        lv.setAdapter(new RatingListAdapter(this, ratingList, ratingsModel.movieTitle));
        // modify the list items individually based on event);
    }

    /**
     * download movie image using an async task
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        /**
         * bmImage
         */
        private ImageView bmImage;

        /**
         * Default constructor for downloaded image
         * @param newImage newImage
         */
        public DownloadImageTask(ImageView newImage) {
            this.bmImage = newImage;
        }

        /**
         * Asynchronously gets the image from online
         * @param urls urls
         * @return Bitmap
         */
        protected Bitmap doInBackground(String... urls) {
            final String url = urls[0];
            Bitmap img = null;
            try {
                final InputStream in = new java.net.URL(url).openStream();
                img = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return img;
        }

        /**
         * Sets image
         * @param result result
         */
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}