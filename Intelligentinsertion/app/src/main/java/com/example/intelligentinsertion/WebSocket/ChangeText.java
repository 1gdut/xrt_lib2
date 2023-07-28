package com.example.intelligentinsertion.WebSocket;

import androidx.lifecycle.MutableLiveData;

public class ChangeText {
    public static MutableLiveData<String> changeText = new MutableLiveData<>();

    public static MutableLiveData<String> getChangeText() {
        return changeText;
    }
}
