package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

public class WeatherAddItem {
    private String city;
    private String description;
    private String time;
    private String temperature;

    public WeatherAddItem(String city, String description, String time, String temperature) {
        this.city = city;
        this.description = description;
        this.time = time;
        this.temperature = temperature;
    }

    // Getters and setters for the item's properties
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
