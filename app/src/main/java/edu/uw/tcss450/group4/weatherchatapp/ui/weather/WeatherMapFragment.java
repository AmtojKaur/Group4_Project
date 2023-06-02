package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import edu.uw.tcss450.group4.weatherchatapp.R;

public class WeatherMapFragment extends Fragment {

    public static double lat;
    public static double lon;
    View globalView;

    // Attributes related to changing location
    View dropDown;
    EditText inputBox;
    Button searchbtn;

    // Attributes related to current weather
    TextView dayAndCityText;
    TextView descriptionText;
    TextView tempText;
    TextView feelsLikeText;
    TextView pressureText;
    TextView humidityText;
    TextView windText;
    ImageView curIcon;

    DecimalFormat df = new DecimalFormat("#.##");
    private boolean celsius;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        globalView = view;

        // Set up search for zip codes
        inputBox = view.findViewById(R.id.inputBox);
        inputBox.setVisibility(View.GONE);
        searchbtn = view.findViewById(R.id.searchbtn);
        searchbtn.setOnClickListener(this::getWeatherDetails);
        searchbtn.setVisibility(View.GONE);

        curIcon = view.findViewById(R.id.curIcon);

        dayAndCityText = view.findViewById(R.id.dayAndCityText);
        descriptionText = view.findViewById(R.id.idTVShortForecast);
        tempText = view.findViewById(R.id.tempText);


