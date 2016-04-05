package teamfour.com.rottentomatoes;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by EstellaD on 4/4/16.
 */
public class SearchTest extends ActivityInstrumentationTestCase2<SearchActivity> {

    private SearchActivity sActivity;

    public SearchTest() {
        super(SearchActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        sActivity = getActivity();
    }

    public void testPreconditions() {
        assertNotNull("sActivity is null", sActivity);
    }

    public void testEmptySearch() {
        sActivity.query.setText("");
        assert(sActivity.getMovies().movies.size() == 0);
    }

    public void testSearchEvent() {
        sActivity.query.setText("harry potter and the order of the phoenix");
        assert(sActivity.getMovies().movies.size() == 4);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
