package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EstellaD on 2/25/16.
 */
public class UserListModel {

    @JsonProperty("users")
    public UserModel[] users;

    public UserListModel() {}
}
