package com.melissarinch.constantiumv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.melissarinch.constantiumv1.R;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class TimerScreenActivity extends AppCompatActivity {
    /*
        code from http://www.androidtutorialshub.com/android-count-down-timer-tutorial/
     */

    public long timeCountInMilliSeconds = 1*60000;
    public enum TimerStatus {
        STARTED,
        STOPPED
    }
    public TimerStatus timerStatus = TimerStatus.STOPPED;
    public ProgressBar progressBarCircle;
    public TextView textViewTime;
    public Button beginCalibration;
    public CountDownTimer countDownTimer;

    public void startStop() {
            setTimerValues();
            setProgressBarValues();
            timerStatus = TimerStatus.STARTED;
            // call to start the count down timer
            beginCalibration.setAlpha(.5f);
            beginCalibration.setClickable(false);
    }

    public void setTimerValues() {
        int time = 1;
        timeCountInMilliSeconds = time * 10 * 1000;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_screen);
        initViews();
        beginCalibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();
            }
        });
    }

    public void initViews() {
        progressBarCircle = findViewById(R.id.progressBarCircle);
        textViewTime = findViewById(R.id.textViewTime);
        beginCalibration = findViewById(R.id.beginButton);
    }

    /**
     * method to set circular progress bar values
     */
    public void setProgressBarValues() {

        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
    }

    /**
     * method to convert millisecond to time format
     *
     * @param milliSeconds
     * @return HH:mm:ss time formatted string
     */
    public String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;
    }

}
