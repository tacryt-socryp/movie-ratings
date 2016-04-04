package views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import models.UserModel;
import services.APIServiceInterface;
import services.UserService;

/**
 * Created by wbtho on 3/14/2016.
 */
public class UserListAdapter extends ArrayAdapter<UserModel> {
    APIServiceInterface us;
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
    }

    /**
     * Get count override function
     * @return
     */
    @Override
    public final int getCount() {
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
    public final UserModel getItem(int position) {
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
    public final View getView(int position, View convertView, ViewGroup parent) {
        UserListItemView itemView = (UserListItemView)convertView;
        if (null == itemView) { itemView = UserListItemView.inflate(parent); }
        itemView.setUser(getItem(position));
        return itemView;
    }

}
