package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

public class WeatherObject {
    private double temperature;
    private double windSpeed;
    private String shortForecast;

    public WeatherObject(double temperature, double windSpeed, String shortForecast) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.shortForecast = shortForecast;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getShortForecast() {
        return shortForecast;
    }
}
