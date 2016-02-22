package teamfour.com.rottentomatoes;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


import otto.BusSubscriberActivity;

/**
 * Created by wbtho on 2/21/2016.
 */
public class SearchList extends BusSubscriberActivity {
    // private List<Movie> movieList;
    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_search_list);
        // movieList = SearchActivity.movieList;
        // ArrayAdapter<Movie> adapter = new ArrayAdapter<Movie>(this, android.R.layout.activity_list_item, movieList);
        // ListView myList = (ListView) findViewById(R.id.listView);
    }
}
