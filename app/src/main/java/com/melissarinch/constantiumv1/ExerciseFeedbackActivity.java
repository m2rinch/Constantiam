package com.melissarinch.constantiumv1;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.melissarinch.constantiumv1.data.WeekLineChart;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class ExerciseFeedbackActivity extends AppCompatActivity {

    LineChart chart;
    int[] xCoords = {0,1,2,3};
    int[] yCoords = {4,1,2,4};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_feedback);


        // Create Feedback Chart
        chart = (LineChart) findViewById(R.id.feedbackChart);

        // add data points to Entry object
        List<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < xCoords.length; i++) {
            entries.add(new Entry(xCoords[i],yCoords[i]));
        }

        // create new data set
        LineDataSet dataSet = new LineDataSet(entries, "Random Data Set 1");
        dataSet.setColor(Color.parseColor("#701112"));
        dataSet.setValueTextColor(Color.parseColor("#FFFFFF"));

        // modify x-axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true); // prevent x-axis duplicates
        xAxis.setValueFormatter(new WeekLineChart());

        // modify y-axis
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        // modify description
        Description description = chart.getDescription();
        description.setText("Random data description");
        description.setEnabled(true);

        // add data set to line chart
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        // refresh
        chart.invalidate();

    }
}
