package teamfour.com.rottentomatoes;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import models.UserModel;
import services.APIServiceInterface;
import services.UserService;

/**
 * Created by EstellaD on 2/25/16.
 */
public class ProfileActivity extends UserActivity {


    /**
     * APIServiceInterface
     */
    private APIServiceInterface service;
    /**
     * currentUser
     */
    private UserModel currentUser;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FrameLayout frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        // inflate the custom activity layout
        final LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View activityView = layoutInflater.inflate(R.layout.activity_profile, null,false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);

        service = UserService.getService();
        currentUser = (UserModel) this.getIntent().getParcelableExtra("user");
        setTextFields();
    }

    /**
     * set text fields with data
     */
    public final void setTextFields() {
        final EditText nameField = (EditText) findViewById(R.id.name);
        final EditText majorField = (EditText) findViewById(R.id.major);

        nameField.setText(currentUser.profile.name);
        majorField.setText(currentUser.profile.major);
        welcomeText.setText("");
    }

    /**
     * Update user profile name and major
     * @param name new name
     * @param major new major
     */
    public final boolean updateUserData(String name, String major) {
        if (name == null || major == null) {
            return false;
        } else if ("".equals(name) || "".equals(major)) {
            return false;
        }
        currentUser.profile.name = name;
        currentUser.profile.major = major;
        return true;
    }

    /**
     * simple test to see if edit profile works
     * @param view view
     */
    public final void onSaveButtonPressed(View view) {
        Log.d("PROFILE ACTIVITY", "updating user profile name");
        final EditText nameField = (EditText) findViewById(R.id.name);
        final EditText majorField = (EditText) findViewById(R.id.major);
        updateUserData(nameField.getText().toString(), majorField.getText().toString());
        Log.d("PROFILE ACTIVITY", "updating user");
        UserService.updateUser(service, currentUser);
        final Toast toast = Toast.makeText(
                this.getApplicationContext(),
                "Profile Updated!",
                Toast.LENGTH_SHORT
        );
        toast.show();
    }
}
