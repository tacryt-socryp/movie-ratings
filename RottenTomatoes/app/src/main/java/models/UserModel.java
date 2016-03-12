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

    @JsonProperty("status")
    public String status;

    /**
     * Recreate user model from parcelable data
     * @param in
     */
    public UserModel(Parcel in){
        String[] data = new String[6];

        in.readStringArray(data);
        this.username = data[0];
        this.password = data[1];
        ProfileModel prof = new ProfileModel();
        prof.name = data[2];
        prof.major = data[3];
        prof.profileID = Integer.parseInt(data[4]);
        this.profile = prof;
        this.status = data[5];
    }

    /**
     * stub function, idk, it's required
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Transcribe the contents of this object to a parcel
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.username,
                this.password,
                this.profile.name,
                this.profile.major,
                Integer.toString(this.profile.profileID, 10),
                this.status
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

    /**
     * initializer for creating a user for registration
     * @param user
     * @param pass
     */
    public UserModel(String user, String pass) {
        this(user, pass, new ProfileModel("","",-1));
    }

    /**
     * initialize user with all data
     * @param user
     * @param pass
     * @param p
     */
    public UserModel(String user, String pass, ProfileModel p) {
        username = user;
        password = pass;
        profile = p;
        status = "active";
    }

    /**
     * empty init for Jackson compatibility
     */
    public UserModel() {}

}
