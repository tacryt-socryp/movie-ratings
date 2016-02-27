package views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.otto.Bus;

import models.MovieModel;
import models.RatingModel;
import otto.BusSingleton;
import teamfour.com.rottentomatoes.R;

/**
 * Created by logan on 2/27/16.
 */
public class MovieListItemView extends RelativeLayout {
    public MovieModel movie = null;
    public RatingModel[] ratings = null;

    private TextView titleTextView;
    private TextView yearTextView;
    private TextView ratingTextView;

    public MovieListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.movie_item_layout_children, this, true);
        setupChildren();
    }

    private void setupChildren() {
        titleTextView = (TextView) findViewById(R.id.listTitleTextView);
        yearTextView = (TextView) findViewById(R.id.listYearTextView);
        ratingTextView = (TextView) findViewById(R.id.listRatingTextView);
    }

    public void setMovie(MovieModel movieModel) {
        movie = movieModel;
        titleTextView.setText(movieModel.title);
        yearTextView.setText(movieModel.year);
    }

    public void setRatings(RatingModel[] ratingModels) {
        ratings = ratingModels;
        float avgRating = 0;
        if (ratings != null && ratings.length > 0) {
            for (RatingModel rating: ratings) {
                avgRating += rating.rating;
            }
            avgRating = avgRating / ratings.length;
        }

        ratingTextView.setText(String.valueOf(avgRating));
    }

    public static MovieListItemView inflate(ViewGroup parent) {
        MovieListItemView itemView = (MovieListItemView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item_layout, parent, false);
        return itemView;
    }
}
