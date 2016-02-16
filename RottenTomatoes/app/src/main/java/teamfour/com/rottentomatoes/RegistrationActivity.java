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

    public void onRegisterButtonClicked(View view) {
        Log.d("REGISTRATION ACTIVITY", "Register Button Pressed");

        EditText usernamefield = (EditText) findViewById(R.id.Username);
        EditText passwordfield = (EditText) findViewById(R.id.Password);
        EditText verifypasswordfield = (EditText) findViewById(R.id.VerifyPassword);

        String username = usernamefield.getText().toString();
        String password = passwordfield.getText().toString();
        String verifypassword = verifypasswordfield.getText().toString();

        if (password.equals(verifypassword)) {
            UserService.createUser(service, new UserModel(username, password));
            Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "Registration Successful",
                    Toast.LENGTH_SHORT
            );
            toast.show();

            Log.d("serviceCall", username + " " + password);
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

    public void onCancelButtonClicked(View view) {
        Log.d("REGISTRATION ACTIVITY", "Cancel button pressed");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
