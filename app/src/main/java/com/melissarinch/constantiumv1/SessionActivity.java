package com.melissarinch.constantiumv1;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.melissarinch.constantiumv1.data.SessionData;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.microsoft.windowsazure.mobileservices.http.HttpConstants.GetMethod;
import static com.microsoft.windowsazure.mobileservices.http.HttpConstants.PostMethod;

public class SessionActivity extends AppCompatActivity {
    Button sessionButton;
    String sensorData = "[2 3 4 5], [2 4 5 6]";

    Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    public void sendString(final String _sensorData) throws MalformedURLException {

        SessionData sessionData = new SessionData();
        sessionData.setmId("1");
        sessionData.setmCreatedAt(new Date());
        sessionData.setmDuration(4.55);
        sessionData.setmExerciseId(1);
        sessionData.setmUserId(1);
        sessionData.setmWeight(4);
        sessionData.setmSensorData(_sensorData);

        Log.d("Session Activity JSON", gson.toJson(sessionData));
        JsonElement sessionJSON = new JsonParser().parse(gson.toJson(sessionData));

        // Create the MobileService Client object and set your backend URL        
        String yourURL = "http://constantiam.azurewebsites.net/";
        MobileServiceClient mClient = new MobileServiceClient(yourURL, this);     // Your query pointing to /api/values/{String}
        ListenableFuture<JsonElement> query = mClient.invokeApi("sessionData", sessionJSON, PostMethod, null);


        // Callback method                                                                        s
        Futures.addCallback(query, new FutureCallback<JsonElement>() {
            @Override
            public void onSuccess(JsonElement jsonElement) {

                // You are expecting a String you can just output the result.
                final String result = jsonElement.toString();

                //since you are on async task you need to show the result on the UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
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