package com.example.music_player.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player.R;

import java.util.ArrayList;

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.myHolder> {
    private View view;
    private Context context;
    private ArrayList<String> hot_list;

    public HotAdapter(Context context, ArrayList<String> hot_list) {
        this.context = context;
        this.hot_list = hot_list;
    }

    public class myHolder extends RecyclerView.ViewHolder {
        TextView hot;

        public myHolder(@NonNull View itemView) {
            super(itemView);
            hot = itemView.findViewById(R.id.hot);
        }
    }

    @NonNull
    @Override
    public HotAdapter.myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.hot_list,parent
                ,false);
        myHolder myHolder = new myHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HotAdapter.myHolder holder, int position) {
            holder.hot.setText(hot_list.get(position));
    }

    @Override
    public int getItemCount() {
        return hot_list.size();
    }
}
