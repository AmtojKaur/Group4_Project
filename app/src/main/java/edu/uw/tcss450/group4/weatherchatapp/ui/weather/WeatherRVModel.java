package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

import android.graphics.drawable.Drawable;

public class WeatherRVModel {
    private String time;
    private String temp;
    private Drawable icon;
    private String windSpeed;

    public WeatherRVModel(String time, String temp, Drawable icon, String windSpeed) {
        this.time = time;
        this.temp = temp;
        this.icon = icon;
        this.windSpeed = windSpeed;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }


}