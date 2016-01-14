package com.omkar.newyorktimes.eventlistfragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.omkar.newyorktimes.moviefragment.MovieBaseAdapter;
import com.omkar.newyorktimes.newyorktimes.ConnectionDetector;
import com.omkar.newyorktimes.newyorktimes.R;
import com.omkar.newyorktimes.newyorktimes.ResultsCallback;
import com.omkar.newyorktimes.newyorktimes.ServiceHandler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by omkardokur on 12/30/15.
 */
public class EventListingFragment extends Fragment implements ResultsCallback {
    Context mContext;
    String eventListingUrl = "http://api.nytimes.com/svc/events/v2/listings.json?api-key=11c7bb04c8dfb124db398b79c35d34b5%3A9%3A73774230";
    ListView listView;

//    public EventListingFragment(Context mContext) {
//        this.mContext = mContext;
//    }

    public EventListingFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_eventlisting, container, false);
        listView = (ListView) rootView.findViewById(R.id.eventListView);

        // Checks for Wi-Fi and Mobile Data connection
        ConnectionDetector connectionDetector = new ConnectionDetector(getActivity());
        if(connectionDetector.isOnline()){
            serverConnection();
        }else{
            Log.e("There is no network", "WIFI");
           // databaseConnection();
        }

        return rootView;
    }

    private void serverConnection() {
        EventAsyncTask downloadAsyc =new EventAsyncTask(this,getActivity(), ServiceHandler.GET,null);
        downloadAsyc.execute(eventListingUrl);
    }
    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(ArrayList<HashMap<String, String>> results) {
        listView.setAdapter(new EventBaseAdapter(getActivity(),results));

    }
}
