package com.mikolaj_app.stacjapogodowa;

public class MeteoData {
    private String mId;
    private String mName;
    private String mTemperature;
    private String mHumidity;
    private String mLightSensitivity;
    private String mPressure;

    private Float[] mTemperatureStat24;
    private Float[] mHumidityStat24;
    private Float[] mLightSensitivity24;
    private Float[] mPressure24;


    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTemperature() {
        return mTemperature;
    }

    public void setTemperature(String temperature) {
        mTemperature = temperature;
    }

    public String getHumidity() {
        return mHumidity;
    }

    public void setHumidity(String humidity) {
        mHumidity = humidity;
    }

    public String getLightSensitivity() {
        return mLightSensitivity;
    }

    public void setLightSensitivity(String lightSensitivity) {
        mLightSensitivity = lightSensitivity;
    }

    public String getPressure() {
        return mPressure;
    }

    public void setPressure(String pressure) {
        mPressure = pressure;
    }


    //Statystyki:

    public Float[] getTemperatureStat24() {
        return mTemperatureStat24;
    }

    public void setTemperatureStat24(Float[] temperatureStat24) {
        mTemperatureStat24 = temperatureStat24;
    }

    public Float[] getHumidityStat24() {
        return mHumidityStat24;
    }

    public void setHumidityStat24(Float[] humidityStat24) {
        mHumidityStat24 = humidityStat24;
    }

    public Float[] getLightSensitivity24() {
        return mLightSensitivity24;
    }

    public void setLightSensitivity24(Float[] lightSensitivity24) {
        mLightSensitivity24 = lightSensitivity24;
    }

    public Float[] getPressure24() {
        return mPressure24;
    }

    public void setPressure24(Float[] pressure24) {
        mPressure24 = pressure24;
    }
}
