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
import com.melissarinch.constantiumv1.data.Session;
import com.melissarinch.constantiumv1.data.SessionData;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;
import java.net.MalformedURLException;
import java.util.Date;
import static com.microsoft.windowsazure.mobileservices.http.HttpConstants.PostMethod;

public class SessionActivity extends AppCompatActivity {
    private Button sessionButton;
    private Chronometer chronometer; // taken from https://www.youtube.com/watch?v=RLnb4vVkftc
    private boolean running;
    private long pauseOffset;
    private NumberPicker weightPicker;
    private ImageView playPause;
    private String sensorData = "93,10,36,18,92,40,21,47,71,4,;,41,77,34,13,1,31,50,90,56,39,;,82,22,75,3,88,69,19,94,63,51,;,16,8,23,100,9,95,11,70,24,84,;,59,57,58,76,26,28,20,42,66,6,;,98,91,97,96,74,44,2,37,81,33,;,55,45,48,14,46,12,7,79,17,64,;,85,89,65,38,32,73,49,83,43,72,;,52,15,53,27,80,25,29,67,54,60,;,5,68,86,61,99,78,30,87,62,35,;,93,10,36,18,92,40,21,47,71,4,;,41,77,34,13,1,31,50,90,56,39,;,82,22,75,3,88,69,19,94,63,51,;,16,8,23,100,9,95,11,70,24,84,;,59,57,58,76,26,28,20,42,66,6,;,98,91,97,96,74,44,2,37,81,33,;,55,45,48,14,46,12,7,79,17,64,;,85,89,65,38,32,73,49,83,43,72,;,52,15,53,27,80,25,29,67,54,60,;,5,68,86,61,99,78,30,87,62,35,;,93,10,36,18,92,40,21,47,71,4,;,41,77,34,13,1,31,50,90,56,39,;,82,22,75,3,88,69,19,94,63,51,;,16,8,23,100,9,95,11,70,24,84,;,59,57,58,76,26,28,20,42,66,6,;,98,91,97,96,74,44,2,37,81,33,;,55,45,48,14,46,12,7,79,17,64,;,85,89,65,38,32,73,49,83,43,72,;,52,15,53,27,80,25,29,67,54,60,;,5,68,86,61,99,78,30,87,62,35,;,93,10,36,18,92,40,21,47,71,4,;,41,77,34,13,1,31,50,90,56,39,;,82,22,75,3,88,69,19,94,63,51,;,16,8,23,100,9,95,11,70,24,84,;,59,57,58,76,26,28,20,42,66,6,;,98,91,97,96,74,44,2,37,81,33,;,55,45,48,14,46,12,7,79,17,64,;,85,89,65,38,32,73,49,83,43,72,;,52,15,53,27,80,25,29,67,54,60,;,5,68,86,61,99,78,30,87,62,35,;,93,10,36,18,92,40,21,47,71,4,;,41,77,34,13,1,31,50,90,56,39,;,82,22,75,3,88,69,19,94,63,51,;,16,8,23,100,9,95,11,70,24,84,;,59,57,58,76,26,28,20,42,66,6,;,98,91,97,96,74,44,2,37,81,33,;,55,45,48,14,46,12,7,79,17,64,;,85,89,65,38,32,73,49,83,43,72,;,52,15,53,27,80,25,29,67,54,60,;,5,68,86,61,99,78,30,87,62,35,;,93,10,36,18,92,40,21,47,71,4,;,41,77,34,13,1,31,50,90,56,39,;,82,22,75,3,88,69,19,94,63,51,;,16,8,23,100,9,95,11,70,24,84,;,59,57,58,76,26,28,20,42,66,6,;,98,91,97,96,74,44,2,37,81,33,;,55,45,48,14,46,12,7,79,17,64,;,85,89,65,38,32,73,49,83,43,72,;,52,15,53,27,80,25,29,67,54,60,;,5,68,86,61,99,78,30,87,62,35,;,93,10,36,18,92,40,21,47,71,4,;,41,77,34,13,1,31,50,90,56,39,;,82,22,75,3,88,69,19,94,63,51,;,16,8,23,100,9,95,11,70,24,84,;,59,57,58,76,26,28,20,42,66,6,;,98,91,97,96,74,44,2,37,81,33,;,55,45,48,14,46,12,7,79,17,64,;,85,89,65,38,32,73,49,83,43,72,;,52,15,53,27,80,25,29,67,54,60,;,5,68,86,61,99,78,30,87,62,35,;,93,10,36,18,92,40,21,47,71,4,;,41,77,34,13,1,31,50,90,56,39,;,82,22,75,3,88,69,19,94,63,51,;,16,8,23,100,9,95,11,70,24,84,;,59,57,58,76,26,28,20,42,66,6,;,98,91,97,96,74,44,2,37,81,33,;,55,45,48,14,46,12,7,79,17,64,;,85,89,65,38,32,73,49,83,43,72,;,52,15,53,27,80,25,29,67,54,60,;,5,68,86,61,99,78,30,87,62,35,;,93,10,36,18,92,40,21,47,71,4,;,41,77,34,13,1,31,50,90,56,39,;,82,22,75,3,88,69,19,94,63,51,;,16,8,23,100,9,95,11,70,24,84,;,59,57,58,76,26,28,20,42,66,6,;,98,91,97,96,74,44,2,37,81,33,;,55,45,48,14,46,12,7,79,17,64,;,85,89,65,38,32,73,49,83,43,72,;,52,15,53,27,80,25,29,67,54,60,;,5,68,86,61,99,78,30,87,62,35,;,93,10,36,18,92,40,21,47,71,4,;,41,77,34,13,1,31,50,90,56,39,;,82,22,75,3,88,69,19,94,63,51,;,16,8,23,100,9,95,11,70,24,84,;,59,57,58,76,26,28,20,42,66,6,;,98,91,97,96,74,44,2,37,81,33,;,55,45,48,14,46,12,7,79,17,64,;,85,89,65,38,32,73,49,83,43,72,;,52,15,53,27,80,25,29,67,54,60,;,5,68,86,61,99,78,30,87,62,35,;,93,10,36,18,92,40,21,47,71,4,;,41,77,34,13,1,31,50,90,56,39,;,82,22,75,3,88,69,19,94,63,51,;,16,8,23,100,9,95,11,70,24,84,;,59,57,58,76,26,28,20,42,66,6,;,98,91,97,96,74,44,2,37,81,33,;,55,45,48,14,46,12,7,79,17,64,;,85,89,65,38,32,73,49,83,43,72,;,52,15,53,27,80,25,29,67,54,60,;,5,68,86,61,99,78,30,87,62,35,;,93,10,36,18,92,40,21,47,71,4,;,41,77,34,13,1,31,50,90,56,39,;,82,22,75,3,88,69,19,94,63,51,;,16,8,23,100,9,95,11,70,24,84,;,59,57,58,76,26,28,20,42,66,6,;,98,91,97,96,74,44,2,37,81,33,;,55,45,48,14,46,12,7,79,17,64,;,85,89,65,38,32,73,49,83,43,72,;,52,15,53,27,80,25,29,67,54,60,;,5,68,86,61,99,78,30,87,62,35,;";
    //private Exercise exercise = (Exercise) getIntent().getSerializableExtra("Exercise");
    private Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        playPause = findViewById(R.id.playPauseSession);
        weightPicker = findViewById(R.id.weightPicker);
        playPause.setImageResource(R.drawable.play);
        chronometer = findViewById(R.id.chronometer);
        sessionButton = findViewById(R.id.session_button);
        sessionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    sendString(sensorData);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        weightPicker.setMinValue(0);
        weightPicker.setMaxValue(50);
    }

    // user logs in
    // sees the play button -> should not see pause and finish and restart session
    // clicks play -> set timer to running
    // change play button to pause, show finish button
    // clicks (now) pause button -> set timer to !running,
    // clicks finish button, -> set timer to !running, send string
    public void controlStopwatch(View v){
        if(!running){
            playPause.setImageResource(R.drawable.stop);
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        } else {
            playPause.setImageResource(R.drawable.play);
            pauseStopwatch();
        }
    }

    public void pauseStopwatch(){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
    }

    public void restartSession(View view){
        Intent intent = new Intent(getApplicationContext(), SessionActivity.class);
        startActivity(intent);
    }
    public void sendString(final String _sensorData) throws MalformedURLException {

        SessionData sessionData = new SessionData();
        sessionData.setmId("1");
        sessionData.setmCreatedAt(new Date());
        sessionData.setmDuration((double)pauseOffset);
        sessionData.setmExerciseId(1);
        sessionData.setmUserId(1);
        sessionData.setmWeight(weightPicker.getValue());
        sessionData.setmSensorData(_sensorData);

        // Log.d("Session Activity JSON", gson.toJson(sessionData));
        JsonElement sessionJSON = new JsonParser().parse(gson.toJson(sessionData));

        // Create the MobileService Client object and set your backend URL        
        String yourURL = "http://constantiam.azurewebsites.net/";
        MobileServiceClient mClient = new MobileServiceClient(yourURL, this);     // Your query pointing to /api/values/{String}
        ListenableFuture<JsonElement> query = mClient.invokeApi("sessionData", sessionJSON, PostMethod, null);


        // Callback method
        Futures.addCallback(query, new FutureCallback<JsonElement>() {
            @Override
            public void onSuccess(JsonElement jsonElement) {

                // You are expecting a String you can just output the result.
                JsonObject result = jsonElement.getAsJsonObject();
                result.remove("constantiam_user");
                result.remove("exercise");
                result.remove("key_char");

                final Session session = new Gson().fromJson(result.toString(), Session.class);
                // go to feedback screen if successful and send session object
                Intent intent = new Intent(getApplicationContext(), ExerciseFeedbackActivity.class);
                intent.putExtra("Session", session);
                startActivity(intent);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                       // Toast.makeText(getApplicationContext(), String.valueOf("COP_overall: " + session.getmCOPOverall()), Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("Session Activity", "onFailure: " + throwable.getMessage());
            }
        });
    }
}