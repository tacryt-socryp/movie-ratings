package teamfour.com.rottentomatoes;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Will Thompson on 4/4/16.
 */
public class RegistrationTest extends ActivityInstrumentationTestCase2<RegistrationActivity> {
    private RegistrationActivity rActivity;
    public RegistrationTest() {super(RegistrationActivity.class);}

    @Override
    public void setUp() throws Exception {
        super.setUp();
        rActivity = getActivity();
    }

    public void testPreconditions() {
        assertNotNull("rActivity is null", rActivity);
    }

    public void testEmptyRegister() {
        assertFalse(rActivity.register("","","",""));
    }

    public void testRegisterEvent() {
        assertTrue(rActivity.register("Example", "password", "password", "CS"));
        assertFalse(rActivity.register("", "password", "password", ""));
        assertTrue(rActivity.registerAdmin("Example", "password", "password", "CS", "password"));
        assertFalse(rActivity.registerAdmin("Example", "", "", "", ""));
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
