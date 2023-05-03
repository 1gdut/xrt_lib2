package com.example.music_player.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.music_player.MyApplication;
import com.example.music_player.R;
import com.example.music_player.adapter.MyRecyclerViewAdapter;
import com.example.music_player.database.LikeDatabase;

import java.util.ArrayList;

public class MyLikeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<String> music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_like);
        recyclerView = findViewById(R.id.my_like_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        music = new ArrayList<>();
        LikeDatabase likeDatabase = new LikeDatabase(MyApplication.getContextObject(), "Likes.db", null, 1);
        SQLiteDatabase writableDatabase = likeDatabase.getWritableDatabase();
        Cursor cursor = writableDatabase.query("Likes",null,null,null,
                null,null,null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString((int) cursor.getColumnIndex("song_name"));
                music.add(name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(MyApplication.getContextObject(), music);
        recyclerView.setAdapter(myRecyclerViewAdapter);
    }
}