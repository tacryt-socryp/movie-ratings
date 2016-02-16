package teamfour.com.rottentomatoes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import services.*;
import models.*;
import otto.*;

import com.squareup.otto.Subscribe;

/**
 * Created by EstellaD on 2/5/16.
 */
public class LoginActivity extends BusSubscriberActivity {

    APIServiceInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        service = UserService.createService();
    }

    public void onLoginButtonPressed(View view)
    {
        Log.d("LOGIN ACTIVITY", "Login Button Pressed");

        EditText namefield = (EditText) findViewById(R.id.editText);
        EditText passwordfield = (EditText) findViewById(R.id.editText2);

        String username = namefield.getText().toString();
        String password = passwordfield.getText().toString();

        // example of calling user service
        UserService.getUser(service, new UserModel(username, password, null));
    }

    @Subscribe public void getUserEvent(UserModel user) {
        Toast toast = Toast.makeText(
                this.getApplicationContext(),
                "Login Successful",
                Toast.LENGTH_SHORT
        );
        toast.show();

        Log.d("serviceCall", user.username + " " + user.password);
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    @Subscribe public void getErrorEvent(ErrorModel error) {
        Toast toast = Toast.makeText(
                this.getApplicationContext(),
                "Login failed - " + error.message,
                Toast.LENGTH_SHORT
        );
        toast.show();
    }

    public void onCancelButtonPressed(View view)
    {
        Log.d("LOGIN ACTIVITY", "Cancel button pressed");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
