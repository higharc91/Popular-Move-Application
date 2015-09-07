package com.example.granitecityapps.popularmovieappplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jarredbeverly on 8/31/15.
 */
public class MovieDataParser {

    private String mJsonString = "";

    public MovieDataParser(String jsonString)
    {
        this.mJsonString = jsonString;
    }


    public List<MoviePosterData> GetMovieTitles()
            throws JSONException
    {
        final String DB_RESULTS = "results";
        final String DB_TITLE = "original_title";
        final String DB_Synopsis = "overview";
        final String DB_ReleaseDate = "release_date";
        final String DB_VoteAverage = "vote_average";
        final String DB_POSTER = "poster_path";
        final String MOVIEPIC_BASE_PATH = "http://image.tmdb.org/t/p/w780";
        JSONArray movieArray;
        List<MoviePosterData> movieDataList = new ArrayList<MoviePosterData>();
        if(mJsonString != null)
        {
            JSONObject movieJson = new JSONObject(mJsonString);
            movieArray = movieJson.getJSONArray(DB_RESULTS);
            MoviePosterData dataMan = new MoviePosterData();
            for(int i =0; i < movieArray.length(); i++)
            {
                JSONObject currentMovie = movieArray.getJSONObject(i);
                MoviePosterData movieData = new MoviePosterData();
                movieData.MovieTitle = currentMovie.getString(DB_TITLE);
                movieData.PicturePath = MOVIEPIC_BASE_PATH + currentMovie.getString(DB_POSTER);
                movieData.PlotSyn = currentMovie.getString(DB_Synopsis);
                movieData.ReleaseDate = currentMovie.getString(DB_ReleaseDate);
                movieData.VoteAverage = currentMovie.getString(DB_VoteAverage);

                movieDataList.add(movieData);
            }
        }
        return movieDataList;


    }



}
