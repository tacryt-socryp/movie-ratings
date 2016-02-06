package teamfour.com.rottentomatoes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by EstellaD on 2/5/16.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLoginButtonPressed(View view)
    {
        Log.d("LOGIN ACTIVITY", "Login Button Pressed");
        UserManager um = new UserManager();
        EditText namefield = (EditText) findViewById(R.id.editText);
        EditText passwordfield = (EditText) findViewById(R.id.editText2);
        CharSequence text;
        Intent intent = null;
        if (um.handleLogin(namefield.getText().toString(), passwordfield.getText().toString()))
        {
            text = "Login Succesful";
            intent = new Intent(this, UserActivity.class);
        }
        else
        {
            text = "Login Failed";
        }
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        if (intent != null)
        {
            startActivity(intent);
        }
    }

    public void onCancelButtonPressed(View view)
    {
        Log.d("LOGIN ACTIVITY", "Cancel button pressed");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
