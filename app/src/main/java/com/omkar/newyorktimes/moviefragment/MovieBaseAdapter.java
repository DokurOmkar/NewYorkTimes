package com.omkar.newyorktimes.moviefragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.omkar.newyorktimes.newyorktimes.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by omkardokur on 12/18/15.
 */
public class MovieBaseAdapter extends BaseAdapter {
    ArrayList<HashMap<String,String>> movieData = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;

    public MovieBaseAdapter(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.movieData = data;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return movieData.size();
    }

    @Override
    public Object getItem(int position) {
        return movieData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieHolder movieHolder = null;
        View row = convertView;
        if(row == null){
            row = layoutInflater.inflate(R.layout.movie_single_row,parent,false);
            movieHolder = new MovieHolder(row);
            row.setTag(movieHolder);
        }
        else {
            movieHolder = (MovieHolder)row.getTag();

        }

        HashMap<String,String> currentItem = movieData.get(position);

        Picasso.with(context).load(currentItem.get("src")).resize(100,100).into(movieHolder.movieImage);
        movieHolder.movieTitle.setText(currentItem.get("display_title"));
        movieHolder.movieRating.setText(currentItem.get("mpaa_rating"));
        movieHolder.movieDescription.setText(currentItem.get("summary_short"));
        return row;
    }
}
