package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import edu.uw.tcss450.group4.weatherchatapp.model.WeatherMapViewModel;


/**
 * Fragment that stores information about the weather
 * @author Abdirizak Ali
 * @version 16May 2023
 */
public class WeatherMapFragment extends Fragment {

    public static double lat;
    public static double lon;
    //keep track of view
    View globalView;

    //testing current location
    public WeatherMapViewModel mModel;

    private FusedLocationProviderClient fusedLocationClient;

    String currentLoc = "location has not been set";

    //denote whether the user has selected c/f as their preference
    boolean celsius = false;

    //defining layout attributes to be linked to the existing xml attributes in fragment_weather.xml

    /**
     * defining layout attributes related to changing location
     */
    //button allow the user to select a search type
    View dropDown;
    //textbox where the user can enter the city/zip
    EditText inputBox;
    //button to allow the user to search the input they entered for city/zip
    Button searchbtn;

    /**
     * defining layout attributes related to current weather
     */
    //displays the day of the week as well as the city if it is gettable by the current search method
    //if not will display coordinates
    TextView dayAndCityText;
    //displays the weather current weather description to the user
    TextView descriptionText;
    //image view that shows the icon for the current weather conditions
    ImageView curIcon;
    //displays the current temperature
    TextView tempText;
    //displays the temp the current weather feels like
    TextView feelsLikeText;
    //displays the pressure of the current weather
    TextView pressureText;
    //displays the humidity of the current weather
    TextView humidityText;
    //displays the wind speed of the current weather
    TextView windText;


    //api key linked
    //private final String appid = "5d35717e8f7700ac945b1abc468129d0";

    //will be used to format temperature data retrieved from json
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
        View view = inflater.inflate(R.layout.fragment_weather, null, false);
        TextView tvResult = (TextView) view.findViewById(R.id.tvResult);
        return view;
        */
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        globalView = view;

        //set up search for zipcodes
        inputBox = view.findViewById(R.id.inputBox);
        inputBox.setVisibility(View.GONE);
        searchbtn = view.findViewById(R.id.searchbtn);
        searchbtn.setOnClickListener(this::getWeatherDetails);
        searchbtn.setVisibility(View.GONE);

        //testing geting images from internet into an imageview
        curIcon = view.findViewById(R.id.curIcon);
        String sampleIcon = "02d";
        String sampleIconUrl = "" + sampleIcon + ".png";
        String sample2 = "";
        Picasso.with(getContext()).load(sampleIconUrl).into(curIcon);

        //setup current weather attributes
        dayAndCityText = view.findViewById(R.id.dayAndCityText);
        descriptionText = view.findViewById(R.id.descriptionText);
        tempText = view.findViewById(R.id.tempText);
        feelsLikeText = view.findViewById(R.id.feelsLikeText);
        pressureText = view.findViewById(R.id.pressureText);
        humidityText = view.findViewById(R.id.humidityText);
        windText = view.findViewById(R.id.windText);

