package com.mikolaj_app.stacjapogodowa;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends MeteoLab {
    private Spinner mSpinnerStatistics;
    private Button mButtonChoose;

    private LineChart mChart;
    private static List<Entry> entries = new ArrayList<>();
    public String StatisticsType;

    private final String EXTRA_STATISTICS_TYPE = "statisticsTypeSave";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            StatisticsType = savedInstanceState.getString(EXTRA_STATISTICS_TYPE);
            Log.d("statType", " :" + StatisticsType);
            setStatisticsType(EXTRA_STATISTICS_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_statistics_fragment,container,false);

        mSpinnerStatistics = view.findViewById(R.id.statistics_spinner);
        mButtonChoose = view.findViewById(R.id.buttonChoose);
        mChart = view.findViewById(R.id.chart);

        mButtonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatisticsType = (String) mSpinnerStatistics.getSelectedItem();
                setStatisticsType(StatisticsType);
            }
        });


        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_STATISTICS_TYPE,StatisticsType);
        Log.d("statType", " :" + StatisticsType);

    }


    public void instalChart(){
        LineDataSet dataSet = new LineDataSet(entries,StatisticsType);
        dataSet.setDrawValues(false);

         dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
         dataSet.setColor(Color.GREEN);
         dataSet.setDrawCircles(false);
         dataSet.setLineWidth(2f);
        //dataSet.setValueTextColor(Color.CYAN);
        LineData lineData = new LineData(dataSet);
        mChart.setData(lineData);
        mChart.invalidate();
    }

    public void addEntry(int xDay, float yStat){
        LineData data = mChart.getData();
        entries.add(new Entry(xDay,yStat));
        //data.notifyDataChanged();
        mChart.notifyDataSetChanged();
    }


    public void configureAxis(){

        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.BLACK);

        YAxis YAxisLeft = mChart.getAxisLeft();

        mChart.getAxisRight().setEnabled(false);
    }


    public void setStatisticsType(String statisticsType){

        Float[] temperatureStat24;
        Float[] humidityStat24;
        Float[] lightSensitivity24;
        Float[] pressure24;
        int hour = 1;
        entries.clear();

        if(MainFragment.sServerState){
            MeteoData meteoJsonData = getJsonData();

            switch (statisticsType){

                case "Temperatura":

                    temperatureStat24 = meteoJsonData.getTemperatureStat24();

                    for (Float aTemperatureStat24 : temperatureStat24) {
                        addEntry(hour, aTemperatureStat24);
                        hour++;
                    }

                    break;

                case "Wilgotność":

                    humidityStat24 = meteoJsonData.getHumidityStat24();

                    for (Float aHumidityStat24 : humidityStat24) {
                        addEntry(hour, aHumidityStat24);
                        hour++;
                    }

                    break;

                case "Nasłonecznienie":

                    lightSensitivity24 = meteoJsonData.getLightSensitivity24();

                    for (Float aLightSensitivity24 : lightSensitivity24) {
                        addEntry(hour, aLightSensitivity24);
                        hour++;
                    }

                    break;

                case "Ciśnienie":

                    pressure24 = meteoJsonData.getPressure24();

                    for (Float aPressure24 : pressure24) {
                        addEntry(hour, aPressure24);
                        hour++;
                    }

                    break;
            }

            instalChart();
            configureAxis();

    }else {
            Toast.makeText(getActivity(),R.string.negativeStatText,Toast.LENGTH_SHORT).show();
        }

    }




}
