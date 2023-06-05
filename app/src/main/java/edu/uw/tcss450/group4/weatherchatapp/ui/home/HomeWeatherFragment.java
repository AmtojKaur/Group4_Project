package edu.uw.tcss450.group4.weatherchatapp.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.ui.weather.WeatherRequest;

public class HomeWeatherFragment extends Fragment {

    private TextView mTemperatureTextView;
    private TextView mHighTemperatureTextView;
    private TextView mLowTemperatureTextView;

    private ImageView mWeatherIconImageView;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    private static final int PERMISSIONS_REQUEST_LOCATION = 100;

    public static HomeWeatherFragment newInstance() {
        return new HomeWeatherFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTemperatureTextView = view.findViewById(R.id.weather_current_temperature);
        mHighTemperatureTextView = view.findViewById(R.id.weather_high_temperature);
        mLowTemperatureTextView = view.findViewById(R.id.weather_low_temperature);
        mWeatherIconImageView = view.findViewById(R.id.weather_current_condition_img);
        mLocationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                // Convert latitude and longitude to zip code
                String zipCode = getZipCodeFromCoordinates(latitude, longitude);

                // Make the API request in a background thread
                new WeatherRequestTask().execute(zipCode);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        } else {
            // Request location permissions
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mLocationManager.removeUpdates(mLocationListener);
    }

    private String getZipCodeFromCoordinates(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (!addresses.isEmpty()) {
                String postalCode = addresses.get(0).getPostalCode();
                if (postalCode != null && !postalCode.isEmpty()) {
                    return postalCode;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    private class WeatherRequestTask extends AsyncTask<String, Void, StringBuilder> {

        @Override
        protected StringBuilder doInBackground(String... params) {
            String zipCode = params[0];
            return WeatherRequest.request(zipCode);
        }

        @Override
        protected void onPostExecute(StringBuilder response) {
            // Parse the response and update the UI
            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONObject properties = jsonObject.getJSONObject("properties");
                    JSONObject currentConditions = properties.getJSONObject("currentConditions");

                    String temperature = currentConditions.getString("temperature");
                    String highTemperature = currentConditions.getString("highTemperature");
                    String lowTemperature = currentConditions.getString("lowTemperature");

                    // Remove non-numeric character from temperature
                    temperature = temperature.replaceAll("[^0-9]", "");

                    mTemperatureTextView.setText(temperature + "° F");
                    mHighTemperatureTextView.setText(highTemperature + "° F");
                    mLowTemperatureTextView.setText(lowTemperature + "° F");

                    int tempValue = Integer.parseInt(temperature);

                    if (tempValue >= 80) {
                        // Set hot weather icon
                        mWeatherIconImageView.setImageResource(R.drawable.sunny_temperature_icon);
                    } else if (tempValue >= 60) {
                        // Set moderate weather icon
                        mWeatherIconImageView.setImageResource(R.drawable.sun_sunny_icon);
                    } else {
                        // Set cold weather icon
                        mWeatherIconImageView.setImageResource(R.drawable.clouds_cloudy_icon);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Handle error case
            }
        }
    }
}
