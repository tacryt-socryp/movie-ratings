package teamfour.com.rottentomatoes;

import android.app.Activity;
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

    APIServiceInterface apiService;
    boolean isCurrScreen = true;

    /**
     * life cycle method
     * @param savedInstanceBundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_admin);

        apiService = UserService.getService();
        UserModel currentUser = (UserModel) this.getIntent().getParcelableExtra("user");
        UserService.viewUserList(apiService);
    }

    /**
     * refresh user list upon ban
     */
    @Subscribe
    public void getBanEvent(UserModel bannedUser) {
        if (isCurrScreen) {
            UserService.viewUserList(apiService);
        }
    }

    /**
     * Asynchronously receive list of users upon successful user search
     * @param list
     */
    @Subscribe
    public void getUserEvent(UserListModel list) {

        final Activity self = this;
        ListView lv= (ListView) findViewById(R.id.listView3);
        List<UserModel> newList = new ArrayList<UserModel>();
        for (UserModel user : list.users) {
            if (user.isActive) {
                user.status = "Active";
            } else {
                user.status = "Banned or Locked";
            }
            newList.add(user);
        }

        UserListAdapter adapter = new UserListAdapter(this, newList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                UserModel item = (UserModel) adapter.getItemAtPosition(position);

                UserService.banOrUnbanUser(apiService, item.username, item.isActive);
            }
        });

    }
}
