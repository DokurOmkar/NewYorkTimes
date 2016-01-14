package com.omkar.newyorktimes.eventlistfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.omkar.newyorktimes.moviefragment.MovieHolder;
import com.omkar.newyorktimes.newyorktimes.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by omkardokur on 1/2/16.
 */
public class EventBaseAdapter extends BaseAdapter {

    ArrayList<HashMap<String,String>> eventData = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    public EventBaseAdapter(Context context, ArrayList<HashMap<String, String>> eventData) {
        this.context = context;
        this.eventData = eventData;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return eventData.size();
    }

    @Override
    public Object getItem(int position) {
        return eventData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventHolder eventHolder = null;
        View row = convertView;
        if(row == null){
            row = layoutInflater.inflate(R.layout.event_single_row,parent,false);
            eventHolder = new EventHolder(row);
            row.setTag(eventHolder);
        }
        else {
            eventHolder = (EventHolder)row.getTag();

        }

        HashMap<String,String> currentItem = eventData.get(position);

        eventHolder.eventNameTextView.setText(currentItem.get("event_name"));
        eventHolder.eventLocationTextView.setText("Location: " + currentItem.get("city"));
        eventHolder.eventDescTextView.setText("Description: " + "\n" +currentItem.get("web_description"));
        eventHolder.eventUrlTextView.setText("Click here for more details: " +"\n"+currentItem.get("event_detail_url"));
        return row;

    }
}
