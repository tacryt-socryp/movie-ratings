package models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by logan on 2/6/16.
 */
public class UserModel {
    @SerializedName("username")
    public String username;

    @SerializedName("password")
    public String password;

    public ProfileModel profile;

    public UserModel(String user, String pass, ProfileModel p) {
        username = user;
        password = pass;
        profile = p;
    }

}
