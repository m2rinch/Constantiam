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

    private long timeCountInMilliSeconds = 1*60000;
    private enum TimerStatus {
        STARTED,
        STOPPED
    }
    private TimerStatus timerStatus = TimerStatus.STOPPED;
    private ProgressBar progressBarCircle;
    private TextView textViewTime;
    private ImageView imageViewStartStop;
    private CountDownTimer countDownTimer;
    private Button readyButton;


    private void startStop() {
        if (timerStatus == TimerStatus.STOPPED) {

            // call to initialize the timer values
            setTimerValues();
            // call to initialize the progress bar values
            setProgressBarValues();
            // changing play icon to stop icon
            imageViewStartStop.setImageResource(R.drawable.stop);
            // changing the timer status to started
            timerStatus = TimerStatus.STARTED;
            // call to start the count down timer
            startCountDownTimer();
            // def

        } else {
            // changing stop icon to start icon
            imageViewStartStop.setImageResource(R.drawable.play);
            // changing the timer status to stopped
            timerStatus = TimerStatus.STOPPED;
            stopCountDownTimer();

        }
    }

    private void setTimerValues() {
        int time = 1;
        // assigning values after converting to milliseconds
//        if(timerStatus == TimerStatus.STOPPED){
//            String tvTime = textViewTime.getText().toString().trim();
//            String[] tvTimeArray = tvTime.split(":");
//
//            time = Integer.parseInt(tvTimeArray[1]);
//        }
        timeCountInMilliSeconds = time * 10 * 1000;
    }

    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                textViewTime.setText(hmsTimeFormatter(millisUntilFinished));

                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {

                textViewTime.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                // call to initialize the progress bar values
                setProgressBarValues();
                // changing stop icon to start icon
                imageViewStartStop.setImageResource(R.drawable.play);
                // changing the timer status to stopped
                timerStatus = TimerStatus.STOPPED;
                startExerciseFeedbackActivity();
            }

        }.start();
        countDownTimer.start();
    }

    private void stopCountDownTimer() {
        countDownTimer.cancel();
    }

    private void startExerciseFeedbackActivity() {
        Intent intent = new Intent(getApplicationContext(), ExerciseFeedbackActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_screen);
        initViews();
        imageViewStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();
            }
        });

        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startExerciseFeedbackActivity();
            }
        });
    }

    private void initViews() {
        progressBarCircle = findViewById(R.id.progressBarCircle);
        textViewTime = findViewById(R.id.textViewTime);
        imageViewStartStop = findViewById(R.id.imageViewStartStop);
        readyButton = findViewById(R.id.ready);
    }

    /**
     * method to set circular progress bar values
     */
    private void setProgressBarValues() {

        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
    }

    /**
     * method to convert millisecond to time format
     *
     * @param milliSeconds
     * @return HH:mm:ss time formatted string
     */
    private String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;
    }

}
