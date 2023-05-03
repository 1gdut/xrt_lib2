package com.example.music_player.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.music_player.MyApplication;
import com.example.music_player.R;
import com.example.music_player.activity.PlayMusicActivity;
import com.example.music_player.adapter.HistoryAdapter;
import com.example.music_player.adapter.HotAdapter;
import com.example.music_player.database.HotDatabase;
import com.example.music_player.service.HotService;
import com.example.music_player.service.WebService;
import com.example.music_player.web_util.Web;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindMusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindMusicFragment extends Fragment {
    private static final String TAG = "FindMusicFragment";
    private RecyclerView recyclerView, hotRecycler;
    private ArrayList<String> history_list, hot_list;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FindMusicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindMusicFragment newInstance(String param1, String param2) {
        FindMusicFragment fragment = new FindMusicFragment();
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
        View view = inflater.inflate(R.layout.fragment_find_music, container, false);
        EditText find_music_by_name = view.findViewById(R.id.find_music_by_name);
        Button clear = view.findViewById(R.id.clear);
        view.findViewById(R.id.find_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PlayMusicActivity.class);

                intent.putExtra("musicName", find_music_by_name.getText().toString() + ".mp3");
                startActivity(intent);

            }
        });
        recyclerView = view.findViewById(R.id.history_record);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getContextObject());
        recyclerView.setLayoutManager(linearLayoutManager);
        history_list = new ArrayList<>();
        HistoryAdapter historyAdapter = new HistoryAdapter(MyApplication.getContextObject(), history_list);
        recyclerView.setAdapter(historyAdapter);

        hotRecycler = view.findViewById(R.id.hot_search_recycler);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(MyApplication.getContextObject());
        hotRecycler.setLayoutManager(linearLayoutManager1);
        HotDatabase hotDatabase = new HotDatabase(MyApplication.getContextObject(), "Hot.db", null, 1);
        SQLiteDatabase writableDatabase = hotDatabase.getWritableDatabase();
        Intent intent = new Intent(getActivity(), HotService.class);
        getActivity().startService(intent);
        hot_list = new ArrayList<>();
        Cursor cursor = writableDatabase.query("Hot", null, null, null, null,
                null, null);
        if (cursor.moveToFirst()) {
            do {
                String hot_search = cursor.getString((int) cursor.getColumnIndex("hot_search"));
                hot_list.add(hot_search);
            } while (cursor.moveToNext());
        }
        cursor.close();
        System.out.println(hot_list.size());
        HotAdapter hotAdapter = new HotAdapter(MyApplication.getContextObject(), hot_list);
        hotRecycler.setAdapter(hotAdapter);

        find_music_by_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                history_list.add(editable.toString());//识别输入，加到集合
                historyAdapter.notifyItemInserted(history_list.size() );
                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < history_list.size(); i++) {
                                history_list.remove(i);
                                historyAdapter.notifyItemRemoved(i);

                        }
                    }
                });
            }
        });

        return view;
    }
}