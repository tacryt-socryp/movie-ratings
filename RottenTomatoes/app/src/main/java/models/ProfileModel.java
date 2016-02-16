package models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by logan on 2/15/16.
 */
public class ProfileModel {

    @SerializedName("name")
    public String name;

    @SerializedName("profileID")
    public int profileID;

    public ProfileModel(String n, int pID) {
        name = n;
        profileID = pID;
    }

}