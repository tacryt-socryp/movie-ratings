package otto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Bus;

/**
 * Extend this class in place of AppCompatActivity to get access to
 * Created by logan on 2/10/16.
 */
public class BusSubscriberActivity extends AppCompatActivity {
    private Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize bus!
        this.initializeBus();
    }

    public void initializeBus() {
        bus = BusSingleton.get();

        // subscribe to new events!
        bus.register(this);
    }
}
