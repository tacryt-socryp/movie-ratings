package views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import models.MovieModel;
import models.RatingModel;
import models.UserModel;
import teamfour.com.rottentomatoes.R;

/**
 * Created by wbtho on 3/14/2016.
 */
public class UserListItemView extends RelativeLayout {
    public UserModel user = null;
    public UserModel[] userModels = null;

    private TextView usernameTextView;
    private TextView statusTextView;
    //private TextView ratingTextView;

    /**
     * Initialize.
     * @param context
     * @param attrs
     */
    public UserListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.user_item_layout_children, this, true);
        setupChildren();
    }

    /**
     * Link up to text views.
     */
    private void setupChildren() {
        usernameTextView = (TextView) findViewById(R.id.listUsernameTextView);
        statusTextView = (TextView) findViewById(R.id.listStatusTextView);
        //ratingTextView = (TextView) findViewById(R.id.listRatingTextView);
    }

    /**
     * Set text using the user model.
     * @param u
     */
    public void setUser(UserModel u) {
        if (u != null) {
            user = u;
            usernameTextView.setText(u.username);
            statusTextView.setText(u.status);
        }
    }

    /**
     * Set ratings for the movie using ratings.
     * @param ratingModels
     */
//    public void setRatings(RatingModel[] ratingModels) {
//        if (ratingModels != null) {
//            ratings = ratingModels;
//            float avgRating = 0;
//            if (ratings != null && ratings.length > 0) {
//                for (RatingModel rating: ratings) {
//                    avgRating += rating.rating;
//                }
//                avgRating = avgRating / ratings.length;
//            }
//
//            ratingTextView.setText(String.valueOf(avgRating));
//        }
//    }

    /**
     * inflate view from layout
     * @param parent
     * @return
     */
    public static UserListItemView inflate(ViewGroup parent) {
        UserListItemView itemView = (UserListItemView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item_layout, parent, false);
        return itemView;
    }
}
