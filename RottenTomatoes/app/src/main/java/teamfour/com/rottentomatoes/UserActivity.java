package teamfour.com.rottentomatoes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import models.UserModel;
import otto.BusSubscriberActivity;
import services.APIServiceInterface;
import services.UserService;

/**
 * Created by EstellaD on 2/5/16.
 */
public class UserActivity extends BusSubscriberActivity {

    APIServiceInterface service;
    UserModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        service = UserService.createService();
    }

    public void onEditTestPressed(View view) {
        currentUser.profile.name = "EDITED";
        UserService.updateUser(service, currentUser);
    }

    public void onLogoutButtonPressed(View view) {
        Log.d("USER ACTIVITY", "Logout Button Pressed");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Subscribe
    public void getUserEvent(UserModel user) {
        Log.d("serviceCall", "WE GOT IT " + user.username + " " + user.password + " " + user.profile.name);
        currentUser = user;
    }
}