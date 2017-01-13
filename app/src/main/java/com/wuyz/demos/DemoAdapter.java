package com.wuyz.demos;

import android.content.Context;
import android.content.Intent;
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

public class DemoAdapter extends RecyclerView.Adapter {

    private List<Class> items = new ArrayList<>();
    private Context context;

    public DemoAdapter(Context context) {
        this.context = context;
    }

    public void setItems(List<Class> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.demo_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        final Class data = items.get(position);
        myHolder.name.setText(data.getSimpleName());

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, data));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.demo_name);
        }
    }
}
