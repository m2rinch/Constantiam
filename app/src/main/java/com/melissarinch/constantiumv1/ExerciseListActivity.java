package com.melissarinch.constantiumv1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.melissarinch.constantiumv1.data.Exercise;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static com.microsoft.windowsazure.mobileservices.http.HttpConstants.GetMethod;

public class ExerciseListActivity extends Activity {

    ListView exerciseList;

    private ExerciseItemAdapter mExerciseItemAdapter;
    private MobileServiceClient mClient;
    private String TAG = ExerciseListActivity.class.getName();
    private String URL = "http://constantiam.azurewebsites.net/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        exerciseList = findViewById(R.id.exerciseList);
        // populate exercise item adapter with the list of exercises and apply to view
        mExerciseItemAdapter = new ExerciseItemAdapter(this,R.layout.row_exercise_list);
        exerciseList.setAdapter(mExerciseItemAdapter);
        exerciseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ExerciseFeedbackActivity.class);
                Exercise exercise = (Exercise) exerciseList.getItemAtPosition(i);
                intent.putExtra("Exercise", exercise);
                startActivity(intent);
            }
        });
            try {
                // update mExerciseJSON from by getting exercise list
                getStringFromAzure();
            }
            catch (Exception e) {

                Log.d(TAG, "Exception: " + e.getMessage());
            }
    }

    private void getStringFromAzure() throws MalformedURLException {
        mClient = new MobileServiceClient(URL, this);

        ListenableFuture<JsonElement> query = mClient.invokeApi("Exercises", null, GetMethod, null);

        Futures.addCallback(query, new FutureCallback<JsonElement>() {
            @Override
            public void onSuccess(JsonElement jsonElement) {
                final JsonElement mExerciseJSON = jsonElement;
                    // convert json element to list of exercises
                Type listType = new TypeToken<List<Exercise>>(){}.getType();
                final ArrayList<Exercise> exercises = (new Gson().fromJson(mExerciseJSON, listType));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mExerciseItemAdapter.clear();

                        for (Exercise item : exercises) {
                            mExerciseItemAdapter.add(item);
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








//    private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceLocalStoreException, ExecutionException, InterruptedException {
//
//        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//
//                    MobileServiceSyncContext syncContext = mClient.getSyncContext();
//
//                    if (syncContext.isInitialized())
//                        return null;
//
//                    SQLiteLocalStore localStore = new SQLiteLocalStore(mClient.getContext(), "OfflineStore", null, 1);
//
//                    Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();
//                    tableDefinition.put("id", ColumnDataType.String);
//                    tableDefinition.put("exercise_name", ColumnDataType.String);
//                    tableDefinition.put("exercise_description", ColumnDataType.String);
//                    tableDefinition.put("image_name", ColumnDataType.String);
//
//                    localStore.defineTable("Exercise", tableDefinition);
//
//                    SimpleSyncHandler handler = new SimpleSyncHandler();
//
//                    syncContext.initialize(localStore, handler).get();
//
//                } catch (final Exception e) {
//                    createAndShowDialogFromTask(e, "Error");
//                }
//
//                return null;
//            }
//        };
//
//        return runAsyncTask(task);
//    }
//
//    private void createAndShowDialogFromTask(final Exception exception, String title) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                createAndShowDialog(exception, "Error");
//            }
//        });
//    }
//
//    private void createAndShowDialog(Exception exception, String title) {
//        Throwable ex = exception;
//        if(exception.getCause() != null){
//            ex = exception.getCause();
//        }
//        createAndShowDialog(ex.getMessage(), title);
//    }
//
//    private void createAndShowDialog(final String message, final String title) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setMessage(message);
//        builder.setTitle(title);
//        builder.create().show();
//    }
//
//    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        } else {
//            return task.execute();
//        }
//    }
//
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
//                    Log.d("ExerciseListActivity", "inside");
//                    // get the list of exercises
//                    final List<Exercise> results = refreshItemsFromMobileServiceTable();
//                    Log.d("ExerciseListActivity", "GOT HERE");
//                    Log.d("ExerciseListActivity", "RESULTS" + results.get(0).getText());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mAdapter.clear();
//
//                            for (Exercise item : results) {
//                                mAdapter.add(item);
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
//
//    private List<Exercise> refreshItemsFromMobileServiceTable() throws ExecutionException, InterruptedException {
//        return mExerciseTable.orderBy("ID", QueryOrder.Ascending)
//                .execute()
//                .get();
//
//    }