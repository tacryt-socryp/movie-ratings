package otto;

import android.content.Context;

import com.squareup.otto.Bus;

/**
 * This is a singleton for the event bus for receiving server data in activities
 * Created by logan on 2/10/16.
 */
public class BusSingleton {
    private static Bus instance;
    private static Context context;

    /**
     * retrieve the bus you need to get data using this method
     * @return instance of Bus
     */
    public static Bus get() {
        if(instance == null) { instance = getSync(); }
        return instance;
    }

    /**
     * if you're doing funky shit with threads, retrieve it synchronously from here.
     * @return instance of Bus
     */
    private static synchronized Bus getSync() {
        if(instance == null) { instance = new Bus(); }
        return instance;
    }

    /**
     * don't call this method anywhere except for App
     * @param ctx
     */
    public static void setContext(Context ctx) {
        context = ctx;
    }

    private BusSingleton() {}
}
