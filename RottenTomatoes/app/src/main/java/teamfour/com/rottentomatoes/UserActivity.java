package teamfour.com.rottentomatoes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import models.ErrorModel;
import models.UserModel;
import otto.BusSubscriberActivity;
import services.APIServiceInterface;
import services.UserService;

// Updated upstream
// Stashed changes
// Updated upstream

// Stashed changes
/**
 * Created by EstellaD on 2/5/16.
 */
public class UserActivity extends BusSubscriberActivity {
    /**
     * userString
     */
    private String userString = "user";

    /**
     * APIServiceInterface
     */
    private APIServiceInterface service;

    /**
     * currentUser
     */
    private UserModel currentUser;

    /**
     * userActivityActive
     */
    private boolean userActivityActive = true;

    /**
     * drawerItems
     */
    private String[] drawerItems = {"Home", "Search", "Get Recommendation", "Edit Profile", "Logout"};

    /**
     * drawerLayout
     */
    private DrawerLayout drawerLayout;

    /**
     * drawerList
     */
    private ListView drawerList;

    /**
     * actionBarDrawerToggle
     */
    private ActionBarDrawerToggle actionBarDrawerToggle;

    /**
     * Activities
     */
    enum Activities {
        /**
         * User
         */
        User,
        /**
         * Search
         */
        Search,
        /**
         * Recommendation
         */
        Recommendation,
        /**
         * Profile
         */
        Profile,
        /**
         * Main
         */
        Main
    }

    protected TextView welcomeText;

    /**
     * receive currentUser from either registration or login via extras,
     * initialize view and create service for interacting with the server
     * @param savedInstanceState savedInstance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        service = UserService.getService();
        currentUser = (UserModel) this.getIntent().getParcelableExtra(userString);

        welcomeText = (TextView) findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome to Buzz Movie Selector! Select the drawer on the" +
                " left to navigate through the app!");

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
    }

    /**
     * Asynchronously receive a successful usermodel upon successful user get
     * @param user user
     */
    @Subscribe
    public final void getUserEvent(UserModel user) {
        if (userActivityActive) {
            final Toast toast = Toast.makeText(
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
        }
    }

    /**
     * Asynchronously receive an errormodel upon unsuccessful user creation or login
     * @param error error
     */
    @Subscribe
    public final void getErrorEvent(ErrorModel error) {
        if (userActivityActive) {
            final Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "Login failed - " + error.message,
                    Toast.LENGTH_SHORT
            );
            toast.show();
        }
    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected final void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    /**
     * drawer handler
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            switch (Activities.values()[position]) {

                case User: {
                    final Intent intent = new Intent(UserActivity.this, UserActivity.class);
                    intent.putExtra(userString, currentUser);
                    startActivity(intent);
                    break;
                }
                case Search: {
                    final Intent intent = new Intent(UserActivity.this, SearchActivity.class);
                    intent.putExtra(userString, currentUser);
                    startActivity(intent);
                    break;
                }
                case Recommendation: {
                    final Intent intent = new Intent(UserActivity.this, RecommendationActivity.class);
                    intent.putExtra(userString, currentUser);
                    startActivity(intent);
                    break;
                }
                case Profile: {
                    final Intent intent = new Intent(UserActivity.this, ProfileActivity.class);
                    intent.putExtra(userString, currentUser);
                    startActivity(intent);
                    break;
                }
                case Main: {
                    final Intent intent = new Intent(UserActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                }
            }
            drawerLayout.closeDrawer(drawerList);
        }
    }
}