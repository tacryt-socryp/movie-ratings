package teamfour.com.rottentomatoes;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Mitch on 4/6/16.
 */
public class ProfileTest extends ActivityInstrumentationTestCase2<ProfileActivity> {
    private ProfileActivity pActivity;
    public ProfileTest() {super(ProfileActivity.class);}

    @Override
    public void setUp() throws Exception {
        super.setUp();
        pActivity = getActivity();
    }

    public void testPreconditions() {
        assertNotNull("pActivity is null", pActivity);
    }

    public void testEmptyDataUpdate() {
        assertFalse(pActivity.updateUserData("", ""));
    }

    public void testUserUpdateScenarios() {
        assertFalse(pActivity.updateUserData("", "Math"));
        assertFalse(pActivity.updateUserData("Mitchell", ""));
        assertTrue(pActivity.updateUserData("Mitch", "CS"));
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

}
