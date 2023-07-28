package com.example.intelligentinsertion.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.intelligentinsertion.R;

import java.util.Objects;

public class ChangePasswordActivity extends AppCompatActivity {
    Button finish_change_password, change_password_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Objects.requireNonNull(getSupportActionBar()).hide();
        change_password_cancel = findViewById(R.id.change_password_cancel);
        finish_change_password = findViewById(R.id.finish_change_password);
        change_password_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        finish_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}