package com.example.granitecityapps.popularmovieappplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {


    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        if(MainActivityFragment.mSelectedMovieData != null)
        {
            //set the movie information
            TextView titleTextView = (TextView)fragment.findViewById(R.id.text_view_detail_movie_title);
            TextView releaseDateTextView = (TextView)fragment.findViewById(R.id.text_view_release_date);
            TextView movieVotes = (TextView)fragment.findViewById(R.id.text_view_movie_rating);
            TextView movieSynp = (TextView)fragment.findViewById(R.id.text_view_syn);
            ImageView posterImageView = (ImageView)fragment.findViewById(R.id.image_view_detail_poster);
            titleTextView.setText(MainActivityFragment.mSelectedMovieData.MovieTitle);
            releaseDateTextView.setText(MainActivityFragment.mSelectedMovieData.ReleaseDate);
            movieVotes.setText(MainActivityFragment.mSelectedMovieData.VoteAverage + "/10");
            movieSynp.setText(MainActivityFragment.mSelectedMovieData.PlotSyn);
            Picasso.with(getActivity()).load(MainActivityFragment.mSelectedMovieData.PicturePath).into(posterImageView);
        }
        return fragment;
    }






}
