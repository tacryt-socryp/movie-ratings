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

    /**
     * Make a user profile with the full name of the user and their profile ID
     * @param n
     * @param pID
     */
    public ProfileModel(String n, int pID) {
        name = n;
        profileID = pID;
    }

    /**
     * empty init for Jackson compatibility
     */
    public ProfileModel() {}
}