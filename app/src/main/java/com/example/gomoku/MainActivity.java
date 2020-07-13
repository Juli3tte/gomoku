package com.example.gomoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    Graphics gameFrame;
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup view_group = (ViewGroup) findViewById(R.id.main_region);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        final View title = (View)findViewById(R.id.title);

        final Graphics ge = new Graphics(this.getBaseContext());
        ge.setNumColumns(12);
        ge.setNumRows(12);
        ge.setBackgroundColor(Color.BLUE);
        title.setBackgroundColor(Color.YELLOW);

        view_group.addView(ge);

        title.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // Hide your View after 3 seconds
                title.setVisibility(View.GONE);
            }
        }, 3000);
    }
}

