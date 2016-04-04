package models;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by logan on 2/15/16.
 */
public class ProfileModel {

    /**
     * name
     */
    @JsonProperty("name")
    public String name;
    /**
     * major
     */
    @JsonProperty("major")
    public String major;
    /**
     * profileID
     */
    @JsonProperty("profileID")
    public int profileID;

    /**
     * Make a user profile with the full name of the user and their profile ID
     * @param n n
     * @param m m
     * @param pID pID
     */
    public ProfileModel(String n, String m, int pID) {
        name = n;
        major = m;
        profileID = pID;
    }

    /**
     * empty init for Jackson compatibility
     */
    public ProfileModel() {}
}