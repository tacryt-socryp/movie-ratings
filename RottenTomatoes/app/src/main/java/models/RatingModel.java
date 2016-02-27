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

    /**
     * set up a rating model
     * @param rID
     * @param r
     * @param t
     * @param mT
     * @param u
     */
    public RatingModel(int rID, int r, String t, String mT, String u) {
        ratingID = rID;
        rating = r;
        text = t;
        movieTitle = mT;
        user = u;
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
        String[] data = new String[5];

        in.readStringArray(data);
        this.ratingID = Integer.parseInt(data[0], 10);
        this.rating = Integer.parseInt(data[1], 10);
        this.text = data[2];
        this.movieTitle = data[3];
        this.user = data[4];
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
                String.valueOf(this.ratingID),
                String.valueOf(this.rating),
                this.text,
                this.movieTitle,
                this.user
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public RatingModel createFromParcel(Parcel in) {
            return new RatingModel(in);
        }

        public RatingModel[] newArray(int size) {
            return new RatingModel[size];
        }
    };
}
