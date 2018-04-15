package com.dicoding.assosiate.mycataloguemovie.util;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.dicoding.assosiate.mycataloguemovie.model.MovieItems;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pmb on 3/21/18.
 */

public class MovieTaskLoader extends AsyncTaskLoader<ArrayList<MovieItems>> {
    private ArrayList<MovieItems> mData;
    private boolean mHasResult = false;
    private String mJudulFilm ;
    public static final String STAT_ASYNC = "AsyncTaskLoader";


    public MovieTaskLoader(final Context context, String judulFilm) {
        super(context);

        onContentChanged();
        this.mJudulFilm = judulFilm;
    }

    // Data diload
    @Override
    protected void onStartLoading(){
        Log.d(STAT_ASYNC,"STATUS : onStartLoading");
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<MovieItems> data){
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset(){
        Log.d(STAT_ASYNC, "STATUS : onReset");
        super.onReset();
        onStopLoading();
        if (mHasResult){
            onReleaseResource(mData);
            mData = null;
            mHasResult =false;
        }
    }

    private static final String API_KEY = "c0a458446549eab3e5b991546602cdcd";

    private void onReleaseResource(ArrayList<MovieItems> mData) {
    }

    // format cari
    // https://api.themoviedb.org/3/search/movie?api_key=APIKEY&language=en-US&query=CARI

    @Override
    public ArrayList<MovieItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItems> movieItemArray = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language=en-US&query="+mJudulFilm;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart(){
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i =0; i< list.length();i++){
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItems = new MovieItems(movie);
                        movieItemArray.add(movieItems);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movieItemArray;
    }
}
