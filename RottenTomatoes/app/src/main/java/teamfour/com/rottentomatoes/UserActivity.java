package teamfour.com.rottentomatoes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

//import teamfour.com.rottentomatoes.volleysample.dummy.State;

/**
 * Created by EstellaD on 2/5/16.
 */
public class UserActivity extends AppCompatActivity {

    private RequestQueue queue;
    private String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        queue = Volley.newRequestQueue(this);
    }

    public void onLogoutButtonPressed(View view) {
        Log.d("USER ACTIVITY", "Logout Button Pressed");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onSearchButtonPressed(View view) {
        EditText queryfield = (EditText) findViewById(R.id.SearchQuery);
        String query = queryfield.getText().toString();

        String url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=[yedukp76ffytfuy24zsqk7f5]&q="
                + query + "page_limit=1";

        //TextView displaytext = (TextView) findViewById(R.id.textBox);

        /*JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });*/
        //this actually queues up the async response with Volley
        //queue.add(jsObjRequest);
    }

    private void search(ArrayList<String> states) {
        Log.d("USER ACTIVITY", "Search Button Pressed");
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("results", states);
        startActivity(intent);
    }
}