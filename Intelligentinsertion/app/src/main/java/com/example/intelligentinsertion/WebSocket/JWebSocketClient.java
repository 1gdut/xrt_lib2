package com.example.intelligentinsertion.WebSocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class JWebSocketClient extends WebSocketClient {
    public JWebSocketClient(URI serverUri) {
        super(serverUri);
    }


    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d("JWebSocketClient", "onOpen()");
    }

    @Override
    public void onMessage(String message) {
        Log.d("JWebSocketClient", "onMessage()" + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d("JWebSocketClient", "onClose()");
    }

    @Override
    public void onError(Exception ex) {
        Log.e("JWebSocketClient", "onError()" + ex);
    }
}
