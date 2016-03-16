package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EstellaD on 2/25/16.
 */
public class UserListModel implements Parcelable {

    @JsonProperty("users")
    public List<UserModel> users = new ArrayList<UserModel>();

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
        });
    }

    /**
     * Needed to include to implement parcelable
     */
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserListModel createFromParcel(Parcel in) {
            return new UserListModel(in);
        }
        public UserListModel[] newArray(int size) {
            return new UserListModel[size];
        }
    };

    /**
     * Constructor using parcelable stuff
     * @param in
     */
    public UserListModel(Parcel in) {
        String[] data = new String[2];

        in.readStringArray(data);
    }

    public UserListModel() {}
}
