package com.example.music_player.web_util;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.music_player.MyApplication;


import com.example.music_player.database.HotDatabase;
import com.example.music_player.database.NewDatabase;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import org.json.JSONArray;

import org.json.JSONObject;

import java.io.IOException;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Web {

    public static void sendRequestWithOkHttp_hot_search(String url) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();//先new一个OkHttpClient
                Request request = new Request.Builder()//发起HTTP请求就要创建这个
                        //指定访问的网址
                        .url(url)
                        .build();//build之前可以连很多东西，比如用url()方法来指定网址
                try {
                    Response response = client.newCall(request).execute();//发送请求并获取返回数据
                    String responseData = response.body().string();//得到具体内容
                    parseJSONWithGson_hot_search(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void parseJSONWithGson_hot_search(String jsonData) {
        HotDatabase hotDatabase = new HotDatabase(MyApplication.getContextObject(), "Hot.db", null, 1);
        SQLiteDatabase writableDatabase = hotDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        JsonElement element = new JsonParser().parse(jsonData);
        JsonObject obj = element.getAsJsonObject();
        String str = obj.toString();
        try {
            JSONObject jsonObj = new JSONObject(str);
            JSONObject resultObj = jsonObj.getJSONObject("result");
            JSONArray hotsArr = resultObj.getJSONArray("hots");
            writableDatabase.delete("Hot", null, null);
            for (int i = 0; i < hotsArr.length(); i++) {
                JSONObject hotsObj = hotsArr.getJSONObject(i);
                String firstStr = hotsObj.getString("first");
                values.put("hot_search", firstStr);
                writableDatabase.replace("Hot", null, values);
                values.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void sendRequestWithOkHttp(String url) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();//先new一个OkHttpClient
                Request request = new Request.Builder()//发起HTTP请求就要创建这个
                        //指定访问的网址
                        .url(url)
                        .build();//build之前可以连很多东西，比如用url()方法来指定网址
                try {
                    Response response = client.newCall(request).execute();//发送请求并获取返回数据
                    Log.d("222", "run: "+response.toString());
                    String responseData = response.body().string();//得到具体内容
                    Log.d("222", "run: "+response.body().toString());
                    Log.d("222", "run: "+responseData);
                    parseJSONWithGson(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("error");
                }
            }
        }).start();
    }

    public static void parseJSONWithGson(String jsonData) {

        NewDatabase newDatabase = new NewDatabase(MyApplication.getContextObject(), "New.db", null, 1);
        SQLiteDatabase writableDatabase = newDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        JsonElement element = new JsonParser().parse(jsonData);
        JsonObject obj = element.getAsJsonObject();
        JsonArray data = obj.get("data").getAsJsonArray();
        //writableDatabase.delete("New", null, null);//开始先把全部数据删了
        for (int i = 0; i < 99; i++) {
            String data00 = data.get(i).toString();
            String name = "\"name\":\"([^\"]+)\"";//歌手名字
            Matcher find_name = Pattern.compile(name).matcher(data00);//匹配歌手名字
            String song = "\"name\":\"([^\"]+)\",\"id\":([^\"]+),\"type\":\"([^\"]+)\"";//歌名
            Matcher find_song_name = Pattern.compile(song).matcher(data00);
            if (find_name.find() && find_song_name.find()) {
                String song_name = find_song_name.group(1);
                String singer_name = find_name.group(1);
                System.out.println(singer_name);
                values.put("singerName", singer_name);
                values.put("songName", song_name);
                writableDatabase.replace("New", null, values);

                values.clear();
            }
        }

    }
}
