package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by logan on 2/27/16.
 */
public class RatingModel implements Parcelable {

    @JsonProperty("ratingID")
    public int ratingID;

    @JsonProperty("rating")
    public int rating;

    @JsonProperty("text")
    public String text;

    @JsonProperty("movieTitle")
    public String movieTitle;

    @JsonProperty("user")
    public String user;

    @JsonProperty("major")
    public String major;

    /**
     * set up a rating model
     * @param rID
     * @param r
     * @param t
     * @param mT
     * @param u
     */
    public RatingModel(int rID, int r, String t, String mT, String u, String m) {
        ratingID = rID;
        rating = r;
        text = t;
        movieTitle = mT;
        user = u;
        major = m;
    }

    /**
     * empty init for Jackson compatibility
     */
    public RatingModel() {}

    /**
     * Recreate user model from parcelable data
     * @param in
     */
    public RatingModel(Parcel in){
        final String[] data = new String[6];

        in.readStringArray(data);
        int integerRadix = 10;
        this.ratingID = Integer.parseInt(data[0], integerRadix);
        this.rating = Integer.parseInt(data[1], integerRadix);
        this.text = data[2];
        this.movieTitle = data[3];
        this.user = data[4];
        this.major = data[5];
    }
    /**
     * stub function, idk, it's required
     * @return
     */
    @Override
    public final int describeContents() {
        return 0;
    }

    /**
     * Transcribe the contents of this object to a parcel
     * @param dest
     * @param flags
     */
    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
            String.valueOf(this.ratingID),
            String.valueOf(this.rating),
            this.text,
            this.movieTitle,
            this.user,
            this.major
        });
    }

    /**
     * Implement parcelable interface
     */
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public RatingModel createFromParcel(Parcel in) {
            return new RatingModel(in);
        }

        public RatingModel[] newArray(int size) {
            return new RatingModel[size];
        }
    };
}
