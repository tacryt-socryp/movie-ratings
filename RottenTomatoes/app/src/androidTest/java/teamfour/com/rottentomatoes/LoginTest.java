package teamfour.com.rottentomatoes;

import android.test.ActivityInstrumentationTestCase2;

import models.UserModel;
import services.APIServiceInterface;

/**
 * Created by Jeremy on 4/6/16.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private LoginActivity loginActivity;
    private APIServiceInterface service;
    private RegistrationActivity regActivity;

    public LoginTest() { super(LoginActivity.class); }

    public void setUp() throws Exception {
        super.setUp();
        loginActivity = getActivity();
    }

    public void testConditions() {
        assertNotNull("loginActivity is null", loginActivity);
    }

    public void testEmptyLogin() {
        assertFalse(loginActivity.login("", ""));
    }

    public void testLogin() {
        boolean user = regActivity.register("username", "password", "password", "CS");

        //creating a user to check against
        assertFalse(user && loginActivity.login("", "password"));
        assertFalse(user && loginActivity.login("username", ""));
        assertTrue(user && loginActivity.login("username", "password"));
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }
}
