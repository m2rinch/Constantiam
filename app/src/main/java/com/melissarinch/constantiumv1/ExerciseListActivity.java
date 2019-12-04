package com.melissarinch.constantiumv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class ExerciseListActivity extends AppCompatActivity {

    ListView exerciseList;
    String[] exerciseNames = {"Squat", "Lunge", "Glute Bridge", "Deadlift"};
    String[] exerciseDescriptions = {"lorem", "ipsum", "lorem", "ipsum"};
    private Integer[] exerciseImageID = {R.drawable.squat, R.drawable.lunge, R.drawable.glute_bridge, R.drawable.deadlift};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        exerciseList = findViewById(R.id.exerciseList);
        CustomListView customListView = new CustomListView(this,exerciseNames, exerciseDescriptions, exerciseImageID);
        exerciseList.setAdapter(customListView);
        exerciseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), TimerScreenActivity.class);
                startActivity(intent);
            }
        });
    }
}
