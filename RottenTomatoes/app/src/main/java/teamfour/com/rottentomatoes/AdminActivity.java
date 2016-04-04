package teamfour.com.rottentomatoes;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import models.UserListModel;
import models.UserModel;
import otto.BusSubscriberActivity;
import services.APIServiceInterface;
import services.UserService;
import views.UserListAdapter;

/**
 * Created by Jeremy on 3/13/16.
 */
public class AdminActivity extends BusSubscriberActivity {

    private APIServiceInterface apiService;
    private boolean isCurrScreen = true;

    /**
     * life cycle method
     * @param savedInstanceBundle
     */
    @Override
    protected final void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_admin);

        apiService = UserService.getService();
        UserService.viewUserList(apiService);
    }

    /**
     * refresh user list upon ban
     */
    @Subscribe
    public final void getBanEvent(UserModel bannedUser) {
        if (isCurrScreen) {
            UserService.viewUserList(apiService);
        }
    }

    /**
     * Asynchronously receive list of users upon successful user search
     * @param list
     */
    @Subscribe
    public final void getUserEvent(UserListModel list) {

        final ListView lv= (ListView) findViewById(R.id.listView3);
        final List<UserModel> newList = new ArrayList<UserModel>();

        for (UserModel user : list.users) {
            if (user.isActive) {
                user.status = "Active";
            } else {
                user.status = "Banned or Locked";
            }
            newList.add(user);
        }

        final UserListAdapter adapter = new UserListAdapter(this, newList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                final UserModel item = (UserModel) adapter.getItemAtPosition(position);

                UserService.banOrUnbanUser(apiService, item.username, item.isActive);
            }
        });

    }
}
