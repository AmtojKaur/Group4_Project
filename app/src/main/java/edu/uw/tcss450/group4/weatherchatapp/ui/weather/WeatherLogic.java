package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherLogic {
    private ArrayList<WeatherObject> hourly;
    private ArrayList<WeatherObject> daily;

    public WeatherLogic(String jsonString) {
        hourly = new ArrayList<>();
        daily = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(jsonString);
            JSONObject properties = json.getJSONObject("properties");
            JSONArray periods = properties.getJSONArray("periods");

            for (int i = 0; i < periods.length(); i++) {
                JSONObject period = periods.getJSONObject(i);
                double temperature = period.getDouble("temperature");
                double windSpeed = Double.parseDouble(period.getString("windSpeed").split(" ")[0]);
                String shortForecast = period.getString("shortForecast");

                if (i < 24) { // Assume the first 24 periods are hourly forecasts
                    hourly.add(new WeatherObject(temperature, windSpeed, shortForecast));
                } else { // The rest are daily forecasts
                    daily.add(new WeatherObject(temperature, windSpeed, shortForecast));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<WeatherObject> getHourly() {
        return hourly;
    }

    public ArrayList<WeatherObject> getDaily() {
        return daily;
    }

    public WeatherRVModel getCurrent() {
        return null;
    }
}
