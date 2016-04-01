package teamfour.com.rottentomatoes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
public class LoginActivity extends BusSubscriberActivity {

    APIServiceInterface service;
    boolean loggedIn = false;

    /**
     * initialize the view and initialize service
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        service = UserService.getService();
    }

    /**
     * login button was pressed, perform a getUser server call
     * @param view
     */
    public void onLoginButtonPressed(View view) {
        Log.d("LOGIN ACTIVITY", "Login Button Pressed");
        loggedIn = false;

        EditText namefield = (EditText) findViewById(R.id.editText);
        EditText passwordfield = (EditText) findViewById(R.id.editText2);

        String username = namefield.getText().toString();
        String password = passwordfield.getText().toString();

        // example of calling user service
        UserService.getUser(service, new UserModel(username, password));

        //checks to see if the user is banned
        //active = UserService.getUser(service, new UserModel(username, password)).isActive;
    }

    /**
     * On successful login, receive back the asynchronous event using Otto!
     * @param user
     */
    @Subscribe public void getUserEvent(UserModel user) {
        if (!loggedIn) {
            Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "Login Successful",
                    Toast.LENGTH_SHORT
            );
            toast.show();
            loggedIn = true;

            Log.d("serviceCall", user.username + " " + user.password + " " + user.isAdmin);


            Intent intent;
            if (user.isAdmin) {
                intent = new Intent(this, AdminActivity.class);
                Log.d("user", user.toString());
            }
            else {
                intent = new Intent(this, UserActivity.class);
                Log.d("user", user.toString());
            }
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }

    /**
     * On unsuccessful login, receive back the error message!
     * @param error
     */
    @Subscribe public void getErrorEvent(ErrorModel error) {
        Toast toast = Toast.makeText(
                this.getApplicationContext(),
                "Login failed - " + error.message,
                Toast.LENGTH_SHORT
        );
        toast.show();
    }

    /**
     * Return to the main view.
     * @param view
     */
    public void onCancelButtonPressed(View view) {
        Log.d("LOGIN ACTIVITY", "Cancel button pressed");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
