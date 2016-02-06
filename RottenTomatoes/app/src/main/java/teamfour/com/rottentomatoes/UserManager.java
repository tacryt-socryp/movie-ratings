package teamfour.com.rottentomatoes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by EstellaD on 2/5/16.
 */
public class UserManager {

    private static Map<String, User> users = new HashMap<>();

    public UserManager(){}

    public User findUser(String name)
    {
        return users.get(name);
    }

    public void addUser(String name, String password)
    {
        User user = new User(name, password);
        users.put(name, user);
    }

    public boolean handleLogin(String name, String password)
    {
        User user = findUser(name);
        if (user == null) return false;
        return user.checkPassword(password);
    }
}
