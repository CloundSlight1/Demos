package com.wuyz.demos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyz on 2017/1/12.
 * MovieAdapter
 */

public class MovieAdapter extends RecyclerView.Adapter {

    private List<MovieData> items = new ArrayList<>();
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setItems(List<MovieData> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public List<MovieData> getItems() {
        return items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        MovieData data = items.get(position);
        myHolder.name.setText(data.getNm());
        myHolder.date.setText(data.getShowInfo());
        myHolder.desc.setText(data.getDesc());
        Glide.with(context).load(data.getImg().replaceAll("w.h", "50.80")).into(myHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView desc;
        private TextView date;
        private ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.mv_name);
            desc = (TextView) itemView.findViewById(R.id.mv_dec);
            date = (TextView) itemView.findViewById(R.id.mv_date);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
