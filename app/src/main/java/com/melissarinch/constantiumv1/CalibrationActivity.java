package com.melissarinch.constantiumv1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melissarinch.constantiumv1.data.Calibration;
import com.melissarinch.constantiumv1.data.CalibrationData;
import com.melissarinch.constantiumv1.data.Session;
import com.melissarinch.constantiumv1.data.SessionData;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import java.net.MalformedURLException;
import java.util.Date;

import static com.microsoft.windowsazure.mobileservices.http.HttpConstants.PostMethod;

public class CalibrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);
    }

    private void startCalibration(View view){
        // start bluetooth + countdown timer + change play to pause sign
    }

    private void onTimerComplete(){
        // reset time and change pause to play sign
        // send calibration data to for processing
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
        ListenableFuture<JsonElement> query = mClient.invokeApi("calibrationData", calibrationJSON, PostMethod, null);


        // Callback method                                                                        s
        Futures.addCallback(query, new FutureCallback<JsonElement>() {
            @Override
            public void onSuccess(JsonElement jsonElement) {

                // You are expecting a String you can just output the result.
                JsonObject result = jsonElement.getAsJsonObject();
                result.remove("constantiam_user");
                result.remove("exercise");
                result.remove("key_char");

                final Calibration calibration = new Gson().fromJson(result.toString(), Calibration.class);
                //since you are on async task you need to show the result on the UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getApplicationContext(), calibration.toString(), Toast.LENGTH_LONG).show();
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