        //set up drop down menu so the user can change locations and choose a search type
        dropDown = view.findViewById(R.id.changeLocationButton);
        dropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), dropDown);

                popupMenu.getMenuInflater().inflate(R.menu.location_choice, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if (menuItem.getTitle().equals("Zipcode")) {
                            //display serach bar and search button to user
                            inputBox.setVisibility(View.VISIBLE);
                            searchbtn.setVisibility(View.VISIBLE);

                        } else if (menuItem.getTitle().equals("Select On Map")) {
                            navigateToSelectLocation();
                        } else if (menuItem.getTitle().equals("Current Location")) {
                            getCurrentLocation(view);
                        }


                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        double mapLat = WeatherMapFragment.lat;
        double mapLon = WeatherMapFragment.lon;
        System.out.println("from wf: " + mapLat + ", and: " + mapLon);

        if (mapLat != 0 && mapLon != 0){
            String mapLocation = mapLat + ":" + mapLon;
            connect(mapLocation);
        } else {
            //auto populate with weather data for current location, if unavailable populate for Tacoma
            getCurrentLocation(view);
        }

    }

    /**
     * Gets the current location of the device and populates the weather fragment with the data
     * @param view
     */
    public void getCurrentLocation(View view) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null) {
                    String lat = Double.toString(location.getLatitude());
                    String lon = Double.toString(location.getLongitude());
                    System.out.println("Perfect lat: "+ lat + ", lon: " + lon);
                    String latAndLon = lat+ ":" +lon;
                    connect(latAndLon);
                } else {
                    System.out.println("Location is NULL");
                    connect("98001");
                }
            }
        });
    }

    /**
     * Navigate to a google map
     */
    private void navigateToSelectLocation() {
        Navigation.findNavController(getView())
                .navigate(WeatherFragmentDirections.actionNavigationWeatherToWeatherMapFragment());
    }


    /**
     * Checks for valid user input and sends input to connect method
     * Used when searching by zipcode
     * @param view
     */
    public void getWeatherDetails(View view){

        //get zipcode from user input and construct first temp url
        String zip = inputBox.getText().toString().trim();
        if(zip.equals("")){
            //resultBox.setText("bro you forgot to enter the city");
            Toast.makeText(getActivity(), "You Must Enter a Zipcode", Toast.LENGTH_SHORT).show();
        } else {
            //call connect method with user input
            connect(zip);
            inputBox.setVisibility(View.GONE);
            searchbtn.setVisibility(View.GONE);
            //System.out.println("look its the enterd zip" + zip);//works!
        }
    }


    /**
     * makes call to webservice with the zipcode or coordinates given
     * @param zip
     */
    public void connect(String zip) {
        String webServiceUrl = getResources().getString(R.string.base_url_service)  + "weather/" + zip;
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                webServiceUrl,
                null, //no body needed!
                this::handleResult,
                this::handleError);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getActivity())
                .add(request);
    }

    /**
     * Gets Json returned from the web service and populates the fragment with all data
     * @param jsonResponse
     */
    public void handleResult(final JSONObject jsonResponse){
        try {
            //parse current weather details
            //get city name, if not seraching by zip no city name is availible so coordinates will be displayed
            String city = "";
            try{
                city = jsonResponse.getString("city");
            }catch (JSONException e) {
                float lat = jsonResponse.getInt("latitude");
                float lon = jsonResponse.getInt("longitude");
                String latDir = "";
                String lonDir = "";
                if(lat > 0){
                    latDir = lat + "°N";
                } else {
                    latDir = lat + "°S";
                }
                if(lon > 0){
                    lonDir = lon + "°E";
                } else {
                    lonDir = lon + "°W";
                }
                city = latDir + ", " + lonDir;
                System.out.println("Error city not found change locations: " + lat + ", " + lon);
            }

            //get temp in c
            double tempc = jsonResponse.getDouble("tempC");
            //get temp in f
            double tempf = jsonResponse.getDouble("tempF");
            //get feels like
            double feelsLike = jsonResponse.getDouble("feel");
            //get the pressure
            float pressure = jsonResponse.getInt("pressure");
            //get humidity
            int humidity = jsonResponse.getInt("humidity");
            //get wind speed
            int windSpeed = jsonResponse.getInt("windSpeed");

            //get the description and icon code of the current weather
            JSONArray description = jsonResponse.getJSONArray("description");
            JSONObject desZero = description.getJSONObject(0);
            String des = desZero.getString("description");
            String icon = desZero.getString("icon");

            //populate the ui with the current weather
            dayAndCityText.setText(city);
            descriptionText.setText(des);
            String iconUrl = "" + icon + ".png";
            Picasso.with(getContext()).load(iconUrl).into(curIcon);
            if(celsius){
                tempText.setText(df.format(tempc) + " °C");
            } else {
                tempText.setText(df.format(tempf) + " °F");
                feelsLike = 1.8 * (feelsLike - 273.15) + 32;
            }
            feelsLikeText.setText("Feels like:\n" + df.format(feelsLike) + "°F");
            pressureText.setText("Pressure:\n" + pressure + "hPa");
            humidityText.setText("Humidity:\n" + humidity + "%");
            windText.setText("Wind Speed:\n" + windSpeed + "m/s");

            //parse and set hourly details
            JSONArray hourlyArray = jsonResponse.getJSONArray("hourly");
            //set up hourly forecast scroll view
            LinearLayout linearLayout = globalView.findViewById(R.id.twentyFourHourForecastRecyclerView);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            linearLayout.removeAllViews();
            for(int i = 1; i <= 24; i++) {
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
                if (curHourWeatherDescription.equals("overcast clouds")){
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

            //parse and set daily details
            JSONArray dailyArray = jsonResponse.getJSONArray("daily");
            //set up daily forecast scroll view
            LinearLayout linearLayout2 = globalView.findViewById(R.id.sevenDayForecastRecyclerView);
            LayoutInflater inflater2 = LayoutInflater.from(getContext());
            linearLayout2.removeAllViews();
            for(int i = 1; i <=5; i++) {
                JSONObject curDayIndex = dailyArray.getJSONObject(i);
                String curDayString = curDayIndex.getString("dt");
                Date date = new Date(Long.parseLong(curDayString) * 1000);
                DateFormat format = new SimpleDateFormat("EEEE");
                String formattedDay = format.format(date);

                JSONObject tempOb = curDayIndex.getJSONObject("temp");
                double dayTempF = 1.8 * (tempOb.getDouble("day") - 273.15) +32;

                JSONArray dailyWeatherArray = curDayIndex.getJSONArray("weather");
                JSONObject dailyWeatherArrayIndex = dailyWeatherArray.getJSONObject(0);
                String dayDescription = dailyWeatherArrayIndex.getString("description");
                String dayIcon = dailyWeatherArrayIndex.getString("icon");


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Error handling when a call to the server fails.
     * Don't understand this and will properly implement as time allows
     *
     * @param error exception error that is returned when server call fails
     */
    private void handleError(final VolleyError error) {
        System.out.println("handle error was triggered");
        /*
        if (Objects.isNull(error.networkResponse)) {
            try {
                mResponse.setValue(new JSONObject("{" +
                        "error:\"" + error.getMessage() +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        }
        else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset())
                    .replace('\"', '\'');
            try {
                JSONObject response = new JSONObject();
                response.put("code", error.networkResponse.statusCode);
                response.put("data", new JSONObject(data));
                //mResponse.setValue(response);
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }


        }
        */
    }

}