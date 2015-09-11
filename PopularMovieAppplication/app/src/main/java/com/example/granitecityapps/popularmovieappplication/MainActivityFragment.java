package com.example.granitecityapps.popularmovieappplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    View fragmentView;



    MoviePosterAdapter movieAdapter;
    public static MoviePosterData mSelectedMovieData;
    private boolean mHasSavedContent = false;
    private ArrayList<MoviePosterData> mMovieQueryResults = new ArrayList<MoviePosterData>();
    public MainActivityFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!mHasSavedContent)
            fetchMovieData();
    }

    private void fetchMovieData() {
        FetchMoivePoster moviePosterTask = new FetchMoivePoster();
        //Fetch the movie sorting preferences and retreve the data accordingly
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortSetting = prefs.getString(getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_default));

        moviePosterTask.execute(sortSetting);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        if(savedInstanceState != null && savedInstanceState.containsKey(MOVIE_ADAPTER_KEY))
        {
            mHasSavedContent = true;
            mMovieQueryResults = savedInstanceState.getParcelableArrayList(MOVIE_ADAPTER_KEY);
        }
        movieAdapter = new MoviePosterAdapter(getActivity(), mMovieQueryResults);
        GridView movieGridView = (GridView)fragmentView.findViewById(R.id.movie_poster_gridview);
        movieGridView.setAdapter(movieAdapter);
        //Wiring up the gridview to allow intent launching on detail
        movieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //need to pass the class containing the movie information here.
                //TODO: get the rest of the information about each movie and add it to the movie data class
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                mSelectedMovieData = mMovieQueryResults.get(position);
                startActivity(intent);
            }
        });


        return fragmentView;





    }

    static final String MOVIE_ADAPTER_KEY = "movieAdapter"; //key for the movie adapter

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //here I will save the member variables
        outState.putParcelableArrayList(MOVIE_ADAPTER_KEY, mMovieQueryResults);
        super.onSaveInstanceState(outState);



    }




    public class FetchMoivePoster extends AsyncTask<String, Void, List<MoviePosterData>> {

        private final String LOG_TAG = FetchMoivePoster.class.getSimpleName();
        private final String API_KEY = "dcadc1b0ce504424cc0ec3c4c81d203e";
        private final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
        private final String POPULAR_PARAMETER = "popularity.desc";
        private final String RATING_PARAMETER = "vote_average.desc";

        protected void onPostExecute(List<MoviePosterData> result)  {
            if(result != null )
            {
                mMovieQueryResults.clear();
                for(MoviePosterData movieObject : result)
                {
                    mMovieQueryResults.add(movieObject);
                }
                movieAdapter.notifyDataSetChanged();
            }


        }

        @Override
        protected List<MoviePosterData> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieJSONString = null;
            String desiredParameter = "";
            List<MoviePosterData> mResultsList = new ArrayList<MoviePosterData>();

            if(params.length > 0) //configure the generic parameter
                if(params[0].equals("rating"))
                    desiredParameter = RATING_PARAMETER;
                else
                    desiredParameter = POPULAR_PARAMETER;
            else
                desiredParameter = POPULAR_PARAMETER;

            //Construct the URL
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter("sort_by", desiredParameter)
                    .appendQueryParameter("api_key", API_KEY)
                    .build();

            Log.d(LOG_TAG, builtUri.toString());
            try
            {
                URL url = new URL(builtUri.toString());
                // Create the request to movie database, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJSONString = buffer.toString();


            }
            catch(IOException ex)
            {
                //Failed to establish connection to movie database, dont parse
                Log.e(LOG_TAG,ex.getMessage());
                return null;
            }
            finally {
                if(urlConnection != null)
                    urlConnection.disconnect(); //close the connection
                if(reader != null)
                    try{
                        reader.close();
                    }
                   catch (IOException e) {
                       Log.e(LOG_TAG, "Error closing stream" + e);
                    }


            }

            //Now to parse the data for the movies
            try{
                MovieDataParser parser = new MovieDataParser(movieJSONString);
                mResultsList = parser.GetMovieTitles();

                //At this point I have an object list of the movies titles, and poster image
                //Need to feed these into my adapter


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return mResultsList;
        }
    }


}

