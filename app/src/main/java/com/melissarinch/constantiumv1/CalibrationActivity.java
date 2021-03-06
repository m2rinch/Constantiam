package com.melissarinch.constantiumv1;

import android.content.Intent;
import android.os.Bundle;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melissarinch.constantiumv1.data.CalibrationData;
import com.melissarinch.constantiumv1.data.Exercise;
import com.melissarinch.constantiumv1.data.Session;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.microsoft.windowsazure.mobileservices.http.HttpConstants.PostMethod;

public class CalibrationActivity extends BluetoothActivity {

    Exercise exercise;
    ImageButton backButton;
    public long timeCountInMilliSeconds = 1*60000;
    public enum TimerStatus {
        STARTED,
        STOPPED
    }

    public TimerStatus timerStatus = TimerStatus.STOPPED;
    public ProgressBar progressBarCircle;
    public TextView textViewTime;
    public Button beginCalibration;
    public Button toSession;
    public CountDownTimer countDownTimer;
    TextView exerciseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);
        initViews();
        exerciseName = findViewById(R.id.exerciseNameCalibration);
        beginCalibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickStartBlue();
                startStop();
            }
        });
        if (getIntent().hasExtra("Exercise")) {
            exercise = (Exercise) getIntent().getSerializableExtra("Exercise");
            exerciseName.setText(exercise.getText());
        }

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ExerciseListActivity.class);
                startActivity(intent);
            }
        });
        initViews();

        toSession = findViewById(R.id.sessionButton);
        toSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SessionActivity.class);
                intent.putExtra("Exercise", exercise);
                startActivity(intent);
            }
        });
        toSession.setVisibility(View.GONE);

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

    public void startStop() {
        setTimerValues();
        setProgressBarValues();
        timerStatus = TimerStatus.STARTED;
        // call to start the count down timer
        beginCalibration.setAlpha(.5f);
        beginCalibration.setClickable(false);
        startCountDownTimer();
    }


    public void setTimerValues() {
        int time = 1;
        timeCountInMilliSeconds = time * 10 * 1000;
    }

    public void startCountDownTimer() {
        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                textViewTime.setText(hmsTimeFormatter(millisUntilFinished));

                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                timeCountInMilliSeconds = 0;
                textViewTime.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                setProgressBarValues();
                try {
                    onClickStopBlue();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    sendCalibrationData(blueData); //TO DO: ensure that blue data is in the right format
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

        }.start();
        countDownTimer.start();
    }

    public void restartCalibration(View view){
        Intent intent = new Intent(getApplicationContext(), CalibrationActivity.class);
        intent.putExtra("Exercise", exercise);
        startActivity(intent);
    }

    public String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;
    }

    public void sendCalibrationData(String _calibrationData) throws MalformedURLException {

//        if(_calibrationData == "")
//        {
//            _calibrationData = "9,7,15,1,36,12,63,19,0,22,;,7,6,14,1,35,11,61,21,0,22,;,7,7,13,1,34,12,65,22,0,19,;,7,8,11,1,34,15,65,19,0,20,;";
//        }
        Log.i("Calibration Activity", _calibrationData);
        CalibrationData calibrationData = new CalibrationData();
        calibrationData.setmCreatedAt(new Date());
        calibrationData.setmExerciseId(Integer.parseInt(exercise.getId()));
        calibrationData.setmUserId(1);
        calibrationData.setmSensorData(_calibrationData);


        JsonElement calibrationJSON = new JsonParser().parse(new Gson().toJson(calibrationData));

        // Create the MobileService Client object and set your backend URL
        String yourURL = "http://constantiam.azurewebsites.net/";
        MobileServiceClient mClient = new MobileServiceClient(yourURL, this);     // Your query pointing to /api/values/{String}

        ListenableFuture<JsonElement> query = mClient.invokeApi("CalibrationData", calibrationJSON, PostMethod, null);

        // Callback method
        Futures.addCallback(query, new FutureCallback<JsonElement>() {
            @Override
            public void onSuccess(final JsonElement jsonElement) {

                // You are expecting a String you can just output the result.
                JsonObject result = jsonElement.getAsJsonObject();
              //  result.remove("constantiam_user");
               // result.remove("exercise");
               // result.remove("key_char");

               // final Calibration calibration = new Gson().fromJson(result.toString(), Calibration.class);
                //since you are on async task you need to show the result on the UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getApplicationContext(), "Calibration Completed!", Toast.LENGTH_LONG).show();
                        beginCalibration.setVisibility(View.GONE);
                        toSession.setVisibility(View.VISIBLE);
                    }
                });

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("Calibration Activity", "onFailure: " + throwable.getMessage());
            }
        });

    }
}
