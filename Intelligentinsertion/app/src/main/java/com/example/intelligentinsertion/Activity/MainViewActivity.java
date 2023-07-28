package com.example.intelligentinsertion.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.intelligentinsertion.Fragment.FirstFragment;
import com.example.intelligentinsertion.Fragment.SecondFragment;
import com.example.intelligentinsertion.Fragment.ThirdFragment;
import com.example.intelligentinsertion.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainViewActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ImageView icon01, icon02, icon03;
    List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        Objects.requireNonNull(getSupportActionBar()).hide();
        viewPager = findViewById(R.id.viewPager);
        icon01 = findViewById(R.id.icon01);
        icon02 = findViewById(R.id.icon02);
        icon03 = findViewById(R.id.icon03);

        fragments = new ArrayList<>();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFragment());

        MyPageAdapter myPageAdapter = new MyPageAdapter(fragmentManager);
        viewPager.setAdapter(myPageAdapter);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    public class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Animation animation = null;
            switch (position) {
                case 0:
                    icon01.setImageResource(R.drawable.icon01_1);
                    icon02.setImageResource(R.drawable.icon02_0);
                    icon03.setImageResource(R.drawable.icon03_0);
                    break;
                case 1:
                    icon01.setImageResource(R.drawable.icon01_0);
                    icon02.setImageResource(R.drawable.icon02_1);
                    icon03.setImageResource(R.drawable.icon03_0);
                    break;
                case 2:
                    icon01.setImageResource(R.drawable.icon01_0);
                    icon02.setImageResource(R.drawable.icon02_0);
                    icon03.setImageResource(R.drawable.icon03_1);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}