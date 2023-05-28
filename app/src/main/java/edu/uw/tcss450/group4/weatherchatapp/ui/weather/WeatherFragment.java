package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();

        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String zipCode = "98105"; // example zip code
        new WeatherRequestTask().execute(zipCode);

        FragmentWeatherBinding weatherBinding = FragmentWeatherBinding.bind(requireView());

        weatherBinding.dayAndCityText.setText("Tacoma, WA");
        weatherBinding.tempText.setText("-");
        weatherBinding.idTVShortForecast.setText("-");
        Drawable condIcon = ContextCompat.getDrawable(getActivity(), R.drawable.cloud);

        condIcon.setBounds(0, 0, 1, 1);

        weatherBinding.curIcon.setImageDrawable(condIcon);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        EditText inputBox = view.findViewById(R.id.inputBox);
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

            weatherBinding.tempText.setText(weather.getCurrent().getTemperature() + "°");
            weatherBinding.idTVShortForecast.setText(weather.getCurrent().getShortForecast());

            dailyWeatherList = new ArrayList<>();
            dailyWeatherAdapter = new WeatherRVAdapter(dailyWeatherList);
            weatherBinding.twentyFourHourForecastRecyclerView.setAdapter(dailyWeatherAdapter);

            weeklyForecastList = new ArrayList<>();
            weeklyForecastAdapter = new WeatherRVAdapter(weeklyForecastList);
            weatherBinding.sevenDayForecastRecyclerView.setAdapter(weeklyForecastAdapter);

            for (WeatherObject weatherHourly : weather.getHourly()) {
                dailyWeatherList.add(new WeatherRVModel(String.valueOf(weatherHourly.getTemperature()),
                        String.valueOf(weatherHourly.getWindSpeed()),
                        weatherHourly.getShortForecast()));
            }

            for (WeatherObject weatherDaily : weather.getDaily()) {
                weeklyForecastList.add(new WeatherRVModel(String.valueOf(weatherDaily.getTemperature()),
                        String.valueOf(weatherDaily.getWindSpeed()),
                        weatherDaily.getShortForecast()));
            }
        }
    }
}
