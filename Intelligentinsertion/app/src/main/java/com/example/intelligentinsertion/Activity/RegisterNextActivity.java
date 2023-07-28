package com.example.intelligentinsertion.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.intelligentinsertion.Bean.RegisterBean;
import com.example.intelligentinsertion.Bean.User;
import com.example.intelligentinsertion.LiveData.AllLiveData;
import com.example.intelligentinsertion.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterNextActivity extends AppCompatActivity {
    private static final String TAG = "RegisterNextActivity";
    Spinner spinner;
    Button forget_next, forget_cancel;
    EditText forget_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        Objects.requireNonNull(getSupportActionBar()).hide();
        initSpinnerForDropdown();
        //绑定
        forget_next = findViewById(R.id.forget_next);
        forget_cancel = findViewById(R.id.forget_cancel);
        spinner = findViewById(R.id.spinner);
        forget_answer = findViewById(R.id.forget_answer);


        forget_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllLiveData.getQuestion.observe(RegisterNextActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        User user = new User();
                        user.setQuestion(s);//得到问题
                        user.setAnswer(forget_answer.getText().toString().trim());//答案
                        Gson gson = new Gson();
                        String json = gson.toJson(user);//变json
                        Log.d(TAG, "onChanged:问题和答案 " + json);
                        AllLiveData.registerHeader.observe(RegisterNextActivity.this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                String data = s;
                                OkHttpClient client = new OkHttpClient();
                                //格式
                                MediaType JSON = MediaType.Companion.parse("application/json;charset=utf-8");
                                RequestBody requestBody = RequestBody.Companion.create(json, JSON);
                                Request request = new Request.Builder()
                                        .url("http://39.98.41.126:31133/users/q")
                                        //加header
                                        .addHeader("Authorization", data)
                                        .post(requestBody)
                                        .build();
                                Log.d(TAG, "onChanged: header " + data);
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
                                        //得到返回的code
                                        RegisterBean json1 = gson.fromJson(jsonData, RegisterBean.class);
                                        int code = json1.getCode();
                                        AllLiveData.getQaCode.postValue(code);
                                    }
                                });
                            }
                        });

                    }
                });

                //判断问题答案成功
                AllLiveData.getQaCode.observe(RegisterNextActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer == 1) {
                            Intent intent = new Intent(RegisterNextActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterNextActivity.this, "错误", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

            }
        });
        forget_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterNextActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // 初始化下拉模式的列表框
    private void initSpinnerForDropdown() {
        // 声明一个下拉列表的数组适配器
        ArrayAdapter<String> starAdapter = new ArrayAdapter<>(this,
                R.layout.item_select, starArray);
        // 从布局文件中获取名叫sp_dropdown的下拉框
        Spinner sp_dropdown = findViewById(R.id.spinner);
        // 设置下拉框的标题。对话框模式才显示标题，下拉模式不显示标题
        sp_dropdown.setPrompt("请选择你的备用问题");
        sp_dropdown.setAdapter(starAdapter); // 设置下拉框的数组适配器
        sp_dropdown.setSelection(0); // 设置下拉框默认显示第一项
        // 给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        sp_dropdown.setOnItemSelectedListener(new MySelectedListener());
    }

    // 定义下拉列表需要显示的文本数组
    private String[] starArray = {"你最喜欢的动物是什么？",
            "你最喜欢的水果是什么？",
            "你最喜欢的植物是什么？"};

    // 定义一个选择监听器，它实现了接口OnItemSelectedListener
    class MySelectedListener implements AdapterView.OnItemSelectedListener {
        // 选择事件的处理方法，其中arg2代表选择项的序号
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            //Toast.makeText(ForgetActivity.this, starArray[arg2], Toast.LENGTH_SHORT).show();
            AllLiveData.getQuestion.postValue(starArray[arg2]);
        }

        // 未选择时的处理方法，通常无需关注
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
}