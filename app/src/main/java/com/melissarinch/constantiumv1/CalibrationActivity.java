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
import android.widget.Toast;

import java.net.MalformedURLException;
import java.util.Date;

import static com.microsoft.windowsazure.mobileservices.http.HttpConstants.PostMethod;

public class CalibrationActivity extends TimerScreenActivity{

    Exercise exercise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);
        initViews();
        beginCalibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();
            }
        });
        if (getIntent().hasExtra("Exercise")) {
            exercise = (Exercise) getIntent().getSerializableExtra("Exercise");
        }

    }

    @Override
    public void startStop() {
        super.startStop();
        startCountDownTimer();
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
                timerStatus = TimerStatus.STOPPED;
                try {
                    sendCalibrationData("[2 4 5 6]");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

        }.start();
        countDownTimer.start();
    }

    public void restartCalibration(View view){
        Intent intent = new Intent(getApplicationContext(), CalibrationActivity.class);
        startActivity(intent);
    }

    public void sendCalibrationData(final String _calibrationData) throws MalformedURLException {

        CalibrationData calibrationData = new CalibrationData();
        calibrationData.setmCreatedAt(new Date());
        calibrationData.setmExerciseId(1);
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

                        Toast.makeText(getApplicationContext(), jsonElement.toString(), Toast.LENGTH_LONG).show();
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
