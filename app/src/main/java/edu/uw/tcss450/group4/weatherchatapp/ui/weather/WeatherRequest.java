package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherRequest {
    public static StringBuilder request(String latitude, String longitude) {
        StringBuilder result = new StringBuilder();

        String urlString = "http://10.0.2.2:5000/weather?lat=" + latitude + "&lon=" + longitude;


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");


            // Connect to the server
            urlConnection.connect();

            // Check the response code
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } else {
                // Handle error cases
                Log.e("Weather Request", "Error response code: " + responseCode);
            }
        } catch (IOException e) {
            Log.e("Weather Request", "Error during HTTP request: " + e.getMessage());
        } finally {
            // Close the connection and reader
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("Weather Request", "Error closing reader: " + e.getMessage());
                }
            }
        }

        return result;
    }
}
