package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentWeatherBinding;

public class WeatherFragment extends Fragment {

    private ArrayList<WeatherRVModel> dailyWeatherList;
    private WeatherRVAdapter dailyWeatherAdapter;

    private ArrayList<WeatherRVModel> weeklyForecastList;
    private WeatherRVAdapter weeklyForecastAdapter;
    private StringBuilder jsonString;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();

        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        setContentView(R.layout.fragment_weather);
        String latitude = "47.258728";
        String longitude = "-122.465973";
        new WeatherRequestTask().execute(latitude, longitude);

        FragmentWeatherBinding weatherBinding = FragmentWeatherBinding.bind(requireView());
        /*
        // Initiate Daily Weather RV
        dailyWeatherList = new ArrayList<>();
        dailyWeatherAdapter = new WeatherRVAdapter(dailyWeatherList);
        weatherBinding.idRVWeather.setAdapter(dailyWeatherAdapter);

        // Initiate Weekly Weather RV
        weeklyForecastList = new ArrayList<>();
        weeklyForecastAdapter = new WeatherRVAdapter(weeklyForecastList);
        weatherBinding.idRVWeeklyWeather.setAdapter(weeklyForecastAdapter);*/

        // Display current data
        weatherBinding.dayAndCityText.setText("Tacoma, WA");
        weatherBinding.tempText.setText("-");
        weatherBinding.descriptionText.setText("-");
        Drawable condIcon = ContextCompat.getDrawable(getActivity(), R.drawable.cloud);

        condIcon.setBounds(0, 0, 1, 1);

        weatherBinding.curIcon.setImageDrawable(condIcon);
        //generateMockData(dailyWeatherList, weeklyForecastList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    private void generateMockData(ArrayList<WeatherRVModel> weatherRVModelArrayList,
                                  ArrayList<WeatherRVModel> weeklyForecastRVList) {
        Random rand = new Random();
        for (int i = 1; i < 12; i++) {
            int randTemp = rand.nextInt((70 - 55) + 1) + 55;;
            int randWind = rand.nextInt((6 - 0) + 1) + 0;;
            weatherRVModelArrayList.add(new WeatherRVModel(Integer.toString(i) + ":00 PM",
                    Integer.toString(randTemp), ContextCompat.getDrawable(getActivity(), R.drawable.cloud), Integer.toString(randWind)));

        }

        for (int i = 1; i < 8; i++) {
            int randTemp = rand.nextInt((70 - 55) + 1) + 55;;
            int randWind = rand.nextInt((6 - 0) + 1) + 0;;
            weeklyForecastRVList.add(new WeatherRVModel("5/"+Integer.toString(i)+"/2023",
                    Integer.toString(randTemp), ContextCompat.getDrawable(getActivity(), R.drawable.cloud), Integer.toString(randWind)));

        }
    }

    private int getIcon(String description) {
        if (description.contains("Partly Sunny")) {
            return R.drawable.cloud;
        } else if (description.contains("Sunny")) {
            return R.drawable.cloud;
        } else if (description.contains("Rain")) {
            return R.drawable.cloud;
        } else if (description.contains("Mostly Cloudy")){
            return R.drawable.cloud;
        } else if (description.contains("Patchy Drizzle")) {
            return R.drawable.cloud;
        } else if (description.contains("Snow")) {
            return R.drawable.cloud;
        }

        return R.drawable.cloud;
    }

    private class WeatherRequestTask extends AsyncTask<String, Void, StringBuilder> {

        @Override
        protected StringBuilder doInBackground(String... params) {
            String latitude = params[0];
            String longitude = params[1];
            FragmentWeatherBinding weatherBinding = FragmentWeatherBinding.bind(requireView());

            // API REQUEST
            jsonString = WeatherRequest.request(latitude, longitude);
            WeatherLogic weather = new WeatherLogic(jsonString);

            // Set current weather
            weatherBinding.tempText.setText(weather.getCurrent().getTemp() + "°");
            weatherBinding.descriptionText.setText(weather.getCurrent().getDescription());
            Drawable condIcon = ContextCompat.getDrawable(getActivity(), getIcon(weather.getCurrent().getDescription()));
            weatherBinding.curIcon.setImageDrawable(condIcon);

            return jsonString;
        }

        protected void onPostExecute(StringBuilder json) {
            WeatherLogic weather = new WeatherLogic(json);


            FragmentWeatherBinding weatherBinding = FragmentWeatherBinding.bind(requireView());
            dailyWeatherList = new ArrayList<>();
            dailyWeatherAdapter = new WeatherRVAdapter(dailyWeatherList);
            weatherBinding.twentyFourHourForecastRecyclerView.setAdapter(dailyWeatherAdapter);

            weeklyForecastList = new ArrayList<>();
            weeklyForecastAdapter = new WeatherRVAdapter(weeklyForecastList);
            weatherBinding.sevenDayForecastRecyclerView.setAdapter(weeklyForecastAdapter);

            Calendar calendar = Calendar.getInstance();
            for (int i = 0; i < 24; i++) {
                String meridiem = "";
                //if (now.get(Calendar.DAY_OF_MONTH) != calendar.get(Calendar.DAY_OF_MONTH)) break;
                if (calendar.get(Calendar.HOUR_OF_DAY) >= 12) {
                    meridiem = "PM";
                } else {
                    meridiem = "AM";
                }
                String time = calendar.get(Calendar.HOUR) + ":00";
                if (time.equals("0:00")) time = "12:00";

                dailyWeatherList.add(new WeatherRVModel( time + meridiem,
                        weather.getHourly().get("" + i).getTemp(),
                        ContextCompat.getDrawable(getActivity(), getIcon(weather.getHourly().get("" + i).getDescription())),
                        weather.getHourly().get("" + i).getWindSpeed()));

                calendar.add(Calendar.HOUR_OF_DAY, 1);
            }

            calendar = Calendar.getInstance();

            for (int i = 0; i < 5; i++) {
                weeklyForecastList.add(new WeatherRVModel( "" + (calendar.get(Calendar.MONTH) + 1) + "/"
                        + calendar.get(Calendar.DAY_OF_MONTH),
                        weather.getDaily().get("" + i).getTemp(),
                        ContextCompat.getDrawable(getActivity(), getIcon(weather.getDaily().get("" + i).getDescription())),
                        weather.getDaily().get("" + i).getWindSpeed()));

                calendar.add(Calendar.DAY_OF_MONTH,1);
            }


        }
    }
}