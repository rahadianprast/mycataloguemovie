package com.dicoding.assosiate.mycataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.assosiate.mycataloguemovie.R;
import com.dicoding.assosiate.mycataloguemovie.activity.DetailMovieActivity;
import com.dicoding.assosiate.mycataloguemovie.model.MovieItems;

import java.util.ArrayList;

/**
 * Created by pmb on 3/21/18.
 */

public class MovieAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<MovieItems> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    RequestOptions option;

    public MovieAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        option = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading_shape)
                .error(R.drawable.loading_shape);
    }

    public void setData(ArrayList<MovieItems> items){
        mData = items;
        notifyDataSetChanged();
    }

    public void addItem(final MovieItems item){
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        mData.clear();
    }

    @Override
    public int getItemViewType(int position){
        return 0;
    }

    public int getViewTypeCount(){
        return 1;
    }

    @Override
    public int getCount() {
        if (mData==null) return 0;
        return mData.size();
    }

    @Override
    public MovieItems getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.movie_list_item, null);

            holder.img_poster = (ImageView)convertView.findViewById(R.id.list_poster_path);
            holder.tv_title = (TextView)convertView.findViewById(R.id.list_title);
            holder.tv_vote_average = (TextView)convertView.findViewById(R.id.list_vote_average);
            holder.tv_release_date = (TextView)convertView.findViewById(R.id.list_release_date);
            holder.tv_overview = (TextView)convertView.findViewById(R.id.list_overview);
            holder.view_container = (LinearLayout)convertView.findViewById(R.id.container);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        Glide.with(mContext).load(mData.get(position).getImg_poster()).apply(option).into(holder.img_poster);
        holder.tv_title.setText(mData.get(position).getTitle());
        holder.tv_vote_average.setText(mData.get(position).getVote());
        holder.tv_release_date.setText(mData.get(position).getRelease_date());
        holder.tv_overview.setText(mData.get(position).getOverview());
        holder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, DetailMovieActivity.class);
                i.putExtra("movie_title", mData.get(position).getTitle());
                i.putExtra("movie_vote", mData.get(position).getVote());
                i.putExtra("movie_release", mData.get(position).getRelease_date());
                i.putExtra("movie_overview", mData.get(position).getOverview());
                i.putExtra("movie_img", mData.get(position).getImg_poster());
                mContext.startActivity(i);
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        ImageView img_poster;
        TextView tv_title;
        TextView tv_vote_average;
        TextView tv_release_date;
        TextView tv_overview;
        LinearLayout view_container;
    }
}
