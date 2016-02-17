package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by logan on 2/6/16.
 */
public class UserModel {
    @JsonProperty("username")
    public String username;

    @JsonProperty("password")
    public String password;

    @JsonProperty("profile")
    public ProfileModel profile;

    public UserModel(String user, String pass) {
        this(user, pass, new ProfileModel("",-1));
    }

    public UserModel(String user, String pass, ProfileModel p) {
        username = user;
        password = pass;
        profile = p;
    }

    public UserModel() {}

}
