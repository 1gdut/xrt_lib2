package com.example.intelligentinsertion.WebSocket;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.intelligentinsertion.Service.BackService;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class InitSocketThread extends Thread {
    private static final String TAG = "InitSocketThread";
    BackService service;
    Handler serviceHandler;

    public InitSocketThread() {
    }

    public InitSocketThread(BackService service, Handler handler) {
        this.service = service;
        this.serviceHandler = handler;
    }

    @Override
    public void run() {
        super.run();
        try {
            initSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final long HEART_BEAT_RATE = 10 * 1000;//每隔10秒进行一次对长连接的心跳检测
    private static final String WEBSOCKET_HOST_AND_PORT = "ws://124.222.224.186:8800";//可替换为自己的主机名和端口号
    public static WebSocket mWebSocket;

    public void initSocket() throws UnknownHostException, IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .connectTimeout(6000, TimeUnit.MINUTES)
                .build();
        Request request = new Request.Builder()
                .url(WEBSOCKET_HOST_AND_PORT).build();//地址
        Log.d(TAG, "initSocket: " + request.url().url());
        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosed(webSocket, code, reason);
                Log.d(TAG, "onClosed: onclose");
            }

            @Override
            public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
                mWebSocket = webSocket;
                Log.e("INITSOCKET", "initsocket onFailure");
                t.printStackTrace();
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                super.onMessage(webSocket, text);
                Log.d(TAG, "onMessage: 返回的消息是 " + text);
                ChangeText.changeText.postValue(text);//livedata
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                super.onOpen(webSocket, response);
                Log.d(TAG, "onOpen: onopen");
                mWebSocket = webSocket;
                mWebSocket.send("11111111111111111111");  //向服务器发送id
            }
        });
        mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);
    }

    private static long sendTime = 0L;
    // 发送心跳包
    private static Handler mHandler = new Handler();
    private static Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if (System.currentTimeMillis() - sendTime >= HEART_BEAT_RATE) {
                if (mWebSocket == null) {
                    mHandler.postDelayed(this, HEART_BEAT_RATE);//每隔一定的时间，对长连接进行一次心跳检测
                    return;
                }
                boolean isSuccess = mWebSocket.send("");//发送一个空消息给服务器，通过发送消息的成功失败来判断长连接的连接状态
                if (!isSuccess) {//长连接已断开
                    mHandler.removeCallbacks(heartBeatRunnable);
                    mWebSocket.cancel();//取消掉以前的长连接
                    new InitSocketThread().start();//创建一个新的连接
                } else {//长连接处于连接状态

                }
                sendTime = System.currentTimeMillis();
            }
            mHandler.postDelayed(this, HEART_BEAT_RATE);//每隔一定的时间，对长连接进行一次心跳检测
        }
    };
}
