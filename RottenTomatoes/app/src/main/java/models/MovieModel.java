package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by logan on 2/21/16.
 */
public class MovieModel implements Parcelable {
    @JsonProperty("Title")
    public String title;

    @JsonProperty("Year")
    public String year;

    @JsonProperty("Rated")
    public String rated;

    @JsonProperty("Released")
    public String released;

    @JsonProperty("Runtime")
    public String runtime;

    @JsonProperty("Genre")
    public String genre;

    @JsonProperty("Director")
    public String director;

    @JsonProperty("Writer")
    public String writer;

    @JsonProperty("Actors")
    public String actors;

    @JsonProperty("Plot")
    public String plot;

    @JsonProperty("Language")
    public String lang;

    @JsonProperty("Country")
    public String country;

    @JsonProperty("Awards")
    public String awards;

    @JsonProperty("Poster")
    public String poster;

    @JsonProperty("Metascore")
    public String metascore;

    @JsonProperty("imdbRating")
    public String imdbrating;

    @JsonProperty("imdbVotes")
    public String imdbvotes;

    @JsonProperty("imdbID")
    public String imdbid;

    @JsonProperty("Type")
    public String type;

    @JsonProperty("Response")
    public String response;

    // TODO: implement Parcelable!

    /**
     * Recreate user model from parcelable data
     * @param in
     */
    public MovieModel(Parcel in){
        String[] data = new String[2];

        in.readStringArray(data);
        this.title = data[0];
        this.year = data[1];
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
                this.title,
                this.year
                //this.profile.name,
                //Integer.toString(this.profile.profileID, 10)
        });
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getTitle()
    {
        return this.title;
    }

    public String getYear()
    {
        return this.year;
    }

    public String getGenre()
    {
        return this.genre;
    }

    public MovieModel() {}
}