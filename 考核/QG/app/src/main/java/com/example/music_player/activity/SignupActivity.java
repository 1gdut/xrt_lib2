package com.example.music_player.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.music_player.R;
import com.example.music_player.database.UserDatabase;

public class SignupActivity extends AppCompatActivity {
    UserDatabase userDatabase = new UserDatabase(this, "User.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        InitView();
    }

    public void InitView() {
        EditText signup_phoneNumber = findViewById(R.id.signup_phoneNumber);
        EditText sign_password = findViewById(R.id.sign_password);
        Button finish_signup = findViewById(R.id.finish_signup);
        finish_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = userDatabase.getWritableDatabase();
                ContentValues values = new ContentValues();
                if (signup_phoneNumber.length() == 0 || sign_password.length() == 0) {//判空
                    Toast.makeText(SignupActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                } else if (signup_phoneNumber.length() != 11) {//判断手机号码
                    Toast.makeText(SignupActivity.this, "手机号码为11位", Toast.LENGTH_SHORT).show();
                } else if (sign_password.length() <6) {//判断密码
                    Toast.makeText(SignupActivity.this, "密码要大于六位", Toast.LENGTH_SHORT).show();
                }else {//正常情况
                    values.put("phoneNumber", signup_phoneNumber.getText().toString());
                    values.put("password", sign_password.getText().toString());
                    db.insert("User",null,values);
                    Toast.makeText(SignupActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}