package com.example.music_player.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player.MyApplication;
import com.example.music_player.R;
import com.example.music_player.activity.MainViewActivity;
import com.example.music_player.activity.PlayMusicActivity;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyHolder> {
    private static final String TAG = "MyRecyclerViewAdapter";
    private View view;
    private Context context;
    private ArrayList<String> musicList;

    public MyRecyclerViewAdapter(Context context, ArrayList<String> musicList) {
        this.context = context;
        this.musicList = musicList;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView musicName;
        ImageView picture;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            musicName = view.findViewById(R.id.music_name);
            picture = view.findViewById(R.id.picture);
            musicName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyApplication.getContextObject(), PlayMusicActivity.class);
                    Log.d(TAG, musicName.getText().toString());
                    intent.putExtra("musicName", musicName.getText().toString());//把名字传过去
                    /*
                    非活动(Activity)类(例如Service或BroadcastReceiver中启动新的Activity。
                    由于这些类没有自己的任务堆栈(task stack)，因此需要在启动Activity时添加FLAG ACTIVITY NEW TASK标志告诉系统为该Activity创建个新的任务堆栈
                    */
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.my_music_list,
                parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.MyHolder holder, int position) {
        holder.musicName.setText(musicList.get(position));
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }
}
