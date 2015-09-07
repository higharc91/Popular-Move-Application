package com.example.granitecityapps.popularmovieappplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jarredbeverly on 8/30/15.
 */
public class MoviePosterAdapter extends BaseAdapter {



    //backing fields
    private static final String LOG_TAG = MoviePosterAdapter.class.getSimpleName();
    private LayoutInflater layoutInflater;
    List<MoviePosterData> results;
    Context context;


    public MoviePosterAdapter(Context mainActivity, List<MoviePosterData> movieObjectList) {
        this.context = mainActivity;
        this.results = movieObjectList;
        this.layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View movieView;
        if(convertView == null) {
            Holder holder = new Holder();
            movieView = layoutInflater.inflate(R.layout.grid_view_item_movie_poster,null); //inflate the view

            holder.imageView = (ImageView) movieView.findViewById(R.id.gridview_movieposter_imageview);
            //holder.textView = (TextView)movieView.findViewById(R.id.gridview_movietitle_textview);
            //now to set the variables based on the list index

            //holder.imageView.setImageDrawable(); this will need to be fixed so that it fetches the movie posters
            //holder.textView.setText(results.get(position).MovieTitle);
            Log.d(LOG_TAG, results.get(position).toString());
            Picasso.with(context).load(results.get(position).PicturePath).into(holder.imageView);

        }
        else {
            //This else statement is called when it the adapter recycles a view
            Holder holder = new Holder();
            movieView = (View) convertView;
            holder.imageView = (ImageView) movieView.findViewById(R.id.gridview_movieposter_imageview);
            Picasso.with(context).load(results.get(position).PicturePath).into(holder.imageView);
        }

        return movieView;
    }


    class Holder
    {
        ImageView imageView;
        TextView textView;
    }
}
