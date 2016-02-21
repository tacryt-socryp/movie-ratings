package teamfour.com.rottentomatoes;

import services.APIService;
import otto.BusSingleton;


/**
 * Created by logan on 2/10/16.
 */
public class App extends android.app.Application {

    /**
     * Android lifecycle method.
     * We initialize the asynchronous event bus here (Otto)
     */
    @Override
    public void onCreate() {
        super.onCreate();

        // here we are initializing bus singleton (this should only happen once!)
        new BusSingleton(this.getApplicationContext());
        APIService.initBus(BusSingleton.get());
    }

}