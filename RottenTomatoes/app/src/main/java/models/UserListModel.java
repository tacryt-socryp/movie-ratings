package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by EstellaD on 2/25/16.
 */
public class UserListModel {

    @JsonProperty("users")
    public ArrayList<UserModel> users;

    public UserListModel() {}
}
