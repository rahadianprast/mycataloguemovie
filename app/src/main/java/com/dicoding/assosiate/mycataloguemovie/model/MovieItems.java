package com.dicoding.assosiate.mycataloguemovie.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pmb on 3/21/18.
 */

public class MovieItems {
    private String title;
    private String vote;
    private String release_date;
    private String overview;
    private String img_poster;


    public MovieItems(JSONObject object) {
        String poster_url = "http://image.tmdb.org/t/p/w185/";
        try {
            String title = object.getString("title");
            String vote = object.getString("vote_average");
            String release_date = object.getString("release_date");
            String overview = object.getString("overview");
            String img = poster_url + object.getString("poster_path");

            this.title = title;
            this.vote = vote;
            this.release_date = release_date;
            this.overview = overview;
            this.img_poster = img;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImg_poster() {
        return img_poster;
    }

    public void setImg_poster(String img_poster) {
        this.img_poster = img_poster;
    }
    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