        dropDown = view.findViewById(R.id.changeLocationButton);
        dropDown.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getActivity(), dropDown);
            popupMenu.getMenuInflater().inflate(R.menu.location_choice, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getTitle().equals("Zipcode")) {
                    inputBox.setVisibility(View.VISIBLE);
                    searchbtn.setVisibility(View.VISIBLE);
                } else if (item.getTitle().equals("Select On Map")) {
                    navigateToSelectLocation();
                } else if (item.getTitle().equals("Current Location")) {
                    getCurrentLocation();
                }
                return true;
            });
            popupMenu.show();
        });

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        double mapLat = WeatherMapFragment.lat;
        double mapLon = WeatherMapFragment.lon;
        System.out.println("from wf: " + mapLat + ", and: " + mapLon);

        if (mapLat != 0 && mapLon != 0) {
            String mapLocation = mapLat + ":" + mapLon;
            connect(mapLocation);
        } else {
            getCurrentLocation();
        }
    }

    public void getCurrentLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    String lat = Double.toString(location.getLatitude());
                    String lon = Double.toString(location.getLongitude());
                    System.out.println("Perfect lat: " + lat + ", lon: " + lon);
                    String latAndLon = lat + ":" + lon;
                    connect(latAndLon);
                } else {
                    System.out.println("Location is NULL");
                    connect("98001");
                }
            }
        });
    }

    private void navigateToSelectLocation() {
        Navigation.findNavController(getView())
                .navigate(WeatherFragmentDirections.actionNavigationWeatherToWeatherMapFragment());
    }

    public void getWeatherDetails(View view) {
        String zip = inputBox.getText().toString().trim();
        if (zip.equals("")) {
            Toast.makeText(getActivity(), "You Must Enter a Zipcode", Toast.LENGTH_SHORT).show();
        } else {
            connect(zip);
            inputBox.setVisibility(View.GONE);
            searchbtn.setVisibility(View.GONE);
        }
    }

    public void connect(String zip) {
        String webServiceUrl = getResources().getString(R.string.base_url_service) + "weather/" + zip;
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                webServiceUrl,
                null,
                this::handleResult,
                this::handleError);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity())
                .add(request);
    }

    public void handleResult(final JSONObject jsonResponse) {
        try {
            String city = "";
            try {
                city = jsonResponse.getString("city");
            } catch (JSONException e) {
                float lat = jsonResponse.getInt("latitude");
                float lon = jsonResponse.getInt("longitude");
                String latDir = "";
                String lonDir = "";
                if (lat > 0) {
                    latDir = lat + "°N";
                } else {
                    latDir = lat + "°S";
                }
                if (lon > 0) {
                    lonDir = lon + "°E";
                } else {
                    lonDir = lon + "°W";
                }
                city = latDir + ", " + lonDir;
                System.out.println("Error city not found change locations: " + lat + ", " + lon);
            }

            double tempc = jsonResponse.getDouble("tempC");
            double tempf = jsonResponse.getDouble("tempF");
            double feelsLike = jsonResponse.getDouble("feel");
            float pressure = jsonResponse.getInt("pressure");
            int humidity = jsonResponse.getInt("humidity");
            int windSpeed = jsonResponse.getInt("windSpeed");

            JSONArray description = jsonResponse.getJSONArray("description");
            JSONObject desZero = description.getJSONObject(0);
            String des = desZero.getString("description");
            String icon = desZero.getString("icon");

            dayAndCityText.setText(city);
            descriptionText.setText(des);
            String iconUrl = "" + icon + ".png";
            Picasso.with(getContext()).load(iconUrl).into(curIcon);
            if (celsius) {
                tempText.setText(df.format(tempc) + " °C");
            } else {
                tempText.setText(df.format(tempf) + " °F");
                feelsLike = 1.8 * (feelsLike - 273.15) + 32;
            }
            feelsLikeText.setText("Feels like:\n" + df.format(feelsLike) + "°F");
            pressureText.setText("Pressure:\n" + pressure + "hPa");
            humidityText.setText("Humidity:\n" + humidity + "%");
            windText.setText("Wind Speed:\n" + windSpeed + "m/s");

            JSONArray hourlyArray = jsonResponse.getJSONArray("hourly");
            LinearLayout linearLayout = globalView.findViewById(R.id.twentyFourHourForecastRecyclerView);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            linearLayout.removeAllViews();
            for (int i = 1; i <= 24; i++) {
                JSONObject curHourIndex = hourlyArray.getJSONObject(i);
                String curHourString = curHourIndex.getString("dt");
                Date date = new Date(Long.parseLong(curHourString) * 1000);
                DateFormat format = new SimpleDateFormat("ha");
                format.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
                String formattedDate = format.format(date);

                double curTemp = curHourIndex.getDouble("temp");
                double curTempF = 1.8 * (curHourIndex.getDouble("temp") - 273.15) + 32;
                JSONArray curHourWeatherArray = curHourIndex.getJSONArray("weather");
                JSONObject curHourWeatherArrayIndex = curHourWeatherArray.getJSONObject(0);
                String curHourWeatherDescription = curHourWeatherArrayIndex.getString("description");
                if (curHourWeatherDescription.equals("overcast clouds")) {
                    curHourWeatherDescription = "Overcast  clouds";
                }
                String curHourWeatherIcon = curHourWeatherArrayIndex.getString("icon");

                View view2 = inflater.inflate(R.layout.hourly_forecast_item, linearLayout, false);
                TextView textView = view2.findViewById(R.id.idTVTime);
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.color_blue1));
                textView.setText(formattedDate + " " + df.format(curTempF) + "°F" + "\n" + curHourWeatherDescription);
                String hourlyIconUrl = "" + curHourWeatherIcon + ".png";
                ImageView imageView = view2.findViewById(R.id.idTVTime);
                Picasso.with(getContext()).load(hourlyIconUrl).into(imageView);

                linearLayout.addView(view2);
            }

            JSONArray dailyArray = jsonResponse.getJSONArray("daily");
            LinearLayout linearLayout2 = globalView.findViewById(R.id.sevenDayForecastRecyclerView);
            LayoutInflater inflater2 = LayoutInflater.from(getContext());
            linearLayout2.removeAllViews();
            for (int i = 1; i <= 5; i++) {
                JSONObject curDayIndex = dailyArray.getJSONObject(i);
                String curDayString = curDayIndex.getString("dt");
                Date date = new Date(Long.parseLong(curDayString) * 1000);
                DateFormat format = new SimpleDateFormat("EEEE");
                String formattedDay = format.format(date);

                JSONObject tempOb = curDayIndex.getJSONObject("temp");
                double dayTempF = 1.8 * (tempOb.getDouble("day") - 273.15) + 32;

                JSONArray dailyWeatherArray = curDayIndex.getJSONArray("weather");
                JSONObject dailyWeatherArrayIndex = dailyWeatherArray.getJSONObject(0);
                String dayDescription = dailyWeatherArrayIndex.getString("description");
                String dayIcon = dailyWeatherArrayIndex.getString("icon");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleError(final VolleyError error) {
        System.out.println("handle error was triggered");
    }
}
