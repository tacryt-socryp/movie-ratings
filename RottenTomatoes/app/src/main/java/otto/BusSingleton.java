package otto;

import android.content.Context;

import teamfour.com.rottentomatoes.App;

import com.squareup.otto.Bus;

/**
 * Created by logan on 2/10/16.
 */
public class BusSingleton {
    private static Bus instance;
    private Context context;

    // retrieve the bus you need to get data using this method
    public static Bus get() {
        if(instance == null) instance = getSync();
        return instance;
    }

    // or if you're doing funky shit with threads, retrieve it synchronously from here.
    private static synchronized Bus getSync() {
        if(instance == null) instance = new Bus();
        return instance;
    }

    // don't call this method anywhere except for App
    public BusSingleton(Context context) {
        this.context = context;
    }
}
