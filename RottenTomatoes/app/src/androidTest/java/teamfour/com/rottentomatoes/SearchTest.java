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
        sActivity.getSearch("");
        assertNull(sActivity.getMovies());
    }

    public void testSearchEvent() {
        sActivity.getSearch("harry potter and the order of the phoenix");
        if (sActivity.gotMovies) {
            assertEquals(sActivity.getMovies().size(), 4);
        }
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
