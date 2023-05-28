package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

import android.graphics.drawable.Drawable;

public class WeatherRVModel {
    private String time;
    private String temperature;
    private Drawable conditionIcon;
    private String windSpeed;
    private String shortForecast; // Changed the attribute name here

    public WeatherRVModel(String time, String temperature, String windSpeed) {
        this.time = time;
        this.temperature = temperature;
        this.conditionIcon = conditionIcon;
        this.windSpeed = windSpeed;
        this.shortForecast = shortForecast; // And here
    }

    public String getTime() {
        return time;
    }

    public String getTemperature() {
        return temperature;
    }

    public Drawable getConditionIcon() {
        return conditionIcon;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getShortForecast() { // And here
        return shortForecast;
    }
}
