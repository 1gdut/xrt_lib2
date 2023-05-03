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

public class NewSongAdapter extends RecyclerView.Adapter<NewSongAdapter.myHolder> {
    private View view;
    private Context context;
    private ArrayList<String> singer_name_list,song_name_list;

    public NewSongAdapter(Context context, ArrayList<String> singer_name_list, ArrayList<String> song_name_list) {
        this.context = context;
        this.singer_name_list = singer_name_list;
        this.song_name_list = song_name_list;
    }
public class myHolder extends RecyclerView.ViewHolder {
    TextView singerName,songName;
    public myHolder(@NonNull View itemView) {
        super(itemView);
        singerName = itemView.findViewById(R.id.singerName);
        songName = itemView.findViewById(R.id.songName);
    }
}
    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       view = LayoutInflater.from(context).inflate(R.layout.new_song_list,
               parent,false);
        myHolder myHolder = new myHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        holder.songName.setText(song_name_list.get(position));
        holder.singerName.setText(singer_name_list.get(position));
    }

    @Override
    public int getItemCount() {
        return singer_name_list.size();
    }
}
