package teamfour.com.rottentomatoes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import models.ErrorModel;
import models.UserModel;
import otto.BusSubscriberActivity;
import services.APIServiceInterface;
import services.UserService;

import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

/**
 * Created by EstellaD on 2/5/16.
 */
public class UserActivity extends BusSubscriberActivity {

    APIServiceInterface service;
    UserModel currentUser;
    boolean userActivityActive = true;

    private String[] drawerItems = {"Search", "Edit Profile", "Logout"};
    private DrawerLayout drawerLayout;
    private ListView drawerList;

    /**
     * receive currentUser from either registration or login via extras,
     * initialize view and create service for interacting with the server
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        service = UserService.getService();
        currentUser = (UserModel) this.getIntent().getParcelableExtra("user");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, drawerItems));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            switch (position) {
                case 0: {
                    Intent intent = new Intent(UserActivity.this, SearchActivity.class);
                    startActivity(intent);
                    break;
                }
                case 1: {
                    Intent intent = new Intent(UserActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    break;
                }
                case 2: {
                    Intent intent = new Intent(UserActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                }
            }
            drawerLayout.closeDrawer(drawerList);
        }
    }

    /**
     * Asynchronously receive a successful usermodel upon successful user get
     * @param user
     */
    @Subscribe
    public void getUserEvent(UserModel user) {
        if (userActivityActive) {
            Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "User information updated!",
                    Toast.LENGTH_SHORT
            );
            toast.show();
            Log.d("serviceCall", "WE GOT IT " +
                    user.username + " " +
                    user.password + " " +
                    user.profile.name + " " +
                    user.profile.profileID);

            currentUser = user;
            //configureView();
        }
    }

    /**
     * Asynchronously receive an errormodel upon unsuccessful user creation or login
     * @param error
     */
    @Subscribe public void getErrorEvent(ErrorModel error) {
        if (userActivityActive) {
            Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "Login failed - " + error.message,
                    Toast.LENGTH_SHORT
            );
            toast.show();
        }
    }

//    /**
//     * view is configured upon new data being received
//     */
//    public void configureView() {
//        TextView nameLabel = (TextView) findViewById(R.id.ProfileNameLabel);
//        Log.d("serviceCall", currentUser.username + " " + currentUser.profile.name);
//        nameLabel.setText(currentUser.profile.name);
//    }
}