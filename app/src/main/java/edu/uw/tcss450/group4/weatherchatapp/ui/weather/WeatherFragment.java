package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentWeatherBinding;

public class WeatherFragment extends Fragment {

    private ArrayList<WeatherRVModel> dailyWeatherList;
    private WeatherRVAdapter dailyWeatherAdapter;

    private ArrayList<WeatherRVModel> weeklyForecastList;
    private WeatherRVAdapter weeklyForecastAdapter;

    private EditText inputBox; // Declare the input box as a class variable

    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();

        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String currentZipCode = "98105"; // example zip code for current location
        new WeatherRequestTask().execute(currentZipCode);

        FragmentWeatherBinding weatherBinding = FragmentWeatherBinding.bind(requireView());

        weatherBinding.dayAndCityText.setText("Tacoma, WA"); // Default location for search
        weatherBinding.tempText.setText("-");
        weatherBinding.idTVShortForecast.setText("-");
        Drawable condIcon = ContextCompat.getDrawable(getActivity(), R.drawable.cloud);

        condIcon.setBounds(0, 0, 1, 1);

        weatherBinding.curIcon.setImageDrawable(condIcon);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        inputBox = view.findViewById(R.id.inputBox); // Assign the input box

        Button searchButton = view.findViewById(R.id.searchbtn);

        searchButton.setOnClickListener(v -> {
            String zipCode = inputBox.getText().toString().trim();
            if (!zipCode.matches("\\d{5}")) {
                Toast.makeText(getContext(), "Please enter a valid 5-digit zip code", Toast.LENGTH_SHORT).show();
                return;
            }
            new WeatherRequestTask().execute(zipCode);
        });

        return view;
    }

    private class WeatherRequestTask extends AsyncTask<String, Void, WeatherLogic> {

        @Override
        protected WeatherLogic doInBackground(String... params) {
            String zipCode = params[0];
            // API REQUEST
            String jsonString = String.valueOf(WeatherRequest.request(zipCode));
            return new WeatherLogic(jsonString);
        }

        protected void onPostExecute(WeatherLogic weather) {
            FragmentWeatherBinding weatherBinding = FragmentWeatherBinding.bind(requireView());

            WeatherObject currentWeather = weather.getCurrent();
            if (currentWeather != null) {
                String temperature = currentWeather.getTemperature();
                String shortForecast = currentWeather.getShortForecast();

                // Set the temperature and short forecast in the appropriate views
                weatherBinding.tempText.setText(String.valueOf(temperature));
                weatherBinding.idTVShortForecast.setText(shortForecast);
                ImageView weatherIconImageView = weatherBinding.curIcon;
                if (shortForecast.equals("Sunny")) {
                    weatherIconImageView.setImageResource(R.drawable.sunny_temperature_icon);
                } else if (shortForecast.equals("Cloudy")) {
                    weatherIconImageView.setImageResource(R.drawable.clouds_cloudy_icon);
                } else if (shortForecast.equals("Rainy")) {
                    weatherIconImageView.setImageResource(R.drawable.cloudy_forecast_rain_icon);
                }else if (shortForecast.equals("Clear")) {
                    weatherIconImageView.setImageResource(R.drawable.sunny_temperature_icon);
                } else if (shortForecast.equals("Mostly Clear")) {
                    weatherIconImageView.setImageResource(R.drawable.sun_sunny_icon);
                }else if (shortForecast.equals("Mostly Rainy")) {
                    weatherIconImageView.setImageResource(R.drawable.cloudy_rain_sunny_icon);
                } else if (shortForecast.equals("Mostly Sunny")) {
                    weatherIconImageView.setImageResource(R.drawable.sun_sunny_icon);
                }else if (shortForecast.equals("Haze")) {
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
                    dailyWeatherList.add(new WeatherRVModel(String.valueOf(weatherHourly.getTemperature()),
                            String.valueOf(weatherHourly.getWindSpeed()),
                            weatherHourly.getShortForecast()));
                }
                dailyWeatherAdapter.notifyDataSetChanged(); // Add this line
            }

            if (weather.getDaily() != null) {
                for (WeatherObject weatherDaily : weather.getDaily()) {
                    weeklyForecastList.add(new WeatherRVModel(String.valueOf(weatherDaily.getTemperature()),
                            String.valueOf(weatherDaily.getWindSpeed()),
                            weatherDaily.getShortForecast()));
                }
                weeklyForecastAdapter.notifyDataSetChanged(); // Add this line
            }

            // Set the location based on the search zip code
            if (inputBox != null) {
                String searchZipCode = inputBox.getText().toString().trim();
                weatherBinding.dayAndCityText.setText("Tacoma, WA"); // Default location
                if (searchZipCode.matches("\\d{5}")) {
                    weatherBinding.dayAndCityText.setText(searchZipCode); // Custom location
                }
            }
        }
    }
}