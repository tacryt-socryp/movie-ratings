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

import models.UserListModel;
import models.UserModel;
import services.APIServiceInterface;
import services.UserService;
import otto.BusSingleton;

/**
 * Created by wbtho on 3/14/2016.
 */
public class UserListAdapter extends ArrayAdapter<UserModel> {
    private Bus bus;
    APIServiceInterface us;
    //APIServiceInterface ratingService;
    private List<UserModel> userModels;

    /**
     * Initialize UI and initialize bus.
     * @param c
     * @param items
     */

    public UserListAdapter(Context c, List<UserModel> items) {
        super(c, 0, items);
        us = UserService.getService();
        userModels = items;

        bus = BusSingleton.get();
        // subscribe to new events!
        bus.register(this);
        UserService.viewUserList(us);
//        for (UserModel user: items) {
//
//            //UserService.viewUserList(us);
//        }
    }

    /**
     * Receive asynchronous event with new ratings.
     * @param users
     */

    @Subscribe
    public void getRatingsEvent(UserListModel users) {
        // bad way of doing this, O(n^2). Fuck it
        UserModel user;
        for (int x = 0; x < this.getCount(); x++) {
            user = this.getItem(x);
            if (user.isActive == true) {
                user.status = "Active";
            } else {
                user.status = "Banned or Locked";
            }

//            if (user.title.equals(users.movieTitle)) {
//                user.ratings = users.ratings;
//                this.notifyDataSetChanged();
//                x = this.getCount();
//            }
        }

        // modify the list items individually based on events
    }

    /**
     * Get count override function
     * @return
     */
    @Override
    public int getCount() {
        if (userModels == null) {
            return 0;
        }
        return userModels.size();
    }

    /**
     * Get item override
     * @param position
     * @return
     */
    @Override
    public UserModel getItem(int position) {
        return userModels == null? null : userModels.get(position);
    }

    /**
     * Get view override that sets up item view.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserListItemView itemView = (UserListItemView)convertView;
        if (null == itemView) itemView = UserListItemView.inflate(parent);
        itemView.setUser(getItem(position));
        //itemView.setUserStatus(getItem(position).status);
        return itemView;
    }

}
