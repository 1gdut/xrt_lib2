package com.example.intelligentinsertion.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.intelligentinsertion.WebSocket.JWebSocketClient;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class JWebSocketClientService extends Service {
    private URI uri;
    public JWebSocketClient client;
    private JWebSocketClientBinder mBinder = new JWebSocketClientBinder();
    private OnMessageListener onMessageListener;//接口的声明

    //回调接口
    public interface OnMessageListener {
        void getMessage(String message);
    }

    //以回调接口为参数的方法
    public void setOnMessageListener(OnMessageListener onMessageListener) {
        this.onMessageListener = onMessageListener;
    }

    class JWebSocketClientBinder extends Binder {
        public JWebSocketClientService getService() {
            return JWebSocketClientService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 初始化websocket连接
     */
    private void initSocketClient() {
        uri = URI.create("ws://124.222.224.186:8800");//放地址
        client = new JWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                Log.d("JWebSocketClientService", "收到的消息：" + message);
                if (onMessageListener != null) {
                    onMessageListener.getMessage(message);
                }
            }

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                super.onOpen(handshakedata);
                Log.d("JWebSocketClientService", "websocket连接成功");
            }

            @Override
            public void onError(Exception ex) {
                super.onError(ex);
                Log.e("JWebSocketClientService", "onError: " + ex);
            }
        };
        connect();
    }

    private void connect() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //connectBlocking多出一个等待操作，会先连接再发送，否则未连接发送会报错
                    client.connectBlocking();
                } catch (InterruptedException e) {
                    Log.e("连接失败", "错误");
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private static final long HEART_BEAT_RATE = 10 * 1000;//每隔10秒进行一次对长连接的心跳检测
    private Handler mHandler = new Handler();
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d("JWebSocketClientService", "心跳包检测websocket连接状态");
            if (client != null) {
                if (client.isClosed()) {
                    reconnectWs();
                } else {
                    //业务逻辑 这里如果服务端需要心跳包为了防止断开 需要不断发送消息给服务端
                    Log.d("心跳检测", "run: 正常");
                    client.send("");
                }
            } else {
                //如果client已为空，重新初始化连接
                client = null;
                Log.d("心跳检测", "run: client已空");
                initSocketClient();
            }
            //每隔一定的时间，对长连接进行一次心跳检测
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    private void reconnectWs() {
        mHandler.removeCallbacks(heartBeatRunnable);
        new Thread() {
            @Override
            public void run() {
                try {
                    Log.e("JWebSocketClientService", "开启重连");
                    client.reconnectBlocking();
                } catch (InterruptedException e) {
                    Log.e("重连失败", "run: " + e);
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
