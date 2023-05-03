package com.example.music_player.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;


import com.example.music_player.R;
import com.example.music_player.adapter.MyRecyclerViewAdapter;


import java.io.File;
import java.util.ArrayList;

public class MyMusicActivity extends AppCompatActivity {
    private static final String TAG = "MyMusicActivity";
    private RecyclerView recyclerView;
    private ArrayList<String> music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_music);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        InitData();
        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(this, music);
        recyclerView.setAdapter(myRecyclerViewAdapter);
    }

    public void InitData() {
        music = new ArrayList<>();
        File dir = new File( "/storage/emulated/0/");//读取这个目录下面的文件
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".mp3")) {//判断是否为文件以及是MP3格式
                String name = file.getName();
                music.add(name);
            }
        }
        if (music.size() == 0) {
            Toast.makeText(this, "666", Toast.LENGTH_SHORT).show();
        }
    }
}


