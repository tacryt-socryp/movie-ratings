package views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import models.UserModel;
import teamfour.com.rottentomatoes.R;

/**
 * Created by wbtho on 3/14/2016.
 */
public class UserListItemView extends RelativeLayout {
    /**
     * user
     */
    public UserModel user = null;
    /**
     * userModels
     */
    public UserModel[] userModels = null;

    /**
     * usernameTextView
     */
    private TextView usernameTextView;
    /**
     * statusTextView
     */
    private TextView statusTextView;

    /**
     * Initialize.
     * @param context context
     * @param attrs attrs
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
    }

    /**
     * Set text using the user model.
     * @param u u
     */
    public final void setUser(UserModel u) {
        if (u != null) {
            user = u;
            usernameTextView.setText(u.username);
            statusTextView.setText(u.status);
        }
    }

    /**
     * inflate view from layout
     * @param parent parent
     * @return itemView
     */
    public static UserListItemView inflate(ViewGroup parent) {
        final UserListItemView itemView = (UserListItemView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item_layout, parent, false);
        return itemView;
    }
}
