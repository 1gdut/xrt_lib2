package com.example.intelligentinsertion.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.intelligentinsertion.R;

public class SetTimeActivity extends AppCompatActivity {
    Button set_time_next,set_time_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        set_time_cancel = findViewById(R.id.set_time_cancel);
        set_time_next = findViewById(R.id.set_time_next);
        set_time_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        set_time_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}