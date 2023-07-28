package com.example.intelligentinsertion.Service;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.intelligentinsertion.WebSocket.InitSocketThread;

public class BackService extends Service{
    private static final String TAG = "BackService";
    protected Handler handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: service create");
        new InitSocketThread(this, handler).start();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public BackService getMyService() {
            return BackService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (InitSocketThread.mWebSocket != null) {
            InitSocketThread.mWebSocket.close(1000, null);
        }
    }
}
