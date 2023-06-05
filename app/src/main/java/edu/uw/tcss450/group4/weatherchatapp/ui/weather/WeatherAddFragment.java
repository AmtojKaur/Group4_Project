package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.uw.tcss450.group4.weatherchatapp.R;

public class WeatherAddFragment extends Fragment {

    public WeatherAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonWeather = view.findViewById(R.id.button_weather);
        buttonWeather.setOnClickListener(v -> {
            String zipCode = getZipCodeFromInput();

            // Validate zip code
            if (isValidZipCode(zipCode)) {
                // Fetch weather data from API
                new WeatherRequestTask().execute(zipCode);

                Bundle bundle = new Bundle();
                bundle.putString("zipCode", zipCode);

                // Navigate to WeatherFragment using the generated NavDirections
                NavDirections action = WeatherAddFragmentDirections.actionWeatherAddFragmentToNavigationWeather();
                Navigation.findNavController(view).navigate(action.getActionId(), bundle);
            }
        });
    }

    private String getZipCodeFromInput() {
        String zipCode = "";

        // Retrieve the entered zip code from the TextInputEditText
        View view = getView();
        if (view != null) {
            TextInputEditText editLocation = view.findViewById(R.id.edit_location);
            if (editLocation != null) {
                zipCode = editLocation.getText().toString().trim();
            }
        }

        return zipCode;
    }

    private boolean isValidZipCode(String zipCode) {
        // Validate the zip code (e.g., check length, format, etc.)
        return zipCode.matches("\\d{5}");
    }

    private class WeatherRequestTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            String zipCode = params[0];
            try {
                String apiUrl = "https://amtojk-tcss450-labs.herokuapp.com/weather/zip?zip=" + zipCode; // Replace with your actual API URL
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    return new JSONObject(response.toString());
                } else {
                    // Handle the error case
                }

                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject response) {
            if (response != null) {
                // Parse the weather data from the response and save it to your desired storage
                String location = response.optString("location");
                double temperature = response.optDouble("temperature");
                String weatherDescription = response.optString("description");

                saveWeatherData(location, temperature, weatherDescription);
            } else {
                // Handle the case when the API request fails
            }
        }

        private void saveWeatherData(String location, double temperature, String weatherDescription) {
            // Save the weather data to your desired storage (e.g., SharedPreferences, Room database, etc.)
            // Here's an example of saving to SharedPreferences:
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("WeatherData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("location", location);
            editor.putString("temperature", String.valueOf(temperature));
            editor.putString("weatherDescription", weatherDescription);
            editor.apply();
        }
    }
}
