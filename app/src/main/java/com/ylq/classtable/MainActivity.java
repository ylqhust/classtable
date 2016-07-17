package com.ylq.classtable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ylq.library.activity.ClasstableMainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Intent intent = new Intent();
        intent.setClass(this, ClasstableMainActivity.class);
        startActivity(intent);
    }
}
