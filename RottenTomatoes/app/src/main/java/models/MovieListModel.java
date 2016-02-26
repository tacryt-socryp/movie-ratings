package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EstellaD on 2/25/16.
 */
public class MovieListModel implements Parcelable {

    @JsonProperty("Search")
    public List<MovieModel> movies = new ArrayList<MovieModel>();

    @JsonProperty("totalResults")
    public String totalResults;

    @JsonProperty("Response")
    public String Response;

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
        public MovieListModel createFromParcel(Parcel in) {
            return new MovieListModel(in);
        }
        public MovieListModel[] newArray(int size) {
            return new MovieListModel[size];
        }
    };

    /**
     * Constructor using parcelable stuff
     * @param in
     */
    public MovieListModel(Parcel in) {
        String[] data = new String[2];

        in.readStringArray(data);
    }

    public MovieListModel() {}
}
