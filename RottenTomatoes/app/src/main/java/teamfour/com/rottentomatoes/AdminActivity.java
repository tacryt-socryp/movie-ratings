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
    private UserModel currentUser;
    boolean userExists = false;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_admin);

        apiService = UserService.getService();
        currentUser = (UserModel) this.getIntent().getParcelableExtra("user");
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
/*            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                MovieModel item = (MovieModel) adapter.getItemAtPosition(position);
                Log.d("movieModel", item.toString());

                Intent intent = new Intent(self, MovieActivity.class);
                MovieModel movieExtra = (MovieModel) adapter.getItemAtPosition(position);
                intent.putExtra("movie", movieExtra);
                intent.putExtra("ratings", movieExtra.ratings);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
        }
*/
    }
}
