package teamfour.com.rottentomatoes;

/**
 * Created by EstellaD on 2/5/16.
 */
public class User {
    String name;
    String password;

    public User(String name, String password)
    {
        this.name = name;
        this.password = password;
    }

    public boolean checkPassword(String password)
    {
        return this.password.equals(password);
    }
}
