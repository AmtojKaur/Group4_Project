package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentWeatherBinding;

public class WeatherFragment extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private ArrayList<WeatherRVModel> dailyWeatherList;
    private WeatherRVAdapter dailyWeatherAdapter;

    private ArrayList<WeatherRVModel> weeklyForecastList;
    private WeatherRVAdapter weeklyForecastAdapter;

    private EditText inputBox; // Declare the input box as a class variable

    private String currentZipCode = "98105"; // Default zip code for current location

    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton saveButton = view.findViewById(R.id.save_non_transition_alpha);
        saveButton.setOnClickListener(v -> {
            // Navigate to WeatherAddFragment
            Navigation.findNavController(view).navigate(R.id.action_navigation_weather_to_weatherAddFragment);
        });
        AppCompatImageButton changeLocationButton = view.findViewById(R.id.changeLocationButton);
        changeLocationButton.setOnClickListener(v -> {
            // Open the location popup here
            showLocationPopup();
        });

        new WeatherRequestTask().execute(currentZipCode);

        FragmentWeatherBinding weatherBinding = FragmentWeatherBinding.bind(requireView());
        weatherBinding.dayAndCityText.setText("Tacoma"); // Default location for search
        weatherBinding.tempText.setText("-----");
        weatherBinding.idTVShortForecast.setText("-");
        Drawable condIcon = ContextCompat.getDrawable(getActivity(), R.drawable.cloud);
        condIcon.setBounds(0, 0, 1, 1);
        weatherBinding.curIcon.setImageDrawable(condIcon);

        // Bottom Nav
        FragmentWeatherBinding binding = FragmentWeatherBinding.bind(getView());
        binding.buttonNavHome.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Home");
            Navigation.findNavController(getView()).navigate(
                    WeatherFragmentDirections.actionNavigationWeatherToNavigationHome()
            );
        });

        binding.buttonNavConnections.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Connections");
            Navigation.findNavController(getView()).navigate(
                    WeatherFragmentDirections.actionNavigationWeatherToNavigationConnections()
            );
        });

        binding.buttonNavChat.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Chat");
            Navigation.findNavController(getView()).navigate(
                    WeatherFragmentDirections.actionNavigationWeatherToNavigationChat()
            );
        });

        binding.buttonNavWeather.setOnClickListener(button -> {
            // do nothing
            Log.d("Button Clicked", "Nav Weather");
        });

        requestLocationPermission();
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Get the device's current location
            LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    currentZipCode = getZipCodeFromCoordinates(latitude, longitude);
                    new WeatherRequestTask().execute(currentZipCode);
                }
            }
        }
    }

    private String getZipCodeFromCoordinates(double latitude, double longitude) {
        String zipCode = "Unknown";
        try {
            Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                zipCode = addresses.get(0).getPostalCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zipCode;
    }

    private void showLocationPopup() {
        // Create and show the location popup here
        // You can use AlertDialog or any other custom dialog implementation
        // Customize the popup to your needs
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Change Location")
                .setMessage("Click Ok for Current Location")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Retrieve the current location
                    getCurrentLocation();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Handle the Cancel button click if needed
                });

        AlertDialog locationPopup = builder.create();
        locationPopup.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        inputBox = view.findViewById(R.id.inputBox); // Assign the input box

        Button searchButton = view.findViewById(R.id.searchbtn);

        searchButton.setOnClickListener(v -> {
            String zipCode = inputBox.getText().toString().trim();
            if (!zipCode.matches("\\d{5}")) {
                Toast.makeText(getContext(), "Please enter a valid 5-digit zip code", Toast.LENGTH_SHORT).show();
                return;
            }
            currentZipCode = zipCode;
            new WeatherRequestTask().execute(currentZipCode);

            // Clear the input box
            inputBox.setText("");

            // Hide the keyboard
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(inputBox.getWindowToken(), 0);
        });

        return view;

    }

    private class WeatherRequestTask extends AsyncTask<String, Void, WeatherViewModel> {

        private String zipCode;
        private FragmentWeatherBinding weatherBinding;

        @Override
        protected WeatherViewModel doInBackground(String... params) {
            zipCode = params[0];
            // Geocode API REQUEST
            String cityName = getCityNameFromZipCode(zipCode);
            if (cityName == null) {
                cityName = "Unknown";
            }
            publishProgress();

            // Weather API REQUEST
            String jsonString = String.valueOf(WeatherRequest.request(zipCode));
            return new WeatherViewModel(jsonString, cityName);
        }

        protected void onProgressUpdate(Void... values) {
            FragmentWeatherBinding weatherBinding = FragmentWeatherBinding.bind(requireView());
            weatherBinding.dayAndCityText.setText("Loading...");
        }

        protected void onPostExecute(WeatherViewModel weather) {
            FragmentWeatherBinding weatherBinding = FragmentWeatherBinding.bind(requireView());

            weatherBinding.dayAndCityText.setText(weather.getCityName());

            WeatherObject currentWeather = weather.getCurrentConditions();
            if (currentWeather != null) {
                String temperatureString = currentWeather.getTemperature();
                String shortForecast = currentWeather.getShortForecast();
                String temperature = extractNumericTemperature(temperatureString);


                if (temperature != null) {
                    // Set the extracted temperature value in the appropriate view
                    weatherBinding.tempText.setText(temperature);
                } else {
                    // Display a default value or handle the case when the temperature cannot be extracted
                    weatherBinding.tempText.setText("--");
                }


                // Set the temperature and short forecast in the appropriate views
                weatherBinding.tempText.setText(temperature);
                weatherBinding.idTVShortForecast.setText(shortForecast);
                ImageView weatherIconImageView = weatherBinding.curIcon;
                if (shortForecast.equals("Sunny")) {
                    weatherIconImageView.setImageResource(R.drawable.sunny_temperature_icon);
                } else if (shortForecast.equals("Cloudy")) {
                    weatherIconImageView.setImageResource(R.drawable.clouds_cloudy_icon);
                } else if (shortForecast.equals("Rainy")) {
                    weatherIconImageView.setImageResource(R.drawable.cloudy_forecast_rain_icon);
                } else if (shortForecast.equals("Clear")) {
                    weatherIconImageView.setImageResource(R.drawable.sunny_temperature_icon);
                } else if (shortForecast.equals("Mostly Clear")) {
                    weatherIconImageView.setImageResource(R.drawable.sun_sunny_icon);
                } else if (shortForecast.equals("Mostly Rainy")) {
                    weatherIconImageView.setImageResource(R.drawable.cloudy_rain_sunny_icon);
                } else if (shortForecast.equals("Mostly Sunny")) {
                    weatherIconImageView.setImageResource(R.drawable.sun_sunny_icon);
                } else if (shortForecast.equals("Partly Cloudy")) {
                    weatherIconImageView.setImageResource(R.drawable.sun_sunny_icon);
                } else if (shortForecast.equals("Haze")) {
                    weatherIconImageView.setImageResource(R.drawable.mist_weather_icon);
                }
            }

            dailyWeatherList = new ArrayList<>();
            dailyWeatherAdapter = new WeatherRVAdapter(dailyWeatherList);
            weatherBinding.twentyFourHourForecastRecyclerView.setAdapter(dailyWeatherAdapter);

            weeklyForecastList = new ArrayList<>();
            weeklyForecastAdapter = new WeatherRVAdapter(weeklyForecastList);
            weatherBinding.sevenDayForecastRecyclerView.setAdapter(weeklyForecastAdapter);

            if (weather.getHourly() != null) {
                for (WeatherObject weatherHourly : weather.getHourly()) {
                    dailyWeatherList.add(new WeatherRVModel(
                            weatherHourly.getTemperature(),
                            String.valueOf(weatherHourly.getWindSpeed()),
                            weatherHourly.getShortForecast()));
                }
                dailyWeatherAdapter.notifyDataSetChanged();
            }

            if (weather.getDaily() != null) {
                for (WeatherObject weatherDaily : weather.getDaily()) {
                    weeklyForecastList.add(new WeatherRVModel(
                            weatherDaily.getTemperature(),
                            String.valueOf(weatherDaily.getWindSpeed()),
                            weatherDaily.getShortForecast()));
                }
                weeklyForecastAdapter.notifyDataSetChanged();
            }
        }


        private String getCityNameFromZipCode(String zipCode) {
            String cityName = null;
            try {
                String url = "https://amtojk-tcss450-labs.herokuapp.com/geocode/zip?zip=" + zipCode;
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    JSONObject jsonObject = new JSONObject(response.toString());
                    cityName = jsonObject.getJSONObject("properties").getString("name");
                } else {
                    // Handle the error case
                }

                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cityName;
        }

        private void setUpCurrent(JSONObject response) {
            try {
                JSONObject properties = response.getJSONObject("properties");
                JSONObject currentConditions = properties.getJSONObject("currentConditions");
                String temperature = currentConditions.getString("temperature");

                // Extract the numeric part of the temperature string
                String numericTemperature = extractNumericTemperature(temperature);

                if (numericTemperature != null) {
                    // Set the extracted temperature value in the appropriate view
                    weatherBinding.tempText.setText(numericTemperature + "°");
                } else {
                    // Display a default value or handle the case when the temperature cannot be extracted
                    weatherBinding.tempText.setText("--");
                }

                // Rest of your code...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private String extractNumericTemperature(String temperatureString) {
            if (temperatureString != null && !temperatureString.isEmpty()) {
                StringBuilder numericTemperature = new StringBuilder();
                for (char c : temperatureString.toCharArray()) {
                    if (Character.isDigit(c) || c == '-') {
                        numericTemperature.append(c);
                    }
                }
                return numericTemperature.toString();
            }
            return null;
        }

    }


}