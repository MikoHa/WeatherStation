package com.mikolaj_app.stacjapogodowa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

public class ConnectPickerFragment extends DialogFragment {
    private Spinner mSpinnerServer;
    public static final String EXTRA_SENSOR_NAME =
            "com.mikolaj_app.stacjapogodowa.sensor_type";

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.management_picker_fragment, null);

        mSpinnerServer = v.findViewById(R.id.server_spinner);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(R.string.buttonConnect, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String serverType = (String) mSpinnerServer.getSelectedItem();
                        sendResults(Activity.RESULT_OK,serverType);
                    }
                })
                .create();

    }

    //wysylanie danych do fragmentu docelowego
    private void sendResults(int resultCode, String sensorType){
        if (getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_SENSOR_NAME,sensorType);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(),resultCode,intent);
    }

}
