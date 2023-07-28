package com.example.intelligentinsertion.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.intelligentinsertion.Bean.User;
import com.example.intelligentinsertion.LiveData.AllLiveData;
import com.example.intelligentinsertion.R;
import com.example.intelligentinsertion.Web.web;
import com.google.gson.Gson;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    Button register_next, register_cancel;
    EditText register_userName, register_password, register_password_again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //绑定
        register_next = findViewById(R.id.register_next);
        register_cancel = findViewById(R.id.register_cancel);
        register_userName = findViewById(R.id.register_userName);
        register_password = findViewById(R.id.register_password);
        register_password_again = findViewById(R.id.register_password_again);

        //注册下一步
        register_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = register_userName.getText().toString().trim();
                String password = register_password.getText().toString().trim();
                String password_again = register_password_again.getText().toString().trim();
                User user = new User(userName, password);
                Gson gson = new Gson();
                //转json
                String json = gson.toJson(user);
                if (userName.length() == 0 || password.length() == 0 || password_again.length() == 0) {//判空
                    Toast.makeText(RegisterActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    web.returnCode(json, new web.OnRegisterListener() {
                        @Override
                        public void getRegisterResponse(int code, String data) {
                            AllLiveData.registerHeader.postValue(data);
                            if (code == 1) {//返回成功
                                if (password.equals(password_again)) {//密码相同
                                    Intent intent = new Intent(RegisterActivity.this, RegisterNextActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
//                                if (!password.equals(password_again)) {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Toast.makeText(RegisterActivity.this, "两次密码不相同,默认为第一次输入的密码", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
                            }
                            if (code == 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "该手机号已被注册", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        register_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}