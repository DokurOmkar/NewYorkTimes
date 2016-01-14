package com.omkar.newyorktimes.moviefragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.omkar.newyorktimes.newyorktimes.ConnectionDetector;
import com.omkar.newyorktimes.newyorktimes.R;
import com.omkar.newyorktimes.newyorktimes.ResultsCallback;
import com.omkar.newyorktimes.newyorktimes.ServiceHandler;
import com.omkar.newyorktimes.newyorktimes.SqliteHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by omkardokur on 12/30/15.
 */
public class MovieReviewFragment extends Fragment implements ResultsCallback {
    Context mContext;
    ListView listView;
    ArrayList<Movie> movieList;
    private String movieReviewUrl = "http://api.nytimes.com/svc/movies/v2/reviews/search.json?api-key=ae2e42965c987fc67d9b93c63681bcfe%3A6%3A73774230";
//    public MovieReviewFragment(Context context) {
//        this.mContext = context;
//    }

    public MovieReviewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_review, container, false);
        Button button = (Button) rootView.findViewById(R.id.button);
        listView = (ListView) rootView.findViewById(R.id.listView);

        // Checks for Wi-Fi and Mobile Data connection
        ConnectionDetector connectionDetector = new ConnectionDetector(getActivity());
        if(connectionDetector.isOnline()){
            serverConnection();
        }else{
            Log.e("There is no network", "WIFI");
            databaseConnection();
        }

        return rootView;
    }

    public void databaseConnection() {
        movieList = (new SqliteHelper(getActivity())).getMovieDetails();
        MovieOfflineAdapter adapter=new MovieOfflineAdapter(getActivity(),movieList);
        listView.setAdapter(adapter);
    }

    private void serverConnection() {
        MovieAsyncTask downloadAsyc=new MovieAsyncTask(this,getActivity(), ServiceHandler.GET,null);
        downloadAsyc.execute(movieReviewUrl);
    }


    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(ArrayList<HashMap<String, String>> results) {
        listView.setAdapter(new MovieBaseAdapter(getActivity(),results));
    }
}
