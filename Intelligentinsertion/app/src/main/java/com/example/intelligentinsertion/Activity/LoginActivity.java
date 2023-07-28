package com.example.intelligentinsertion.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intelligentinsertion.Bean.User;
import com.example.intelligentinsertion.LiveData.AllLiveData;
import com.example.intelligentinsertion.R;
import com.example.intelligentinsertion.Web.web;
import com.google.gson.Gson;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    TextView forgetPassword;
    Button login, register;
    EditText userName, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //绑定
        forgetPassword = findViewById(R.id.forgetPassword);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        userName = findViewById(R.id.login_userName);
        password = findViewById(R.id.login_password);
        TextView skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainViewActivity.class);
                startActivity(intent);
            }
        });

        Objects.requireNonNull(getSupportActionBar()).hide();
        //忘记密码
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //登录
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (userName.getText().toString().trim().length() == 0 || password.getText().toString().trim().length() == 0) {//判空
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "手机号或密码不能为空", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                Gson gson = new Gson();
                User user = new User(userName.getText().toString().trim(), password.getText().toString().trim());
                String json = gson.toJson(user);
                Log.d(TAG, "onClick:用户名和密码 " + json);
                web.returnLoginCode(json, new web.OnLoginListener() {
                    @Override
                    public void getLoginResponse(int code, String data) {
                        if (code == 1) {//登录成功
                            Intent intent = new Intent(LoginActivity.this, MainViewActivity.class);
                            startActivity(intent);
                            finish();
                            AllLiveData.userName.postValue(userName.getText().toString().trim());
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        Intent intent = new Intent(LoginActivity.this, ChangePwdActivity.class);
                        intent.putExtra("loginData", data);
                        Log.d(TAG, "getLoginResponse:登录header " + data);
                        AllLiveData.loginHeader.postValue(data);
                    }
                });

            }
        });

        //注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}