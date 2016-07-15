package com.ylq.classtable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ylq.library.activity.ClasstableMainActivity;
import com.ylq.library.util.ClickGuard;

public class MainActivity extends AppCompatActivity {

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        button = (Button) findViewById(R.id.button);
        ClickGuard.guard(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"CLICK",Toast.LENGTH_LONG).show();
            }
        },button);
        Intent intent = new Intent();
        intent.setClass(this, ClasstableMainActivity.class);
        startActivity(intent);
    }
}
