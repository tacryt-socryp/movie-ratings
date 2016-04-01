package otto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Bus;

/**
 * Extend this class in place of AppCompatActivity to get access to the server
 * Created by logan on 2/10/16.
 */
public class BusSubscriberActivity extends AppCompatActivity {
    /**
     * onCreate, initialize the bus!
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize bus!
        this.initializeBus();
    }

    /**
     * Get an instance of the event bus (otto) from the BusSingleton, and register it to receive new events
     */
    public void initializeBus() {
        Bus bus = BusSingleton.get();

        // subscribe to new events!
        bus.register(this);
    }
}
