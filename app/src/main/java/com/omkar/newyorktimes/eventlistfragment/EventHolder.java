package com.omkar.newyorktimes.eventlistfragment;

import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.omkar.newyorktimes.newyorktimes.R;

/**
 * Created by omkardokur on 1/3/16.
 */
public class EventHolder {
    public TextView eventNameTextView, eventLocationTextView, eventDescTextView, eventUrlTextView;

    public EventHolder(View view){

        eventNameTextView = (TextView) view.findViewById(R.id.eventNameTv);
        eventLocationTextView = (TextView) view.findViewById(R.id.eventLocationTv);
        eventDescTextView = (TextView) view.findViewById(R.id.eventDescriptionTv);
        eventUrlTextView = (TextView) view.findViewById(R.id.eventUrlTv);

    }

}
