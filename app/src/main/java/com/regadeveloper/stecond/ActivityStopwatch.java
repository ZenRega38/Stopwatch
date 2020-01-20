package com.regadeveloper.stecond;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

public class ActivityStopwatch extends AppCompatActivity {
    Chronometer chronometer;
    ImageButton btnStart, btnStop, btnPause;

    Handler handler;
    long tMiliSec, tStart, tBuff, tUpdate = 0L;
    int min, sec, milisec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        chronometer = findViewById(R.id.time_display);
        btnStart = findViewById(R.id.start);
        btnStop = findViewById(R.id.stop);
        btnPause = findViewById(R.id.pause);

        handler = new Handler();

        btnPause.setVisibility(View.GONE);
        btnStop.setVisibility(View.GONE);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tStart = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                chronometer.start();
                btnStop.setVisibility(View.GONE);
                btnStart.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    tMiliSec = 0L;
                    tStart = 0L;
                    tBuff = 0L;
                    tUpdate = 0L;
                    sec = 0;
                    min = 0;
                    milisec = 0;
                    chronometer.setText("00:00:00");
                    btnStop.setVisibility(View.GONE);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tBuff += tMiliSec;
                handler.removeCallbacks(runnable);
                chronometer.stop();
                btnStop.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.GONE);
                btnStart.setVisibility(View.VISIBLE);
            }
        });
    }



    public Runnable runnable = new Runnable() {
        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        @Override
        public void run() {
            tMiliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMiliSec;
            sec = (int) (tUpdate / 1000);
            min = sec / 60;
            milisec = (int) (tUpdate % 100);
            chronometer.setText(String.format("%02d", min) + ":"
                    + String.format("%02d", sec) + ":" + String.format("%02d", milisec));
            handler.postDelayed(this, 60);

            if (sec == 60){
                return  ;
            }
        }
    };
}
