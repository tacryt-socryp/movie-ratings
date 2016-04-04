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
    /**
     * APIServiceInterface
     */
    private APIServiceInterface ratingService;
    /**
     * list of ratings
     */
    private List<RatingModel> ratings;
    /**
     * movieTitle
     */
    private String movieTitle;

    /**
     * initialize adapter
     * @param c c
     * @param ratingList ratingList
     * @param movieT movieT
     */
    public RatingListAdapter(Context c, List<RatingModel> ratingList, String movieT) {
        super(c, 0, ratingList);
        ratingService = RatingService.getService();
        ratings = ratingList;
        movieTitle = movieT;

        final Bus bus = BusSingleton.get();
        // subscribe to new events!
        bus.register(this);
    }

    /**
     * get async ratings event from server
     * @param newRatings newRatings
     */
    @Subscribe
    public final void getRatingsEvent(RatingsModel newRatings) {
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
     * @return count
     */
    @Override
    public final int getCount() {
        if (ratings == null) {
            return 0;
        }
        return ratings.size();
    }

    /**
     * override get item to use internal data structure
     * @param position position
     * @return item
     */
    @Override
    public final RatingModel getItem(int position) {
        return ratings == null? null : ratings.get(position);
    }

    /**
     * override get view
     * @param position position
     * @param convertView convertView
     * @param parent parent
     * @return view
     */
    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        RatingListItemView itemView = (RatingListItemView) convertView;
        if (null == itemView) { itemView = RatingListItemView.inflate(parent); }
        itemView.setItem(getItem(position));
        return itemView;
    }

}