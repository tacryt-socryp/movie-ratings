package teamfour.com.rottentomatoes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import services.*;
import models.*;
import otto.*;

/**
 * Created by Mitch Myers on 2/11/16.
 */
public class RegistrationActivity extends BusSubscriberActivity {

    APIServiceInterface apiService;
    boolean isRegistrationActive = true;
    boolean admin = false;

    /**
     * initialize view and apiService for making calls to the server using Retrofit
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        apiService = UserService.getService();
    }

    /**
     * Action to create a new user with a name, username, and password.
     * @param view the registration view in which this action takes place
     */
    public void onRegisterButtonClicked(View view) {
        Log.d("REGISTRATION ACTIVITY", "Register Button Pressed");

        EditText namefield = (EditText) findViewById((R.id.Name));
        EditText majorfield = (EditText) findViewById((R.id.Major));
        EditText usernamefield = (EditText) findViewById(R.id.Username);
        EditText passwordfield = (EditText) findViewById(R.id.Password);
        EditText verifypasswordfield = (EditText) findViewById(R.id.VerifyPassword);
        EditText verifyadminfield = (EditText) findViewById(R.id.VerifyAdmin);

        String name = namefield.getText().toString();
        String major = majorfield.getText().toString();
        String username = usernamefield.getText().toString();
        String password = passwordfield.getText().toString();
        String verifypassword = verifypasswordfield.getText().toString();
        String verifyadmin = verifyadminfield.getText().toString();

        if (verifyadmin.equals("password")) {
            admin = true;
            if (password.equals(verifypassword)) {
                ProfileModel profile = new ProfileModel(name, major, -1); // NONEXISTENT ID

                UserService.createUser(apiService, new UserModel(username, password, profile, true));
            } else {
                Toast toast = Toast.makeText(
                        this.getApplicationContext(),
                        "Make Sure Your Passwords Match",
                        Toast.LENGTH_SHORT
                );
                toast.show();
            }
        } else if (verifyadmin.equals("")) {
            admin = false;
            if (password.equals(verifypassword)) {
                ProfileModel profile = new ProfileModel(name, major, -1); // NONEXISTENT ID

                UserService.createUser(apiService, new UserModel(username, password, profile));
            } else {
                Toast toast = Toast.makeText(
                        this.getApplicationContext(),
                        "Make Sure Your Passwords Match",
                        Toast.LENGTH_SHORT
                );
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "This is the wrong admin password. Try to register again.",
                    Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    /**
     * Asynchronously receive a successful usermodel upon successful user creation
     * @param user
     */
    @Subscribe
    public void getUserEvent(UserModel user) {
        if (isRegistrationActive) {
            Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "Registration Successful",
                    Toast.LENGTH_SHORT
            );
            toast.show();
            if (admin) {
                Toast welcome = Toast.makeText(this.getApplicationContext(),
                        "Welcome Admin!",
                        Toast.LENGTH_SHORT);
                welcome.show();
            }

            Intent intent;
            if (admin) {
                intent = new Intent(this, AdminActivity.class);
            }
            else {
                intent = new Intent(this, UserActivity.class);
            }
            intent.putExtra("user", user);
            startActivity(intent);
            isRegistrationActive = false;
        }
    }

    /**
     * Asynchronously receive an errormodel upon unsuccessful user creation
     * @param error
     */
    @Subscribe public void getErrorEvent(ErrorModel error) {
        if (isRegistrationActive) {
            Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "Registration failed - " + error.message,
                    Toast.LENGTH_SHORT
            );
            toast.show(); }
    }

    /**
     * Action to cancel registering and go back to the main login screen.
     * @param view the view in which this action takes place
     */
    public void onCancelButtonClicked(View view) {
        Log.d("REGISTRATION ACTIVITY", "Cancel button pressed");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
