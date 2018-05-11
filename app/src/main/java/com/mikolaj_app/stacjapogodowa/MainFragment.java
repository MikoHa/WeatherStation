package com.mikolaj_app.stacjapogodowa;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainFragment extends MeteoLab {
    public MeteoData mMeteoJsonData;

    public TextView mWelcomeText;
    public TextView mCounterTemperature;
    public TextView mCounterPressure;
    public TextView mCounterHumidity;
    public TextView mCounterInsolation;
    public SwipeRefreshLayout mSwipeRefreshLayout;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_fragment,container,false);

        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        mWelcomeText = view.findViewById(R.id.welcomeText);
        mCounterTemperature = view.findViewById(R.id.CounterTemperature);
        mCounterPressure = view.findViewById(R.id.CounterPressure);
        mCounterHumidity = view.findViewById(R.id.CounterHumidity);
        mCounterInsolation = view.findViewById(R.id.CounterInsolation);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(sServerState){
                    loadToast();
                    new MeteoItemsTask().execute();
                    updateWelcomeText();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        updateWelcomeText();
    }



    public void updateWelcomeText(){
        String positiveText = getString(R.string.positiveWelcomeText);
        String negativeText = getString(R.string.negativeWelcomeText);

        if(sServerState){
            mMeteoJsonData = getJsonData();

            mWelcomeText.setText(positiveText + " " + mMeteoJsonData.getName());
            mCounterTemperature.setText(mMeteoJsonData.getTemperature());
            mCounterPressure.setText(mMeteoJsonData.getPressure());
            mCounterHumidity.setText(mMeteoJsonData.getHumidity());
            mCounterInsolation.setText(mMeteoJsonData.getLightSensitivity());

        }else{
            mWelcomeText.setText(negativeText);
        }
    }

    public void loadToast(){
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        positiveToast = Toast.makeText(activity,R.string.updateServerToast,Toast.LENGTH_SHORT);
        negativeToast = Toast.makeText(activity,R.string.notConnectSeverToast,Toast.LENGTH_SHORT);
    }

}
