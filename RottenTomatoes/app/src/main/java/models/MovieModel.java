package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by logan on 2/21/16.
 */
public class MovieModel implements Parcelable {

    int dataLength = 20;

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

    public RatingModel[] ratings;

    /**
     * Recreate user model from parcelable data
     * @param in
     */
    public MovieModel(Parcel in){
        final String[] data = new String[dataLength];
        in.readStringArray(data);
        this.title = data[0];
        this.year = data[1];
        this.rated = data[2];
        this.released = data[3];
        this.runtime = data[4];
        this.genre = data[5];
        this.director = data[6];
        this.writer = data[7];
        this.actors = data[8];
        this.plot = data[9];
        this.lang = data[10];
        this.country = data[11];
        this.awards = data[12];
        this.poster = data[13];
        this.metascore = data[14];
        this.imdbrating = data[15];
        this.imdbvotes = data[16];
        this.imdbid = data[17];
        this.type = data[18];
        this.response = data[19];
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
            this.title,
            this.year,
            this.rated,
            this.released,
            this.runtime,
            this.genre,
            this.director,
            this.writer,
            this.actors,
            this.plot,
            this.lang,
            this.country,
            this.awards,
            this.poster,
            this.metascore,
            this.imdbrating,
            this.imdbvotes,
            this.imdbid,
            this.type,
            this.response
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

    public final String toString() {
        return title + "\n" + year + "\n";
    }

    /**
     * Empty movie model constructor
     */
    public MovieModel() {}
}