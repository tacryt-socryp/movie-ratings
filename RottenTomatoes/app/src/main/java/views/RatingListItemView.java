package views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import models.RatingModel;
import teamfour.com.rottentomatoes.R;

/**
 * Created by logan on 2/27/16.
 */
public class RatingListItemView extends RelativeLayout {
    /**
     * RatingModel
     */
    private RatingModel rating = null;

    /**
     * ratingNumTextView
     */
    private TextView ratingNumTextView;
    /**
     * userTextView
     */
    private TextView userTextView;
    /**
     * descriptionTextView
     */
    private TextView descriptionTextView;

    /**
     * Constructor for RatingListItemView
     * @param context context
     * @param attrs attrs
     */
    public RatingListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.rating_item_layout_children, this, true);
        setupChildren();
    }

    /**
     * Sets up children
     */
    private void setupChildren() {
        ratingNumTextView = (TextView) findViewById(R.id.listRatingDescriptionNumTextView);
        userTextView = (TextView) findViewById(R.id.listRatingUserTextView);
        descriptionTextView = (TextView) findViewById(R.id.listRatingDescriptionTextView);
    }

    /**
     * Sets item
     * @param ratingModel ratingModel
     */
    public final void setItem(RatingModel ratingModel) {
        if (ratingModel != null) {
            rating = ratingModel;
            Log.d("rating", rating.toString());
            ratingNumTextView.setText(String.valueOf(rating.rating));
            userTextView.setText(rating.user);
            descriptionTextView.setText(rating.text);
        }
    }

    /**
     * Inflates rating list item view
     * @param parent parent
     * @return itemView
     */
    public static RatingListItemView inflate(ViewGroup parent) {
        final RatingListItemView itemView = (RatingListItemView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_item_layout, parent, false);
        return itemView;
    }
}