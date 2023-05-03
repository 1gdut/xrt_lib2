package com.example.music_player.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.music_player.MyApplication;
import com.example.music_player.R;
import com.example.music_player.database.UserDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    UserDatabase userDatabase = new UserDatabase(this, "User.db", null, 1);
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.login_picture);
        Button login = findViewById(R.id.login);
        Button signup = findViewById(R.id.signup);
        Button skip = findViewById(R.id.skip);
        skip.setOnClickListener(this);
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(15000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        imageView.startAnimation(rotateAnimation);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                Boolean judgement = match();//判断是否能登录
                if (judgement) {//能
                    Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainViewActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "登陆失败,请检查输入", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.signup://注册
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
                break;
            case R.id.skip:
                Intent intent1 = new Intent(this, MainViewActivity.class);
                startActivity(intent1);
                finish();
        }
    }

    public Boolean match() {
        EditText phoneNumber = findViewById(R.id.phoneNumber);
        EditText password = findViewById(R.id.password);
        SQLiteDatabase db = userDatabase.getWritableDatabase();
        Cursor cursor = db.query("User", null, null, null
                , null, null, null);
        if (cursor.moveToFirst()) {
            if (phoneNumber.length() == 0 || password.length() == 0) {//判空
                Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
            } else {
                do {
                    @SuppressLint("Range")
                    String read_phoneNumber = cursor.getString(cursor.getColumnIndex("phoneNumber"));
                    @SuppressLint("Range")
                    String read_password = cursor.getString(cursor.getColumnIndex("password"));
                    if (Objects.equals(read_phoneNumber, phoneNumber.getText().toString()) &&
                            Objects.equals(read_password, password.getText().toString())) {//都能匹配
                        cursor.close();
                        return true;
                    }
                } while (cursor.moveToNext());
            }
        }
        return false;
    }
}