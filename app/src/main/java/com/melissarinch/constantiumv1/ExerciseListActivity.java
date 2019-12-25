package com.melissarinch.constantiumv1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.melissarinch.constantiumv1.data.Exercise;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.val;

public class ExerciseListActivity extends AppCompatActivity {


    private MobileServiceClient mClient;
    private MobileServiceTable<Exercise> mExerciseTable;

    ListView exerciseList;
    View overlay;
    String[] exerciseNames = {"Squat", "Lunge", "Glute Bridge", "Deadlift"};
    String[] exerciseDescriptions = {"lorem", "ipsum", "lorem", "ipsum"};
    private Integer[] exerciseImageID = {R.drawable.squat, R.drawable.lunge, R.drawable.glute_bridge, R.drawable.deadlift};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        overlay = findViewById(R.id.overlay);
        overlay.setVisibility(View.INVISIBLE);

        exerciseList = findViewById(R.id.exerciseList);

        try {
            // Create the client instance, using the provided mobile app URL.
            mClient = new MobileServiceClient(
                    "https://todolistapp123.azurewebsites.net",
                    this);

            // Extend timeout from default of 10s to 20s
            mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .build();

                    return client;
                }
            });

            mExerciseTable = mClient.getTable(Exercise.class);
            initLocalStore().get();

            // Create an adapter to bind the items with the view

            CustomListView customListView = new CustomListView(this,exerciseNames, exerciseDescriptions, exerciseImageID);
            exerciseList.setAdapter(customListView);
            exerciseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    overlay.setVisibility(View.VISIBLE);
                }
            });
            // Load the items from the mobile app backend.
            //refreshItemsFromTable();

        } catch (MalformedURLException e) {
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        } catch (Exception e){
            createAndShowDialog(e, "Error");
        }
    }

    private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceLocalStoreException, ExecutionException, InterruptedException {

        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    MobileServiceSyncContext syncContext = mClient.getSyncContext();

                    if (syncContext.isInitialized())
                        return null;

                    SQLiteLocalStore localStore = new SQLiteLocalStore(mClient.getContext(), "OfflineStore", null, 1);

                    Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();
                    tableDefinition.put("id", ColumnDataType.String);
                    tableDefinition.put("exercise_name", ColumnDataType.String);
                    tableDefinition.put("exercise_description", ColumnDataType.String);
                    tableDefinition.put("image_name", ColumnDataType.String);

                    localStore.defineTable("Exercise", tableDefinition);

                    SimpleSyncHandler handler = new SimpleSyncHandler();

                    syncContext.initialize(localStore, handler).get();

                } catch (final Exception e) {
                    createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        return runAsyncTask(task);
    }

    private void createAndShowDialogFromTask(final Exception exception, String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createAndShowDialog(exception, "Error");
            }
        });
    }

    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }

//    @SuppressLint("StaticFieldLeak")
//    private void refreshItemsFromTable() {
//
//        // get all items from the table and add to adapter
//
//        AsyncTask<Void, Void, Void> task;
//        task = new AsyncTask<Void, Void, Void>(){
//            @Override
//            protected Void doInBackground(Void... params) {
//
//                try {
//                    // get the list of exercises
//                    final List<Exercise> results = refreshItemsFromMobileServiceTable();
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            CustomListView.clear();
//
//                            for (Exercise item : results) {
//                                CustomListView.add(item);
//                            }
//                        }
//                    });
//                } catch (final Exception e){
//                    createAndShowDialogFromTask(e, "Error");
//                }
//
//                return null;
//            }
//        };
//
//        runAsyncTask(task);
//    }

    private List<Exercise> refreshItemsFromMobileServiceTable() throws ExecutionException, InterruptedException {
        return mExerciseTable.execute("select * from exercise").get();
    }

}
