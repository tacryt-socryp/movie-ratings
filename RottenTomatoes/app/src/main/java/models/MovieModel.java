package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by logan on 2/21/16.
 */
public class MovieModel implements Parcelable {

    /**
     * dataLength
     */
    private int dataLength = 20;

    /**
     * Title
     */
    @JsonProperty("Title")
    public String title;

    /**
     * Year
     */
    @JsonProperty("Year")
    public String year;

    /**
     * Rated
     */
    @JsonProperty("Rated")
    public String rated;

    /**
     * Released
     */
    @JsonProperty("Released")
    public String released;

    /**
     * Runtime
     */
    @JsonProperty("Runtime")
    public String runtime;

    /**
     * Genre
     */
    @JsonProperty("Genre")
    public String genre;

    /**
     * Director
     */
    @JsonProperty("Director")
    public String director;

    /**
     * Writer
     */
    @JsonProperty("Writer")
    public String writer;

    /**
     * Actors
     */
    @JsonProperty("Actors")
    public String actors;

    /**
     * Plot
     */
    @JsonProperty("Plot")
    public String plot;

    /**
     * Language
     */
    @JsonProperty("Language")
    public String lang;

    /**
     * Country
     */
    @JsonProperty("Country")
    public String country;

    /**
     * Awards
     */
    @JsonProperty("Awards")
    public String awards;

    /**
     * Poster
     */
    @JsonProperty("Poster")
    public String poster;

    /**
     * Metascore
     */
    @JsonProperty("Metascore")
    public String metascore;

    /**
     * imdbRating
     */
    @JsonProperty("imdbRating")
    public String imdbrating;

    /**
     * imdbVotes
     */
    @JsonProperty("imdbVotes")
    public String imdbvotes;

    /**
     * imdbID
     */
    @JsonProperty("imdbID")
    public String imdbid;

    /**
     * Type
     */
    @JsonProperty("Type")
    public String type;

    /**
     * Response
     */
    @JsonProperty("Response")
    public String response;

    /**
     * ratings
     */
    public RatingModel[] ratings;

    /**
     * Recreate user model from parcelable data
     * @param in in
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

    /**
     * Needed to implement Parcelable
     */
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };


    /**
     * toString for MovieModel
     * @return string
     */
    public final String toString() {
        return title + "\n" + year + "\n";
    }

    /**
     * Empty movie model constructor
     */
    public MovieModel() {}
}