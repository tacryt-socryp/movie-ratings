package models;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by logan on 2/15/16.
 */
public class ProfileModel {

    @JsonProperty("name")
    public String name;

    @JsonProperty("profileID")
    public int profileID;

    public ProfileModel(String n, int pID) {
        name = n;
        profileID = pID;
    }

    public ProfileModel() {}
}