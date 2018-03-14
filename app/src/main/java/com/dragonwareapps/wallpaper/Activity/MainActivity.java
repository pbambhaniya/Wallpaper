package com.dragonwareapps.wallpaper.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dragonwareapps.wallpaper.R;

public class MainActivity extends AppCompatActivity {
    TextView txt_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txt_view = (TextView) findViewById(R.id.txt_view);
        String deviceName = android.os.Build.MODEL;
//        Toast.makeText(this, model, Toast.LENGTH_SHORT).show();
        txt_view.setText(deviceName);


    }
}
