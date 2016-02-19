package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by logan on 2/6/16.
 */
public class UserModel implements Parcelable {
    @JsonProperty("username")
    public String username;

    @JsonProperty("password")
    public String password;

    @JsonProperty("profile")
    public ProfileModel profile;

    // Parcelling part
    public UserModel(Parcel in){
        String[] data = new String[4];

        in.readStringArray(data);
        this.username = data[0];
        this.password = data[1];
        ProfileModel prof = new ProfileModel();
        prof.name = data[2];
        prof.profileID = Integer.parseInt(data[3]);
        this.profile = prof;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.username,
                this.password,
                this.profile.name,
                Integer.toString(this.profile.profileID, 10)
        });
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

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
