package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

public class WeatherObject {
    private String temperature;
    private double windSpeed;
    private String shortForecast;

    public WeatherObject(String temperature, double windSpeed, String shortForecast) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.shortForecast = shortForecast;
    }

    public String getTemperature() {

        return temperature;
    }

    public void setTemperature(String temperature) {

        this.temperature = temperature;
    }

    public double getWindSpeed() {

        return windSpeed;
    }

    public String getShortForecast() {

        return shortForecast;
    }

    public void setShortForecast(String shortForecast) {

        this.shortForecast = shortForecast;
    }
}
