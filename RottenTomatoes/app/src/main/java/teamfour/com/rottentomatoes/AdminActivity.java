package teamfour.com.rottentomatoes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

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
    private UserModel currentUser;
    boolean userExists = false;

    private String[] drawerItems = {"Home", "Edit Profile", "Logout"};
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_admin);

        apiService = UserService.getService();
        currentUser = (UserModel) this.getIntent().getParcelableExtra("user");
/*
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, drawerItems));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
*/
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            switch (position) {
                case 0: {
                    Intent intent = new Intent(AdminActivity.this, AdminActivity.class);
                    intent.putExtra("user", currentUser);
                    startActivity(intent);
                    break;
                }
                case 1: {
                    Intent intent = new Intent(AdminActivity.this, ProfileActivity.class);
                    intent.putExtra("user", currentUser);
                    startActivity(intent);
                    break;
                }
                case 2: {
                    Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                }
            }
            drawerLayout.closeDrawer(drawerList);
        }
    }

    public void pressedBan(View view) {
        EditText query = (EditText) findViewById(R.id.BanQuery);
        String banRequest = query.getText().toString();

        //want to get the list of users to check against

        if (userExists) {
            banOrUnbanUser(banRequest, true);
        } else {
            //outputs an error message to the admin
            Toast toast = Toast.makeText(this.getApplicationContext(),
                    "This user does not exist.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void pressedUnlock(View view) {
        EditText query = (EditText) findViewById(R.id.UnlockQuery);
        String unlockRequest = query.getText().toString();

        //want to get the list of users to check against

        if (userExists) {
            banOrUnbanUser(unlockRequest, false);
        } else {
            //outputs an error message to the admin
            Toast toast = Toast.makeText(this.getApplicationContext(),
                    "This user does not exist.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public void banOrUnbanUser(String user, Boolean ban) {
        //currently set to take in a string username as a parameter rather
        //than a user modeal --> need to check this change somehow for right now
        if (ban) {
            //user.isActive = false;
        } else {
            //user.isActive = true;
        }
    }

    public void showUsers(View view) {
        Log.d("ADMIN ACTIVITY", "Pressed show users button");
        UserService.viewUserList(apiService);
    }

    /**
     * Asynchronously receive list of users upon successful user search
     * @param list
     */
    @Subscribe
    public void getUserEvent(UserListModel list) {
        System.out.println("Made it to get User event");

        final Activity self = this;
        ListView lv= (ListView) findViewById(R.id.listView3);
        UserListAdapter adapter = new UserListAdapter(this, list.users);
        lv.setAdapter(adapter);
        Log.d("USERS = ", list.users.get(0).toString());
//            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
//
//                MovieModel item = (MovieModel) adapter.getItemAtPosition(position);
//                Log.d("movieModel", item.toString());
//
//                Intent intent = new Intent(self, MovieActivity.class);
//                MovieModel movieExtra = (MovieModel) adapter.getItemAtPosition(position);
//                intent.putExtra("movie", movieExtra);
//                intent.putExtra("ratings", movieExtra.ratings);
//                intent.putExtra("user", currentUser);
//                startActivity(intent);
//            }
//        }
    }
}
