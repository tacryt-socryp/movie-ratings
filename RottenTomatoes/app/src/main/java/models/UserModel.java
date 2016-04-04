package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by logan on 2/6/16.
 */
public class UserModel implements Parcelable {
    /**
     * username
     */
    @JsonProperty("username")
    public String username;

    /**
     * password
     */
    @JsonProperty("password")
    public String password;

    /**
     * profile
     */
    @JsonProperty("profile")
    public ProfileModel profile;

    /**
     * isAdmin
     */
    @JsonProperty("isAdmin")
    public boolean isAdmin;

    /**
     * isActive
     */
    @JsonProperty("isActive")
    public boolean isActive;

    /**
     * status
     */
    public String status;

    /**
     * Recreate user model from parcelable data
     * @param in in
     */
    public UserModel(Parcel in){
        final int dataLength = 7;
        final String[] data = new String[dataLength];

        in.readStringArray(data);
        this.username = data[0];
        this.password = data[1];
        final ProfileModel prof = new ProfileModel();
        prof.name = data[2];
        prof.major = data[3];
        prof.profileID = Integer.parseInt(data[4]);
        this.profile = prof;
        this.isAdmin = Boolean.getBoolean(data[5]);
        this.isActive = Boolean.getBoolean(data[6]);
    }

    /**
     * stub function, idk, it's required
     * @return int
     */
    @Override
    public final int describeContents() {
        return 0;
    }

    /**
     * Transcribe the contents of this object to a parcel
     * @param dest dest
     * @param flags flags
     */
    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        final int integerRadix = 10;
        dest.writeStringArray(new String[] {
            this.username,
            this.password,
            this.profile.name,
            this.profile.major,
            Integer.toString(this.profile.profileID, integerRadix),
            Boolean.toString(this.isAdmin),
            Boolean.toString(this.isActive)
        });
    }

    /**
     * Needed to implement Parcelable
     */
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
     * @param user user
     * @param pass pass
     */
    public UserModel(String user, String pass) {
        this(user, pass, new ProfileModel("","",-1));
    }

    /**
     * initialize user with all data
     * @param user user
     * @param pass pass
     * @param p p
     */
    public UserModel(String user, String pass, ProfileModel p) {
        username = user;
        password = pass;
        profile = p;
        isActive = true;
        isAdmin = false;
    }

    /**
     * optional constructor when isAdmin is true
     * initialize user with all data
     * @param user user
     * @param pass pass
     * @param p p
     * @param admin admin
     */
    public UserModel(String user, String pass, ProfileModel p, Boolean admin) {
        username = user;
        password = pass;
        profile = p;
        isAdmin = admin;
        isActive = true;
    }

    /**
     * empty init for Jackson compatibility
     */
    public UserModel() {}

}
