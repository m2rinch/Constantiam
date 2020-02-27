package com.melissarinch.constantiumv1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.melissarinch.constantiumv1.data.Session;

import java.util.ArrayList;
import java.util.List;


public class ExerciseFeedbackActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    ScatterChart chart;
    LineChart varChart;
    double[] xCoords;
    double[] yCoords;
    double[] xCoordsV;
    double[] yCoordsV;
    TextView chartTitle;
    SeekBar seekBar;
    String chartName;
    ImageView rightFoot;
    ImageView leftFoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_feedback);
        chartTitle = findViewById(R.id.feedbackChartTitle);
        rightFoot = findViewById(R.id.overlay);
        leftFoot = findViewById(R.id.overlayLeft);
        spinner = findViewById(R.id.chartSpinner);
        seekBar = findViewById(R.id.seekBar);
        varChart = findViewById(R.id.variabilityChart);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.charts_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;


            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                int alpha = seekBar.getProgress();
                chart.centerViewTo(progress, (float)yCoords[progress], YAxis.AxisDependency.LEFT);
              //  rightFoot.getBackground().setAlpha(alpha*25);
               // leftFoot.getBackground().setAlpha(255-(alpha*25));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void setChartData(int _pos) {
        if (getIntent().hasExtra("Session")) {

            Session session = (Session) getIntent().getSerializableExtra("Session");
            chart = findViewById(R.id.feedbackChart);
            varChart = findViewById(R.id.variabilityChart);
            switch(_pos) {
                case 0:
                    yCoords = parseData(session.getmCOPOverallY());
                    yCoordsV = parseData(session.getVariabilityOverall());
                    chart.setBackgroundResource(R.drawable.shoeprint);
                    break;
                case 1:
                    yCoords = parseData(session.getmCOPRightY());
                    yCoordsV = parseData(session.getVariabilityRight());
                    chart.setBackgroundResource(R.drawable.right_shoe);
                    break;
                case 2:
                    yCoords = parseData(session.getmCOPLeftY());
                    yCoordsV = parseData(session.getVariabilityLeft());
                    chart.setBackgroundResource(R.drawable.left_shoe);
                    break;
                default:
                    yCoords = parseData(session.getmCOPOverallY());
                    yCoordsV = parseData(session.getVariabilityLeft());
                    chart.setBackgroundResource(R.drawable.shoeprint);
            }
            xCoords = generateTimePoints(yCoords);
            xCoordsV = generateTimePoints(yCoordsV);
            // Create Feedback Chart


        }
    }

    private List<Entry> getEntries(double[] xCoords, double[] yCoords) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < xCoords.length; i++) {
            entries.add(new Entry((float)xCoords[i], (float)yCoords[i]));
        }
        return entries;
    }

    private void createChart(List<Entry> entries, String _chartName){
            // create new data set
            ScatterDataSet dataSet = new ScatterDataSet(entries, _chartName);
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
            ScatterData scatterData = new ScatterData(dataSet);
            chart.setData(scatterData);
            chart.setVisibleXRangeMaximum(1);
            chart.getXAxis().setDrawGridLines(false);
            chart.getAxisLeft().setDrawGridLines(false);
            chart.getAxisRight().setDrawGridLines(false);
            // refresh
            chart.notifyDataSetChanged();
            chart.invalidate();
    }

    private void createVarChart(List<Entry> entries, String _chartName){
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
        varChart.setData(lineData);
        // refresh
        varChart.notifyDataSetChanged();
        varChart.invalidate();
    }
    private double[] parseData(String _responseData){
        // string split on comma
        String[] stringData = _responseData.split(",");
        // convert to int array
        double[] chartData = new double[stringData.length];
        for(int i = 0; i < stringData.length; i++){
            chartData[i] = Double.parseDouble(stringData[i]);
        }
        return chartData;
    }

    private double[] generateTimePoints(double[] _yData) {
        double[] timeData = new double[_yData.length];
        for (int i = 0; i < _yData.length; i++) {
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
        setChartData(pos);
        List<Entry> entries = getEntries(xCoords, yCoords);
        List<Entry> varEntries = getEntries(xCoordsV, yCoordsV);
        if(entries != null & varEntries != null){
            createChart(entries, chartName);
            createVarChart(varEntries, chartName);
            //Toast.makeText(getApplicationContext(), chartName, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
