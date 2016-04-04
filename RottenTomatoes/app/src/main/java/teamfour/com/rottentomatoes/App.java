package teamfour.com.rottentomatoes;

import services.APIService;
import otto.BusSingleton;
import services.RottenTomatoesService;


/**
 * Created by logan on 2/10/16.
 */
public class App extends android.app.Application {

    /**
     * Android lifecycle method.
     * We initialize the asynchronous event bus here (Otto)
     */
    @Override
    public final void onCreate() {
        super.onCreate();

        // here we are initializing bus singleton (this should only happen once!)
        BusSingleton.setContext(this.getApplicationContext());
        APIService.initBus(BusSingleton.get());
        RottenTomatoesService.initBus(BusSingleton.get());
    }

}