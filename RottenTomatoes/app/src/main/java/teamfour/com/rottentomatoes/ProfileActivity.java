package teamfour.com.rottentomatoes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.FrameLayout;
import android.view.LayoutInflater;
import android.content.Context;

import models.UserModel;
import otto.BusSubscriberActivity;
import services.APIServiceInterface;
import services.UserService;

/**
 * Created by EstellaD on 2/25/16.
 */
public class ProfileActivity extends UserActivity {

    boolean userActivityActive = true;
    APIServiceInterface service;
    UserModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_profile, null,false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);

        service = UserService.getService();
        currentUser = (UserModel) this.getIntent().getParcelableExtra("user");
        setTextFields();
    }

    public void setTextFields() {
        EditText nameField = (EditText) findViewById(R.id.name);
        EditText majorField = (EditText) findViewById(R.id.major);

        nameField.setText(currentUser.profile.name);
        majorField.setText(currentUser.profile.major);
    }

    /**
     * simple test to see if edit profile works
     * @param view
     */
    public void onSaveButtonPressed(View view) {
        Log.d("PROFILE ACTIVITY", "updating user profile name");
        EditText nameField = (EditText) findViewById(R.id.name);
        EditText majorField = (EditText) findViewById(R.id.major);

        Log.d("PROFILE ACTIVITY", "updating user");
        currentUser.profile.name = nameField.getText().toString();
        currentUser.profile.major = majorField.getText().toString();
        UserService.updateUser(service, currentUser);
        Toast toast = Toast.makeText(
                this.getApplicationContext(),
                "Profile Updated!",
                Toast.LENGTH_SHORT
        );
        toast.show();
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
