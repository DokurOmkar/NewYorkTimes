package com.omkar.newyorktimes.eventlistfragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
 * Created by omkardokur on 1/2/16.
 */
public class EventAsyncTask extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {
    private Context context;
    private int mMethod;
    private List<NameValuePair> paramsValues;
    private ProgressDialog progressDialog;
    private ResultsCallback resultCallback;
    SqliteHelper sqliteHelper;

    public EventAsyncTask(ResultsCallback resultCallback, Context context, int method, List<NameValuePair> paramsValues) {
        this.resultCallback = resultCallback;
        this.context = context;
        mMethod = method;
        this.paramsValues = paramsValues;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        sqliteHelper = new SqliteHelper(context);
      //  SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        //db.delete(sqliteHelper.EVENT_TABLE, null, null);

    }

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(String... params) {
        ServiceHandler serviceHandler = new ServiceHandler();
        String response = serviceHandler.makeConnection(params[0],mMethod, paramsValues);
        ArrayList<HashMap<String,String>> results = new ArrayList<>();
        HashMap<String, String> currentMap = null;
        Event event = new Event();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray resultsList = jsonObject.getJSONArray("results");

            for(int i=0; i<resultsList.length(); i++){
                currentMap = new HashMap<>();
                JSONObject resultsListJSONObject =resultsList.getJSONObject(i);

                //Event name
                String eventName = resultsListJSONObject.getString("event_name");
                currentMap.put("event_name", eventName);
                event.setEventName(eventName);

                //Event Detail URL
                String eventUrl = resultsListJSONObject.getString("event_detail_url");
                currentMap.put("event_detail_url", eventUrl);
                event.setEventDetailUrl(eventUrl);

                //Event Description
                String eventDescription = resultsListJSONObject.getString("web_description");
                currentMap.put("web_description", eventDescription);
                event.setEventDescription(eventDescription);

                // Event Location
                String eventLocation = resultsListJSONObject.getString("city");
                currentMap.put("city", eventLocation);
                event.setEventLocation(eventLocation);

                if(currentMap!=null && !currentMap.isEmpty()){
                    results.add(currentMap);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return results;
    }

    @Override
    protected void onPostExecute(ArrayList<HashMap<String, String>> eventResult) {
        super.onPostExecute(eventResult);
        //Log.e("re", eventResult.toString());
        resultCallback.onPostExecute(eventResult);
        progressDialog.dismiss();
    }
}
