package edu.uw.tcss450.group4.weatherchatapp.ui.weather;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherLogic {
    private ArrayList<WeatherObject> hourly;
    private ArrayList<WeatherObject> daily;
    private WeatherObject current;

    public WeatherLogic(String jsonString) {
        hourly = new ArrayList<>();
        daily = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonString);
            JSONObject properties = json.getJSONObject("properties");

            JSONArray twentyFourHourForecast = properties.getJSONArray("twentyFourHourForecast");
            JSONArray sevenDayForecast = properties.getJSONArray("sevenDayForecast");

            // Process 24-hour forecast
            for (int i = 0; i < twentyFourHourForecast.length(); i++) {
                JSONObject period = twentyFourHourForecast.getJSONObject(i);
                String time = period.getString("time");
                double temperature = parseTemperature(period.getString("temperature"));
                String forecast = period.getString("forecast");

                hourly.add(new WeatherObject(time, temperature, forecast));
            }

            // Process 7-day forecast
            for (int i = 0; i < sevenDayForecast.length(); i++) {
                JSONObject period = sevenDayForecast.getJSONObject(i);
                String day = period.getString("day");
                double highTemperature = parseTemperature(period.getString("temperature"));
                String forecast = period.getString("forecast");

                daily.add(new WeatherObject(day, highTemperature, forecast));
            }

            // Set the current weather object
            JSONObject currentConditions = properties.getJSONObject("currentConditions");
            String temperature = currentConditions.getString("temperature");
            String forecast = currentConditions.getString("forecast");

            current = new WeatherObject("Current", parseTemperature(temperature), forecast);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private double parseTemperature(String temperatureString) {
        // Assuming the temperature string is in the format "48°F"
        String[] parts = temperatureString.split("°");
        if (parts.length == 2) {
            String temperatureValue = parts[0];
            try {
                return Double.parseDouble(temperatureValue);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0.0;
    }

    public ArrayList<WeatherObject> getHourly() {
        return hourly;
    }

    public ArrayList<WeatherObject> getDaily() {
        return daily;
    }

    public WeatherObject getCurrent() {
        return current;
    }
}
