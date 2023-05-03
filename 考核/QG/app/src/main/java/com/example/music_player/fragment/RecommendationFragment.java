package com.example.music_player.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.music_player.MyApplication;
import com.example.music_player.R;
import com.example.music_player.adapter.MyRecyclerViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class RecommendationFragment extends Fragment {
    RecyclerView recyclerView;
    private ArrayList<String> music;
    ImageView[] imageViews;
    //图片资源id
    int[] imageResIds = {R.drawable.picture1, R.drawable.picture2, R.drawable.picture3, R.drawable.picture4};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);
        recyclerView = view.findViewById(R.id.recommend_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getContextObject());
        recyclerView.setLayoutManager(linearLayoutManager);
        InitData();
        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(MyApplication.getContextObject(), music);
        recyclerView.setAdapter(myRecyclerViewAdapter);

        return view;
    }


    public void InitData() {
        music = new ArrayList<>();
        File dir = new File("/storage/emulated/0/");//读取这个目录下面的文件
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".mp3")) {//判断是否为文件以及是MP3格式
                String name = file.getName();
                music.add(name);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 初始化ImageView数组并为每个ImageView设置图片、缩放类型和布局参数
        imageViews = new ImageView[imageResIds.length];
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i] = new ImageView(requireActivity());
            imageViews[i].setImageResource(imageResIds[i]);
            imageViews[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageViews[i].setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
        }

        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new BannerAdapter(imageViews));
    }

    @Override
    public void onStart() {
        View view = getView();
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        super.onStart();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int currentItem = viewPager.getCurrentItem();
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        }
                    });
                }
            }
        }, 3000, 3000);//3秒换一次

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    viewPager.setCurrentItem(imageResIds.length - 2, true);
                } else if (position == imageResIds.length - 1) {
                    viewPager.setCurrentItem(1, true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class BannerAdapter extends PagerAdapter {
        private ImageView[] imageViews;

        public BannerAdapter(ImageView[] imageViews) {
            this.imageViews = imageViews;
        }

        @Override
        public int getCount() {
            return imageResIds.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(imageViews[position % imageResIds.length]);
            return imageViews[position % imageResIds.length];
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position,
                                @NonNull Object object) {
            container.removeView((ImageView) object);
        }
    }


}