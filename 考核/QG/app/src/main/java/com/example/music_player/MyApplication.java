package com.example.music_player;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    //全局Context
    public static Context getContextObject() {
        return context;
    }
}
