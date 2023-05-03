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

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyHolder> {
    private View view;
    private Context context;
    private ArrayList<String> history_list;

    public HistoryAdapter(Context context, ArrayList<String> history_list) {
        this.context = context;
        this.history_list = history_list;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView history;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            history = view.findViewById(R.id.history);
        }
    }
    @NonNull
    @Override
    public HistoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.history_list,parent
        ,false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyHolder holder, int position) {
        holder.history.setText(history_list.get(position));
    }

    @Override
    public int getItemCount() {
        return history_list.size();
    }
}
