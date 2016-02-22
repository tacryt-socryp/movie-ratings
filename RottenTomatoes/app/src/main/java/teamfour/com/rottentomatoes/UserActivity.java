package teamfour.com.rottentomatoes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import models.ErrorModel;
import models.UserModel;
import otto.BusSubscriberActivity;
import services.APIServiceInterface;
import services.UserService;

/**
 * Created by EstellaD on 2/5/16.
 */
public class UserActivity extends BusSubscriberActivity {

    APIServiceInterface service;
    UserModel currentUser;
    boolean userActivityActive = true;

    /**
     * receive currentUser from either registration or login via extras,
     * initialize view and create service for interacting with the server
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        service = UserService.createService();
        currentUser = (UserModel) this.getIntent().getParcelableExtra("user");
        configureView();
    }

    /**
     * simple test to see if edit profile works
     * @param view
     */
    public void onEditTestPressed(View view) {
        EditText nameField = (EditText) findViewById(R.id.editNameForEdit);
        if (currentUser != null) {
            currentUser.profile.name = nameField.getText().toString();
            UserService.updateUser(service, currentUser);
        }
    }

    /**
     * log out of the activity
     * @param view
     */
    public void onLogoutButtonPressed(View view) {
        Log.d("USER ACTIVITY", "Logout Button Pressed");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onSearchButtonPressed(View w) {
        Log.d("USER ACTIVITY", "Pressed Search");
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
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
            configureView();
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

    /**
     * view is configured upon new data being received
     */
    public void configureView() {
        TextView nameLabel = (TextView) findViewById(R.id.ProfileNameLabel);
        Log.d("serviceCall", currentUser.username + " " + currentUser.profile.name);
        nameLabel.setText(currentUser.profile.name);
    }
}