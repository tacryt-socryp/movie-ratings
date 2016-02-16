package teamfour.com.rottentomatoes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import services.*;
import models.*;
import otto.*;

/**
 * Created by Mitch Myers on 2/11/16.
 */
public class RegistrationActivity extends BusSubscriberActivity {

    APIServiceInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        service = UserService.createService();
    }

    /**
     * Action to create a new user with a name, username, and password.
     * @param view the registration view in which this action takes place
     */
    public void onRegisterButtonClicked(View view) {
        Log.d("REGISTRATION ACTIVITY", "Register Button Pressed");

        EditText namefield = (EditText) findViewById((R.id.Name));
        EditText usernamefield = (EditText) findViewById(R.id.Username);
        EditText passwordfield = (EditText) findViewById(R.id.Password);
        EditText verifypasswordfield = (EditText) findViewById(R.id.VerifyPassword);

        String name = namefield.getText().toString();
        String username = usernamefield.getText().toString();
        String password = passwordfield.getText().toString();
        String verifypassword = verifypasswordfield.getText().toString();

        if (password.equals(verifypassword)) {
            ProfileModel profile = new ProfileModel(name, -1); // NONEXISTENT ID

            UserService.createUser(service, new UserModel(username, password, profile));
            Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "Registration Successful",
                    Toast.LENGTH_SHORT
            );
            toast.show();

            Log.d("serviceCall", username + " " + password + " Profile Name: " + name);
            Intent intent = new Intent(this, UserActivity.class);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "Make Sure Your Passwords Match",
                    Toast.LENGTH_SHORT
            );
            toast.show();
        }
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
