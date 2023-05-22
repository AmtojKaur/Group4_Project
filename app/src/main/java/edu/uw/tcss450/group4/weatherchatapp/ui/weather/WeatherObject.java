package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

import androidx.annotation.NonNull;

public class WeatherObject {
    private String temp;
    private String description;
    private String windSpeed;
    public WeatherObject(String temp, String description, String windSpeed) {
        this.temp = temp;
        this.description = description;
        this.windSpeed = windSpeed;
    }

    @NonNull
    @Override
    public String toString() {
        return "temp: '" + this.temp + "' desc: '" + this.description + "' wind: '" + this.windSpeed + "'";
    }

    public String getTemp() {

        return this.temp;
    }

    public String getWindSpeed() {

        return this.windSpeed;
    }

    public String getDescription() {

        return this.description;
    }
}
