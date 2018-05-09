package com.mikolaj_app.stacjapogodowa;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class ManagementFragment extends MeteoLab {
    public Spinner mSpinnerServer;
    public Button mButtonConnect;

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_management_fragment,container,false);

        mSpinnerServer = (Spinner) view.findViewById(R.id.server_spinner);
        mButtonConnect = (Button) view.findViewById(R.id.buttonConnect);

        mButtonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String serverType = (String) mSpinnerServer.getSelectedItem();
                 setServer(serverType);
            }
        });

        return view;
    }
}
