package com.example.music_player.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.music_player.R;
import com.example.music_player.fragment.RecommendationFragment;
import com.example.music_player.fragment.FindMusicFragment;
import com.example.music_player.fragment.MyFragment;
import com.example.music_player.adapter.MyPagerAdapter;
import com.example.music_player.fragment.NewSongFragment;
import com.example.music_player.web_util.Web;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainViewActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<String> tab_title_list = new ArrayList<>();
    private ArrayList<Fragment> fragments_list = new ArrayList<>();
    private Fragment firstFragment, newSongFragment, secondFragment, myFragment;
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        viewPager = findViewById(R.id.my_viewPager);
        tabLayout = findViewById(R.id.my_tabLayout);

        //底部标签
        tab_title_list.add("推荐");
        tab_title_list.add("新歌");
        tab_title_list.add("搜索");
        tab_title_list.add("我");

        firstFragment = new RecommendationFragment();
        newSongFragment = new NewSongFragment();
        secondFragment = new FindMusicFragment();
        myFragment = new MyFragment();

        fragments_list.add(firstFragment);
        fragments_list.add(newSongFragment);
        fragments_list.add(secondFragment);
        fragments_list.add(myFragment);

        adapter = new MyPagerAdapter(getSupportFragmentManager(), tab_title_list, fragments_list);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);//可滑动换页

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.change_color, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//改变颜色
        switch (item.getItemId()) {
            case R.id.blue:
                tabLayout.setBackgroundColor(Color.BLUE);
                break;
            case R.id.red:
                tabLayout.setBackgroundColor(Color.RED);
                break;
            case R.id.green:
                tabLayout.setBackgroundColor(Color.GREEN);
                break;
            case R.id.gray:
                tabLayout.setBackgroundColor(Color.GRAY);
                break;
            case R.id.yellow:
                tabLayout.setBackgroundColor(Color.YELLOW);
                break;
            default:
                break;
        }
        return true;
    }

}