package com.mikolaj_app.stacjapogodowa;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mikolaj on 25.03.2018.
 */

public class MeteoLab extends Fragment {

    private final String TAG = "MeteoLabHTTP";
    public static Boolean sServerState = false;
    private final String url = "https://api.myjson.com/bins/12j7w6"; //szkielet api
    public static MeteoData mMeteoData;
    public Toast positiveToast;
    public Toast negativeToast;

    private Float[] temperatureStat24 = new Float[24];
    private Float[] humidityStat24 = new Float[24];
    private Float[] lightSensitivity24 = new Float[24];
    private Float[] pressure24 = new Float[24];


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }



    //wybieranie serwera
    public void setServer(String serverName){                         //InterruptedException dla usypiania wątku
        loadToast();

        switch (serverName){

            case "Czujnik nr 1":
                new MeteoItemsTask().execute();
                break;

            case "Czujnik nr 2":
                new MeteoItemsTask().execute();
                break;

            case "Czujnik nr 3":
                new MeteoItemsTask().execute();
                break;
        }

    }

    //implementacja połączenia sieciowego (zaczęte 26.03.2018)
    //pobieranie surowych danych z określonego adresu URL -> zwracanie ich w postatci tablicy bajtów
    public byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();  //tworzenie obiektu połączenia wskazującego na podany adres URL

        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage()+
                            ": z " +
                                urlSpec);
            }

            //pobieranie danych
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0){
                out.write(buffer,0,bytesRead);
            }
            //zamykam polaczenie, kopiuje dane do tablicy
            out.close();
            return out.toByteArray();

        }
        finally {
            connection.disconnect();
        }
    }

    //konwertowanie wynikow zwracanych przez getUrlBytes do Stringa
    public String getUrlString(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }



    public class MeteoItemsTask extends AsyncTask<Void,Void,MeteoData>{
        @Override
        protected MeteoData doInBackground(Void... params) {
            return meteoItems();
        }

        @Override
        protected void onPostExecute(MeteoData meteoData) {
            mMeteoData = meteoData;
            PagerAdapter.mFragments.get(1).onResume(); //aktualizuje dane
        }
    }



    public MeteoData meteoItems(){
        MeteoData meteoData = new MeteoData();

        try {
            String jsonString = getUrlString(url);
            JSONObject jsonObject = new JSONObject(jsonString);
            parseItems(meteoData,jsonObject);
            positiveToast.show();
            MainFragment.sServerState = true;
            Log.i(TAG, "Pobrano dane z bartnikowego API: " + jsonString);
        } catch (IOException ioe) {
            negativeToast.show();
            MainFragment.sServerState = false;
            Log.e(TAG, "Nie można pobrać danych z bartnikowego API :( ", ioe);
        } catch (JSONException je){
            Log.e(TAG,"Nie mozna sparsowac otrzymanych danych JSON", je);
            MainFragment.sServerState = false;
        }

        return meteoData;
    }


    //parsowanie
    private void parseItems(MeteoData item, JSONObject jsonObject)
        throws IOException, JSONException {

                JSONObject meteoJsonObject = jsonObject;

                item.setId(meteoJsonObject.getString("sensorId"));
                item.setName(meteoJsonObject.getString("sensorName"));

                Log.d(TAG, "name" + item.getName() + " id" + item.getId());

                JSONObject currentData = meteoJsonObject.getJSONObject("currentData");

                 item.setTemperature(currentData.getString("temperature"));
                 item.setHumidity(currentData.getString("humidity"));
                 item.setLightSensitivity(currentData.getString("lightSensitivity"));
                 item.setPressure(currentData.getString("pressure"));


                JSONArray dataMeteoArray = meteoJsonObject.getJSONArray("last24HoursData");
                for(int i = 0; i < dataMeteoArray.length(); i++){
                    JSONObject dataMeteoObject = dataMeteoArray.getJSONObject(i);
                    temperatureStat24[i] =  Float.parseFloat( dataMeteoObject.getString("temperature"));
                    humidityStat24[i] = Float.parseFloat( dataMeteoObject.getString("humidity"));
                    lightSensitivity24[i] = Float.parseFloat( dataMeteoObject.getString("lightSensitivity"));
                    pressure24[i] = Float.parseFloat( dataMeteoObject.getString("pressure"));
                }

                item.setTemperatureStat24(temperatureStat24);
                item.setHumidityStat24(humidityStat24);
                item.setLightSensitivity24(lightSensitivity24);
                item.setPressure24(pressure24);



        }



    public void loadToast(){
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        positiveToast = Toast.makeText(activity,R.string.connectServerToast,Toast.LENGTH_SHORT);
        negativeToast = Toast.makeText(activity,R.string.notConnectSeverToast,Toast.LENGTH_SHORT);
    }

    public MeteoData getJsonData(){
        return mMeteoData;
    }



}//koniec klasy
