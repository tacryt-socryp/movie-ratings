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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        service = UserService.createService();
        currentUser = (UserModel) this.getIntent().getParcelableExtra("user");
        configureView();
    }

    public void onEditTestPressed(View view) {
        EditText nameField = (EditText) findViewById(R.id.editNameForEdit);
        if (currentUser != null) {
            currentUser.profile.name = nameField.getText().toString();
            UserService.updateUser(service, currentUser);
        }
    }

    public void onLogoutButtonPressed(View view) {
        Log.d("USER ACTIVITY", "Logout Button Pressed");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

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

    public void configureView() {
        TextView nameLabel = (TextView) findViewById(R.id.ProfileNameLabel);
        Log.d("serviceCall", currentUser.username + " " + currentUser.profile.name);
        nameLabel.setText(currentUser.profile.name);
    }
}