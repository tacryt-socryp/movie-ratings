package teamfour.com.rottentomatoes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import models.MovieModel;
import models.UserModel;
import services.APIService;
import services.APIServiceInterface;
import services.RottenTomatoesService;
import services.UserService;

/**
 * Created by Jeremy on 3/13/16.
 */
public class AdminActivity extends UserActivity {

    APIServiceInterface apiService;
    private UserModel currentUser;
    boolean userExists = false;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);

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
}
