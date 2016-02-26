package teamfour.com.rottentomatoes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import models.UserModel;
import otto.BusSubscriberActivity;
import services.APIServiceInterface;
import services.UserService;

/**
 * Created by EstellaD on 2/25/16.
 */
public class ProfileActivity extends BusSubscriberActivity {

    boolean userActivityActive = true;
    APIServiceInterface service;
    UserModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        service = UserService.createService();
        currentUser = (UserModel) this.getIntent().getParcelableExtra("user");
    }

    /**
     * simple test to see if edit profile works
     * @param view
     */
    public void onSaveButtonPressed(View view) {
        Log.d("PROFILE ACTIVITY", "updating user profile name");
        EditText nameField = (EditText) findViewById(R.id.name);
        EditText passwordField = (EditText) findViewById(R.id.password);

        String password = passwordField.getText().toString();

        if (password.equals(currentUser.password)) {
            Log.d("PROFILE ACTIVITY", "updating user");
            currentUser.profile.name = nameField.getText().toString();
            UserService.updateUser(service, currentUser);
            Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "Profile Updated!",
                    Toast.LENGTH_SHORT
            );
            toast.show();
        } else {
            Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "Incorrect password!",
                    Toast.LENGTH_SHORT
            );
            toast.show();
        }
    }

    /**
     * Returns the user to the main screen
     * @param view
     */
    public void onHomeButtonPressed(View view) {
        Log.d("PROFILE ACTIVITY", "Home Button Pressed");
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra("user", currentUser);
        startActivity(intent);
    }
}
