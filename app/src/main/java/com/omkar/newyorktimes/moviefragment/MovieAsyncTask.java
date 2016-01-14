package com.omkar.newyorktimes.moviefragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;


import com.omkar.newyorktimes.moviefragment.Movie;
import com.omkar.newyorktimes.newyorktimes.ResultsCallback;
import com.omkar.newyorktimes.newyorktimes.ServiceHandler;
import com.omkar.newyorktimes.newyorktimes.SqliteHelper;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by omkardokur on 12/15/15.
 */
public class MovieAsyncTask extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {
    private Context context;
    private int mMethod;
    private List<NameValuePair> paramsValues;
    private ProgressDialog progressDialog;
    private ResultsCallback resultCallback;
    SqliteHelper sqliteHelper;


    public MovieAsyncTask(ResultsCallback resultCallback, Context context, int method, List<NameValuePair> params) {
        this.context = context;
        mMethod = method;
        this.paramsValues = params;
        this.resultCallback =resultCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        sqliteHelper = new SqliteHelper(context);
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        db.delete(sqliteHelper.MOVIE_TABLE, null, null);


    }

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(String... params) {
        ServiceHandler serviceHandler = new ServiceHandler();
        String response = serviceHandler.makeConnection(params[0],mMethod, paramsValues);
        ArrayList<HashMap<String,String>> results = new ArrayList<>();
        HashMap<String, String> currentMap = null;
        Movie movie = new Movie();

        try {

            JSONObject jsonObject = new JSONObject(response);
            JSONArray resultsList = jsonObject.getJSONArray("results");


            for(int i=0; i<resultsList.length(); i++){
                currentMap = new HashMap<>();
                JSONObject resultsListJSONObject =resultsList.getJSONObject(i);
                //Display Title
                String displayTitle = resultsListJSONObject.getString("display_title");
                currentMap.put("display_title", displayTitle);
                movie.setMovieName(displayTitle);
                //Rating
                String rating = resultsListJSONObject.getString("mpaa_rating");
                currentMap.put("mpaa_rating", rating);
                movie.setMovieRating(rating);
                //Short Summary
                String shortSummary = resultsListJSONObject.getString("summary_short");
                currentMap.put("summary_short",shortSummary);
                movie.setMovieDescription(shortSummary);

                // Gets image URL
                JSONObject multimediaObject = resultsListJSONObject.getJSONObject("multimedia");
                JSONObject resourceObject = multimediaObject.getJSONObject("resource");
                String imageURL = resourceObject.getString("src");
                currentMap.put("src",imageURL);
                movie.setImageURL(imageURL);

                (new SqliteHelper(context)).createMovieDetails(movie);

                if(currentMap!=null && !currentMap.isEmpty()){
                    results.add(currentMap);

                }

            }
          //  Log.e("current", results.toString());
        }

        catch (JSONException e) {
            e.printStackTrace();
        }

        return results;
    }

    @Override
    protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
        super.onPostExecute(result);
       // Log.e("re", result.toString());
        resultCallback.onPostExecute(result);
        progressDialog.dismiss();
    }
}
