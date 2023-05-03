package com.example.music_player.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.music_player.web_util.Web;

import java.security.Provider;

public class HotService extends Service {
    public HotService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Web.sendRequestWithOkHttp_hot_search("https://api.hiles.live/search/hot");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
