package com.melissarinch.constantiumv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.melissarinch.constantiumv1.R;
import com.melissarinch.constantiumv1.data.Exercise;
import com.melissarinch.constantiumv1.data.KeyCharDescription;
import com.melissarinch.constantiumv1.data.Session;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static com.microsoft.windowsazure.mobileservices.http.HttpConstants.GetMethod;

public class SummaryActivity extends AppCompatActivity {

    ListView keycharList;
    private KeyCharAdapter mKeyCharAdapter;
    private MobileServiceClient mClient;
    private String TAG = SummaryActivity.class.getName();
    private String URL = "http://constantiam.azurewebsites.net/";
    Session session;
    ImageButton backButton;
    Exercise exercise;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        keycharList = findViewById(R.id.keycharList);
        mKeyCharAdapter = new KeyCharAdapter(this,R.layout.row_keychar_list);
        keycharList.setAdapter(mKeyCharAdapter);
        backButton = findViewById(R.id.backButton);
        if (getIntent().hasExtra("Session")) {
            session = (Session) getIntent().getSerializableExtra("Session");
        }
        try {
            // update mExerciseJSON from by getting exercise list
            getStringFromAzure();
        }
        catch (Exception e) {

            Log.d(TAG, "Exception: " + e.getMessage());
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().hasExtra("Exercise")) {
                    exercise = (Exercise) getIntent().getSerializableExtra("Exercise");
                    Intent intent = new Intent(getApplicationContext(), ExerciseFeedbackActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Exercise", exercise);
                    bundle.putSerializable("Session", session);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
    }


    private void getStringFromAzure() throws MalformedURLException {
        mClient = new MobileServiceClient(URL, this);
       // int sessionid = session.getmId();
        int sessionid = 1;

        ListenableFuture<JsonElement> query = mClient.invokeApi("KeyCharDescription/" + sessionid , null, GetMethod, null);

        Futures.addCallback(query, new FutureCallback<JsonElement>() {
            @Override
            public void onSuccess(JsonElement jsonElement) {
                final JsonElement mKeyCharJSON = jsonElement;
                // convert json element to list of exercises
                Type listType = new TypeToken<List<KeyCharDescription>>(){}.getType();
                final ArrayList<KeyCharDescription> keyCharDescriptions = (new Gson().fromJson(mKeyCharJSON, listType));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mKeyCharAdapter.clear();

                        for (KeyCharDescription item : keyCharDescriptions) {
                            mKeyCharAdapter.add(item);
                        }
                    }
                });


            }
            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
