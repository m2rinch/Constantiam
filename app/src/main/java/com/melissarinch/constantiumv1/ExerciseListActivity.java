package com.melissarinch.constantiumv1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonElement;
import com.melissarinch.constantiumv1.data.Exercise;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import static com.microsoft.windowsazure.mobileservices.http.HttpConstants.GetMethod;

public class ExerciseListActivity extends Activity {

    private MobileServiceTable<Exercise> mExerciseTable;
    private ExerciseItemAdapter mAdapter;

    ListView exerciseList;
    View overlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        overlay = findViewById(R.id.overlay);
        overlay.setVisibility(View.INVISIBLE);



//        try {
//            // Create the client instance, using the provided mobile app URL.
//            mClient = new MobileServiceClient(
//                    "http://constantiam.azurewebsites.net/",
//                    this);
//
//            // Extend timeout from default of 10s to 20s
//            mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
//                @Override
//                public OkHttpClient createOkHttpClient() {
//                    OkHttpClient client = new OkHttpClient.Builder()
//                            .connectTimeout(20, TimeUnit.SECONDS)
//                            .readTimeout(20, TimeUnit.SECONDS)
//                            .build();
//
//                    return client;
//                }
//            });
//
//            mExerciseTable = mClient.getTable(Exercise.class);
//            initLocalStore().get();
//
//            Log.d("ExerciseListActivity", "HERE");
//            // Create an adapter to bind the items with the view
//
            mAdapter = new ExerciseItemAdapter(this,R.layout.row_exercise_list);
            exerciseList = findViewById(R.id.exerciseList);
            exerciseList.setAdapter(mAdapter);
            exerciseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    overlay.setVisibility(View.VISIBLE);
                }
            });
            try{
                getStringFromAzure();
            }
            catch (Exception e){

                Log.d("Exercise List", "Execption: " + e.getMessage());
            }

//            // Load the items from the mobile app backend.
//            Log.d("ExerciseListActivity", "HERE");
//            refreshItemsFromTable();
//
//        } catch (MalformedURLException e) {
//            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
//        } catch (Exception e){
//            createAndShowDialog(e, "Error");
//        }
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

    @SuppressLint("StaticFieldLeak")
    private void refreshItemsFromTable() {

        // get all items from the table and add to adapter

        AsyncTask<Void, Void, Void> task;
        task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    Log.d("ExerciseListActivity", "inside");
                    // get the list of exercises
                    final List<Exercise> results = refreshItemsFromMobileServiceTable();
                    Log.d("ExerciseListActivity", "GOT HERE");
                    Log.d("ExerciseListActivity", "RESULTS" + results.get(0).getText());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.clear();

                            for (Exercise item : results) {
                                mAdapter.add(item);
                            }
                        }
                    });
                } catch (final Exception e){
                    createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        runAsyncTask(task);
    }

    private List<Exercise> refreshItemsFromMobileServiceTable() throws ExecutionException, InterruptedException {
        return mExerciseTable.orderBy("ID", QueryOrder.Ascending)
                .execute()
                .get();

    }

    private void getStringFromAzure() throws MalformedURLException {
        String yourURL = "http://constantiam.azurewebsites.net/";
        MobileServiceClient mClient = new MobileServiceClient(yourURL, this);

        ListenableFuture<JsonElement> query = mClient.invokeApi("Exercises", null, GetMethod, null);

        Futures.addCallback(query, new FutureCallback<JsonElement>() {
            @Override
            public void onSuccess(JsonElement jsonElement) {
                // you are expecting a string, you can just output the result
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
            public void onFailure(Throwable t) {
                Log.d("Exercise List", "onFailure: " + t.getMessage());
            }
        });

    }

}
