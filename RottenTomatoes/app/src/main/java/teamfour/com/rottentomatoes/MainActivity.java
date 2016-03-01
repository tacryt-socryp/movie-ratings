package teamfour.com.rottentomatoes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import models.UserModel;

import services.APIService;
import services.APIServiceInterface;

import otto.BusSubscriberActivity;

public class MainActivity extends UserActivity {

    /**
     * initialize view onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Login button was pressed, open LoginActivity!
     * @param v
     */
    public void onLoginButtonClicked(View v) {
        Log.d("OPENING SCREEN", "Pressed login");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * an item was selected from the action bar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * open register button upon register button pressed!
     * @param w
     */
    public void onRegisterButtonPressed(View w) {
        Log.d("OPENING SCREEN", "Pressed register");
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}
