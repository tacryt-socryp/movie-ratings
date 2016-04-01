package views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import models.RatingModel;
import models.RatingsModel;
import otto.BusSingleton;
import services.APIServiceInterface;
import services.RatingService;

/**
 * Created by logan on 2/27/16.
 */
public class RatingListAdapter extends ArrayAdapter<RatingModel> {
    APIServiceInterface ratingService;
    private List<RatingModel> ratings;
    private String movieTitle;

    /**
     * initialize adapter
     * @param c
     * @param ratingList
     * @param movieT
     */
    public RatingListAdapter(Context c, List<RatingModel> ratingList, String movieT) {
        super(c, 0, ratingList);
        ratingService = RatingService.getService();
        ratings = ratingList;
        movieTitle = movieT;

        Bus bus = BusSingleton.get();
        // subscribe to new events!
        bus.register(this);
    }

    /**
     * get async ratings event from server
     * @param newRatings
     */
    @Subscribe
    public void getRatingsEvent(RatingsModel newRatings) {
        if (newRatings.movieTitle.equals(movieTitle)) {
            ratings.clear();
            for (RatingModel rating: newRatings.ratings) {
                ratings.add(rating);
            }
            this.notifyDataSetChanged();
        }
    }

    /**
     * override get count to use internal data structure
     * @return
     */
    @Override
    public int getCount() {
        if (ratings == null) {
            return 0;
        }
        return ratings.size();
    }

    /**
     * override get item to use internal data structure
     * @param position
     * @return
     */
    @Override
    public RatingModel getItem(int position) {
        return ratings == null? null : ratings.get(position);
    }

    /**
     * override get view
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RatingListItemView itemView = (RatingListItemView) convertView;
        if (null == itemView) { itemView = RatingListItemView.inflate(parent); }
        itemView.setItem(getItem(position));
        return itemView;
    }

}