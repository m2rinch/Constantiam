package com.melissarinch.constantiumv1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.melissarinch.constantiumv1.data.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class ExerciseFeedbackActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner chartSpinner;
    LineChart chart;
    int[] xCoords;
    int[] yCoords;
    TextView chartTitle;
    String chartName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_feedback);
        chartTitle = findViewById(R.id.feedbackChartTitle);
        Spinner spinner = findViewById(R.id.chartSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.charts_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private List<Entry> getChartData(int _pos) {
        if (getIntent().hasExtra("Session")) {

            Session session = (Session) getIntent().getSerializableExtra("Session");

            switch(_pos) {
                case 0:
                    yCoords = parseData(session.getmCOPOverall());
                    break;
                case 1:
                    yCoords = parseData(session.getmCOPRight());
                    break;
                case 2:
                    yCoords = parseData(session.getmCOPLeft());
                    break;
                case 3:
                    yCoords = parseData(session.getmSI());
                    break;
                default:
                    yCoords = parseData(session.getmCOPOverall());
            }
            xCoords = generateTimePoints(yCoords);
            // Create Feedback Chart
            chart = findViewById(R.id.feedbackChart);

            // add data points to Entry object
            List<Entry> entries = new ArrayList<Entry>();
            for (int i = 0; i < xCoords.length; i++) {
                entries.add(new Entry(xCoords[i], yCoords[i]));
            }
            return entries;
        }
        return null;
    }

    private void createChart(List<Entry> entries, String _chartName){
            // create new data set
            LineDataSet dataSet = new LineDataSet(entries, _chartName);
            dataSet.setColor(Color.parseColor("#701112"));
            dataSet.setValueTextColor(Color.parseColor("#FFFFFF"));

            // modify x-axis
            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularityEnabled(true); // prevent x-axis duplicates
            // xAxis.setValueFormatter(new WeekLineChart());

            // modify y-axis
            YAxis yAxisRight = chart.getAxisRight();
            yAxisRight.setEnabled(false);

            // add data set to line chart
            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
            // refresh
            chart.invalidate();
    }
    private int[] parseData(String _responseData){
        // string split on comma
        String[] stringData = _responseData.split(",");
        // convert to int array
        int[] intData = new int[stringData.length];
        for(int i = 0; i < stringData.length; i++){
            intData[i] = Integer.parseInt(stringData[i]);
        }
        return intData;
    }

    private int[] generateTimePoints(int[] _yData){
        int[] timeData = new int[_yData.length];
        for(int i = 0; i < _yData.length; i++){
            timeData[i] = i;
        }
        return timeData;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
        // An item was selected. You can retrieve the selected item using
        chartName = parent.getItemAtPosition(pos).toString();
        chartTitle.setText(chartName);
        //getChartData will only return info is session has been populated
        List<Entry> entries = getChartData(pos);
        if(entries != null){
            createChart(entries, chartName);
            Toast.makeText(getApplicationContext(), chartName, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
