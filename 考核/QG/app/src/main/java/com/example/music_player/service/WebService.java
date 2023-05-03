package com.example.music_player.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.music_player.web_util.Web;


public class WebService extends Service {
    public WebService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Web.sendRequestWithOkHttp("https://api.hiles.live/top/song?type=0");
        return START_STICKY;//服务被杀死后会自动重启
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
