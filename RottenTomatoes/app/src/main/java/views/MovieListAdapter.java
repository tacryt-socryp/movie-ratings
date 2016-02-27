package views;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Movie;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import models.MovieModel;
import models.RatingModel;
import models.RatingsModel;
import otto.BusSingleton;
import services.APIServiceInterface;
import services.RatingService;

import java.util.Hashtable;

/**
 * Created by logan on 2/27/16.
 */
public class MovieListAdapter extends ArrayAdapter<MovieModel> {
    private Bus bus;
    APIServiceInterface ratingService;
    private List<MovieModel> movieModels;

    public MovieListAdapter(Context c, List<MovieModel> items) {
        super(c, 0, items);
        ratingService = RatingService.getService();
        movieModels = items;

        bus = BusSingleton.get();
        // subscribe to new events!
        bus.register(this);
        for (MovieModel movie: items) {
            Log.d("fetchRatings", movie.title);
            RatingService.getRatings(ratingService, movie.title);
        }
    }


    @Subscribe
    public void getRatingsEvent(RatingsModel ratings) {
        Log.d("ratingsEvent", ratings.movieTitle);
        // bad way of doing this, O(n^2). Fuck it
        MovieModel movie;
        for (int x = 0; x < this.getCount(); x++) {
            movie = this.getItem(x);
            if (movie.title.equals(ratings.movieTitle)) {
                movie.ratings = ratings.ratings;
                this.notifyDataSetChanged();
                x = this.getCount();
            }
        }

        // modify the list items individually based on events
    }

    @Override
    public int getCount() {
        if (movieModels == null) {
            return 0;
        }
        return movieModels.size();
    }

    @Override
    public MovieModel getItem(int position) {
        return movieModels == null? null : movieModels.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieListItemView itemView = (MovieListItemView)convertView;
        if (null == itemView) itemView = MovieListItemView.inflate(parent);
        itemView.setMovie(getItem(position));
        itemView.setRatings(getItem(position).ratings);
        return itemView;
    }

}