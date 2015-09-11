package com.example.granitecityapps.popularmovieappplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jarredbeverly on 9/1/15.
 */
public class MoviePosterData implements Parcelable {

    public String MovieTitle;
    public String PicturePath;
    public String ReleaseDate;
    public String VoteAverage;
    public String PlotSyn;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.MovieTitle);
        dest.writeString(this.PicturePath);
        dest.writeString(this.ReleaseDate);
        dest.writeString(this.VoteAverage);
        dest.writeString(this.PlotSyn);
    }

    public MoviePosterData() {
    }

    protected MoviePosterData(Parcel in) {
        this.MovieTitle = in.readString();
        this.PicturePath = in.readString();
        this.ReleaseDate = in.readString();
        this.VoteAverage = in.readString();
        this.PlotSyn = in.readString();
    }

    public static final Creator<MoviePosterData> CREATOR = new Creator<MoviePosterData>() {
        public MoviePosterData createFromParcel(Parcel source) {
            return new MoviePosterData(source);
        }

        public MoviePosterData[] newArray(int size) {
            return new MoviePosterData[size];
        }
    };
}
