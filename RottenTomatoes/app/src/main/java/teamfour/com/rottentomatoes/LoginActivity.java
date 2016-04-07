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
import services.APIService;
import services.APIServiceInterface;
import services.UserService;

/**
 * Created by EstellaD on 2/5/16.
 */
public class LoginActivity extends BusSubscriberActivity {

    /**
     * APIServiceInterface
     */
    private APIServiceInterface service;
    /**
     * boolean loggedIn
     */
    private boolean loggedIn = false;
    String username = "not an account yet";
    String password = "not an account yet";
    int numTries = 0;

    /**
     * initialize the view and initialize service
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        service = UserService.getService();
    }

    /**
     * login button was pressed, perform a getUser server call
     * @param view view
     */
    public final void onLoginButtonPressed(View view) {
        Log.d("LOGIN ACTIVITY", "Login Button Pressed");
        loggedIn = false;

        final EditText namefield = (EditText) findViewById(R.id.editText);
        final EditText passwordfield = (EditText) findViewById(R.id.editText2);

        username = namefield.getText().toString();
        password = passwordfield.getText().toString();
        if (numTries == 5) {
            UserService.banOrUnbanUser(service, username, true);
            numTries = 0;
        } else {
            // example of calling user service
            UserService.getUser(service, new UserModel(username, password));
            numTries++;
        }

        //checks to see if the user is banned
        //active = UserService.getUser(service, new UserModel(username, password)).isActive;
    }

    /**
     * for Junit test
     * @param user, the name of the user
     * @param pass, password of the user
     * @return boolean, whether the values match
     */
    public final boolean login(String user, String pass) {
        boolean value;
        if (user.equals(username)) {
            value = false;
        } else if (pass.equals(password)) {
            value = false;
        } else {
            value = true;
        }
        return value;
    }

    /**
     * On successful login, receive back the asynchronous event using Otto!
     * @param user user
     */
    @Subscribe
    public final void getUserEvent(UserModel user) {
        if (!loggedIn) {
            final Toast toast = Toast.makeText(
                    this.getApplicationContext(),
                    "Login Successful",
                    Toast.LENGTH_SHORT
            );
            toast.show();
            loggedIn = true;
            numTries = 0;

            Log.d("serviceCall", user.username + " " + user.password + " " + user.isAdmin);


            Intent intent;
            if (user.isAdmin) {
                intent = new Intent(this, AdminActivity.class);
                Log.d("user", user.toString());
            } else {
                intent = new Intent(this, UserActivity.class);
                Log.d("user", user.toString());
            }
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }

    /**
     * On unsuccessful login, receive back the error message!
     * @param error error
     */
    @Subscribe
    public final void getErrorEvent(ErrorModel error) {
        final Toast toast = Toast.makeText(
                this.getApplicationContext(),
                "Login failed - " + error.message,
                Toast.LENGTH_SHORT
        );
        toast.show();
    }

    /**
     * Return to the main view.
     * @param view view
     */
    public final void onCancelButtonPressed(View view) {
        Log.d("LOGIN ACTIVITY", "Cancel button pressed");
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
