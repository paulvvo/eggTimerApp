package com.example.sleepy.eggtimerapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int buttonFlag = 0;
    CountDownTimer timer;
    SeekBar timerSeekBar;
    TextView timeView;

    public void startStopClick(View view){
        final Button startStopButton  = (Button)findViewById(R.id.startStopButton);
        timerSeekBar  = (SeekBar)findViewById(R.id.timerSeekBar);
        timeView = (TextView) findViewById(R.id.timeView);

        final MediaPlayer medPlay = MediaPlayer.create(getApplicationContext(),R.raw.marbles);

        if(buttonFlag==0){
            startStopButton.setText("STOP");
            timerSeekBar.setEnabled(false);
            timer = new CountDownTimer(timerSeekBar.getProgress()*10000,1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.i("Time Left", Long.toString(millisUntilFinished/1000));
                    long hour = (millisUntilFinished / 3600000) % 24;
                    long min = (millisUntilFinished / 60000) % 60;
                    long sec = (millisUntilFinished / 1000) % 60;
                    timeView.setText(Long.toString(hour) +":"+ Long.toString(min) +":" + Long.toString(sec));
                }

                @Override
                public void onFinish() {
                    medPlay.start();
                    timerSeekBar.setEnabled(true);
                    startStopButton.setText("START");
                    buttonFlag=0;
                }
            };
            timer.start();
            buttonFlag=1;
        }else{
            startStopButton.setText("START");
            timerSeekBar.setEnabled(true);
            timer.cancel();
            buttonFlag=0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar  = (SeekBar)findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(50);

        timeView = (TextView) findViewById(R.id.timeView);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Seek Bar Level", Integer.toString(progress));

                long time = (long)progress * 10000;
                long hour = (time / 3600000) % 24;
                long min = (time / 60000) % 60;
                long sec = (time/ 1000) % 60;
                timeView.setText(Long.toString(hour) +":"+ Long.toString(min) +":" + Long.toString(sec));

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }
}
