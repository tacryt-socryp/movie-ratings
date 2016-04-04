package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by EstellaD on 2/25/16.
 */
public class UserListModel implements Parcelable {

    @JsonProperty("users")
    public UserModel[] users;

    @Override
    public final int describeContents(){
        return 0;
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
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
