package com.melissarinch.constantiumv1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.melissarinch.constantiumv1.data.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ExerciseFeedbackActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    ScatterChart chart;
    ScatterData scatterData;
    // LineChart varChart;
    double[] xCoords;
    double[] yCoords;
    double[] xCoordsV;
    double[] yCoordsV;
    double[] rightForce;
    double[] leftForce;
    CheckBox forceCheck;
    CheckBox zoneCheck;
    TextView chartTitle;
    SeekBar seekBar;
    String chartName;
    ImageView rightFoot;
    ImageView leftFoot;
    Session session;
    TextView rightText;
    TextView leftText;
    ScatterDataSet dataSet;
    List<Entry> entries;
    enum footType {
        RIGHT,
        LEFT,
        OVERALL
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_feedback);
        chartTitle = findViewById(R.id.feedbackChartTitle);
        rightFoot = findViewById(R.id.overlay);
        leftFoot = findViewById(R.id.overlayLeft);
        spinner = findViewById(R.id.chartSpinner);
        seekBar = findViewById(R.id.seekBar);
        rightText = findViewById(R.id.rightText);
        leftText = findViewById(R.id.leftText);
        forceCheck = findViewById(R.id.force_check);
        zoneCheck = findViewById(R.id.zone_check);

        entries = new ArrayList<>();
       forceCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
               if(isChecked){
                   showForce(spinner.getSelectedItemPosition());
               }
               else {
                   hideForce();
               }
           }
       });

        zoneCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    showZones(spinner.getSelectedItemPosition());
                }
                else {
                    hideZones(spinner.getSelectedItemPosition());
                }
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.charts_array, R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;


            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                UpdateChartValues(new double[] {xCoords[progress]}, new double[]{yCoords[progress]});
                //chart.centerViewTo((float)xCoords[progress], (float)yCoords[progress], YAxis.AxisDependency.LEFT);
                if(forceCheck.isChecked()){
                    int rightProgress = calculateProgress(progress, footType.RIGHT);
                    int leftProgress = calculateProgress(progress, footType.LEFT);
                    rightText.setText(String.valueOf(rightProgress) + '%');
                    leftText.setText(String.valueOf(leftProgress) + '%');
                    int alphaR = (int)(((double)rightProgress/100)*255);
                    int alphaL = (int)(((double)leftProgress/100)*255);
                    rightFoot.getBackground().setAlpha(alphaR);
                    leftFoot.getBackground().setAlpha(alphaL);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private int calculateProgress(int pos, footType _foot) {
        if(_foot == footType.RIGHT) {
            return (int)((double)Math.abs(rightForce[pos]) / ((double)Math.abs(rightForce[pos]) + Math.abs(leftForce[pos])) * 100);
        }
        return (int)(Math.abs(leftForce[pos]) / (Math.abs(rightForce[pos]) + Math.abs(leftForce[pos])) * 100);
    }
    private void setChartData(int _pos, Session _session) {

            chart = findViewById(R.id.feedbackChart);
            rightForce = parseData(_session.getmCOPRightY());
            leftForce = parseData(_session.getmCOPLeftY());
            switch(_pos) {
                case 0:
                    yCoords = parseData(_session.getmCOPOverallY());
                    xCoords = parseData(_session.getmCOPOverallX());
                    chart.setBackgroundResource(R.drawable.shoeprint);
                    chart.getXAxis().setAxisMaximum(22);
                    chart.getXAxis().setAxisMinimum(-20);
                    break;
                case 1:
                    yCoords = parseData(_session.getmCOPRightY());
                    xCoords = parseData(_session.getmCOPRightX());
                    chart.setBackgroundResource(R.drawable.right_shoe);
                    chart.getXAxis().setAxisMaximum(22);
                    chart.getXAxis().setAxisMinimum(12);
                    break;
                case 2:
                    yCoords = parseData(_session.getmCOPLeftY());
                    xCoords = parseData(_session.getmCOPLeftX());
                    chart.setBackgroundResource(R.drawable.left_shoe);
                    chart.getXAxis().setAxisMaximum(-12);
                    chart.getXAxis().setAxisMinimum(-21);
                    break;
            }
          //  xCoords = (generateTimePoints(yCoords));
    }

    private List<Entry> getEntries(double[] xCoords, double[] yCoords) {
        entries.clear();
        for (int i = 0; i < xCoords.length; i++) {
            entries.add(new Entry((float)xCoords[i], (float)yCoords[i]));
        }
        return entries;
    }

    private void createChart(List<Entry> entries, String _chartName){
            // create new data set
           // Collections.sort(entries, new EntryXComparator());
            dataSet = new ScatterDataSet(entries, _chartName);
            dataSet.setColor(Color.parseColor("#701112"));
            dataSet.setValueTextColor(Color.parseColor("#FFFFFF"));

            // modify x-axis
            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularityEnabled(true); // prevent x-axis duplicates
            // xAxis.setValueFormatter(new WeekLineChart());

            // modify y-axis
            chart.getAxisRight().setEnabled(false);
            YAxis yAxis = chart.getAxisLeft();

            // add data set to line chart
            scatterData = new ScatterData(dataSet);
            chart.setData(scatterData);

           // chart.setVisibleXRangeMaximum(1);
            //chart.setVisibleYRangeMaximum(1, YAxis.AxisDependency.LEFT);
            chart.getXAxis().setDrawGridLines(false);
            yAxis.setDrawGridLines(false);
            yAxis.setAxisMinimum(-18);
            yAxis.setAxisMaximum(10);
            chart.getAxisRight().setDrawGridLines(false);
            chart.setScaleEnabled(false);
            chart.getLegend().setEnabled(false);
            // refresh

            chart.notifyDataSetChanged();
            chart.invalidate();
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

//    private double[] generateTimePoints(double[] _yData) {
//        double[] timeData = new double[_yData.length];
//        for (int i = 0; i < _yData.length; i++) {
//            timeData[i] = i;
//        }
//        return timeData;
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
        // An item was selected. You can retrieve the selected item using
        chartName = parent.getItemAtPosition(pos).toString();
        chartTitle.setText(chartName);
        if (getIntent().hasExtra("Session")) {
            session = (Session) getIntent().getSerializableExtra("Session");
            setChartData(pos, session);
        }
        //getChartData will only return info is session has been populated
        zoneCheck.setChecked(false);
        forceCheck.setChecked(false);

        UpdateChartValues(new double[] {xCoords[0]}, new double[]{yCoords[0]});
    }

    private void UpdateChartValues(double[] _xCoords, double[] _yCoords) {
        entries = getEntries(_xCoords, _yCoords);

        if(entries != null){
            if(chart.getScatterData() == null) {
                createChart(entries, chartName);
                entries = getEntries(_xCoords, _yCoords);
                scatterData.notifyDataChanged();
                chart.notifyDataSetChanged();
                chart.invalidate();
            }
            else {
                scatterData.notifyDataChanged();
                chart.notifyDataSetChanged();
                chart.invalidate();
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void showForce(int pos){
        if(pos == 0) {
            //replace image background depending on the selected chart
            rightFoot.setBackgroundResource(R.drawable.shoeprint_right_trans);
            leftFoot.setBackgroundResource(R.drawable.shoeprint_left_trans);
            rightFoot.setVisibility(View.VISIBLE);
            leftFoot.setVisibility(View.VISIBLE);
            rightText.setVisibility(View.VISIBLE);
            leftText.setVisibility(View.VISIBLE);
        }
    }

    private void hideForce(){
        rightFoot.setVisibility(View.GONE);
        leftFoot.setVisibility(View.GONE);
        rightText.setVisibility(View.GONE);
        leftText.setVisibility(View.GONE);
    }

    private void showZones(int pos){
        // delete variability from dataset

        switch(pos) {
            case 0:
                chart.setBackgroundResource(R.drawable.shoeprint_col);
                break;
            case 1:
                chart.setBackgroundResource(R.drawable.right_col);
                hideForce();
                break;
            case 2:
                chart.setBackgroundResource(R.drawable.left_col);
                hideForce();
                break;
        }

    }

    private void hideZones(int pos) {
        switch(pos) {
            case 0:
                chart.setBackgroundResource(R.drawable.shoeprint);
                break;
            case 1:
                chart.setBackgroundResource(R.drawable.right_shoe);
                hideForce();
                break;
            case 2:
                chart.setBackgroundResource(R.drawable.left_shoe);
                hideForce();
                break;
        }
    }

    public void viewSummary(View v){
        Intent intent = new Intent(getApplicationContext(), SummaryActivity.class);
        intent.putExtra("Session", session);
        startActivity(intent);
    }

}
