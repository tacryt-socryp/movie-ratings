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
    private RatingModel rating = null;

    private TextView ratingNumTextView;
    private TextView userTextView;
    private TextView descriptionTextView;

    public RatingListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.rating_item_layout_children, this, true);
        setupChildren();
    }

    private void setupChildren() {
        ratingNumTextView = (TextView) findViewById(R.id.listRatingDescriptionNumTextView);
        userTextView = (TextView) findViewById(R.id.listRatingUserTextView);
        descriptionTextView = (TextView) findViewById(R.id.listRatingDescriptionTextView);
    }

    public final void setItem(RatingModel ratingModel) {
        if (ratingModel != null) {
            rating = ratingModel;
            Log.d("rating", rating.toString());
            ratingNumTextView.setText(String.valueOf(rating.rating));
            userTextView.setText(rating.user);
            descriptionTextView.setText(rating.text);
        }
    }

    public static RatingListItemView inflate(ViewGroup parent) {
        RatingListItemView itemView = (RatingListItemView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_item_layout, parent, false);
        return itemView;
    }
}