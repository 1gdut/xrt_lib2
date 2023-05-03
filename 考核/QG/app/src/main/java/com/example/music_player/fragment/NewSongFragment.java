package com.example.music_player.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.music_player.MyApplication;
import com.example.music_player.R;
import com.example.music_player.adapter.NewSongAdapter;
import com.example.music_player.database.NewDatabase;

import com.example.music_player.service.WebService;
import com.example.music_player.web_util.Web;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewSongFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewSongFragment extends Fragment {
    private ArrayList<String> singer, song;
    private RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewSongFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewSongFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewSongFragment newInstance(String param1, String param2) {
        NewSongFragment fragment = new NewSongFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_song, container, false);
        singer = new ArrayList<>();
        song = new ArrayList<>();
        recyclerView = view.findViewById(R.id.new_song_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getContextObject());
        recyclerView.setLayoutManager(linearLayoutManager);
//        Web.sendRequestWithOkHttp("https://api.hiles.live/top/song?type=0");
        Intent intent = new Intent(getActivity(), WebService.class);
        getActivity().startService(intent);
        NewDatabase newDatabase = new NewDatabase(MyApplication.getContextObject(), "New.db", null, 1);
        SQLiteDatabase writableDatabase = newDatabase.getWritableDatabase();
        Cursor cursor = writableDatabase.query("New", null, null, null, null,
                null, null);
        if (cursor.moveToFirst()) {
            do {
                String singerName = cursor.getString((int) cursor.getColumnIndex("singerName"));
                String songName = cursor.getString((int) cursor.getColumnIndex("songName"));
                singer.add(singerName);
                song.add(songName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        NewSongAdapter newSongAdapter = new NewSongAdapter(MyApplication.getContextObject(), singer, song);
        recyclerView.setAdapter(newSongAdapter);
        return view;
    }
}