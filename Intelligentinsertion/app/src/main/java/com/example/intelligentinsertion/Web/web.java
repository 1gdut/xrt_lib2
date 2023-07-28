package com.example.intelligentinsertion.Web;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.intelligentinsertion.Bean.RegisterBean;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class web {
    //接口回调得到注册结果
    public interface OnRegisterListener {
        void getRegisterResponse(int code, String data);
    }

    //接口回调得到注册结果
    public interface OnLoginListener {
        void getLoginResponse(int code,String data);
    }

    public static void register(String jsonData, String url, OnRegisterListener onRegisterListener) {
        OkHttpClient client = new OkHttpClient();
        //格式
        MediaType JSON = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.Companion.create(jsonData, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("网络请求失败", "onFailure: " + e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String jsonData = response.body().string();
                Log.d("网络请求成功", "onResponse: " + jsonData);
                Gson gson = new Gson();
                RegisterBean registerBean = gson.fromJson(jsonData, RegisterBean.class);
                int code = registerBean.getCode();
                String data = registerBean.getData();
                //得到code
                onRegisterListener.getRegisterResponse(code, data);
            }
        });
    }

    public static void returnCode(String jsonData, OnRegisterListener onRegisterListener) {
        register(jsonData, "http://39.98.41.126:31133/users", onRegisterListener);
    }


    //+/l
    public static void login(String jsonData, String url, OnLoginListener onLoginListener) {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.Companion.create(jsonData, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("网络请求失败", "onFailure: " + e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String jsonData = response.body().string();
                Log.d("网络请求成功", "onResponse: " + jsonData);
                Gson gson = new Gson();
                RegisterBean registerBean = gson.fromJson(jsonData, RegisterBean.class);
                int code = registerBean.getCode();
                String data = registerBean.getData();
                onLoginListener.getLoginResponse(code,data);
            }
        });
    }

    public static void returnLoginCode(String jsonData, OnLoginListener onLoginListener) {
        login(jsonData, "http://39.98.41.126:31133/users/l", onLoginListener);
    }

    //接口回调得到注册结果
    public interface OnChangePwdListener {
        void getChangePwdResponse(int code);
    }

    public static void changePwd(String jsonData, String url, OnChangePwdListener onChangePwdListener) {
        OkHttpClient client = new OkHttpClient();
        //格式
        MediaType JSON = MediaType.Companion.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.Companion.create(jsonData, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("网络请求失败", "onFailure: " + e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String jsonData = response.body().string();
                Log.d("网络请求成功", "onResponse: " + jsonData);
                Gson gson = new Gson();
                RegisterBean registerBean = gson.fromJson(jsonData, RegisterBean.class);
                int code = registerBean.getCode();
                onChangePwdListener.getChangePwdResponse(code);
            }
        });
    }

    public static void returnChangePwdCode(String jsonData, OnChangePwdListener onChangePwdListener) {
        changePwd(jsonData, "http://39.98.41.126:31133/users/c", onChangePwdListener);
    }
}
