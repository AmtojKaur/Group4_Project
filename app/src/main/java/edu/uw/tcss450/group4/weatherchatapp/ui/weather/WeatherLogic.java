package edu.uw.tcss450.group4.weatherchatapp.ui.weather;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WeatherLogic implements Serializable {
    private StringBuilder jsonString;
    private Map<String, Object> jsonMap;
    private WeatherObject current;
    private Map<String, WeatherObject> daily;
    private Map<String, WeatherObject> hourly;

    public WeatherLogic(StringBuilder jsonString) {
        this.jsonString = jsonString;
        this.jsonMap = jsonToMap(jsonString.toString());
        this.current = parseCurrent(this.jsonMap.get("current").toString());
        this.daily = parseDaily(this.jsonMap.get("daily").toString());
        this.hourly = parseHourly(this.jsonMap.get("hourly").toString());
    }


    private static Map<String, WeatherObject> parseHourly(String jsonString) {
        Map<String, WeatherObject> hourly = new HashMap<>();
        int counter = 0;
        while (jsonString.contains("dt=")) {

            String stringCopy = jsonString;

            String temp = stringCopy.substring(stringCopy.indexOf("temp=") + "temp=".length());
            temp = temp.substring(0, temp.indexOf(","));
            temp = "" + ((int) Double.parseDouble(temp));

            String description = stringCopy.substring(stringCopy.indexOf("description=") + "description=".length());
            description = description.substring(0, description.indexOf(","));

            String windSpeed = stringCopy.substring(stringCopy.indexOf("wind_speed=") + "wind_speed=".length());
            windSpeed = windSpeed.substring(0, windSpeed.indexOf(","));
            windSpeed = "" + ((int) Double.parseDouble(windSpeed));

            WeatherObject hourlyTemp = new WeatherObject(temp, description, windSpeed);
            hourly.put("" + counter++, hourlyTemp);

            jsonString = jsonString.substring(jsonString.indexOf("pop") + "pop".length());
        }
        return hourly;
    }

    private Map<String, WeatherObject> parseDaily(String jsonString) {
        Map<String, WeatherObject> daily = new HashMap<>();
        int counter = 0;
        while (jsonString.contains("dt=")) {

            String stringCopy = jsonString;

            String temp = stringCopy.substring(stringCopy.indexOf("day=") + "day=".length());
            temp = temp.substring(0, temp.indexOf(","));
            temp = "" + ((int) Double.parseDouble(temp));

            String description = stringCopy.substring(stringCopy.indexOf("description=") + "description=".length());
            description = description.substring(0, description.indexOf(","));

            String windSpeed = stringCopy.substring(stringCopy.indexOf("wind_speed=") + "wind_speed=".length());
            windSpeed = windSpeed.substring(0, windSpeed.indexOf(","));
            windSpeed = "" + ((int) Double.parseDouble(windSpeed));

            WeatherObject dailyTemp = new WeatherObject(temp, description, windSpeed);
            daily.put("" + counter++, dailyTemp);

            jsonString = jsonString.substring(jsonString.indexOf("uvi") + "uvi".length());
        }
        return daily;
    }


    private Map<String, Object> jsonToMap(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> map = gson.fromJson(json, type);
        return map;
    }
    private static WeatherObject parseCurrent(String current) {
        current = current.substring(1, current.length()-1);


        String temp = current.substring(current.indexOf("temp=") + "temp=".length());
        temp = temp.substring(0, temp.indexOf(","));
        temp = "" + ((int) Double.parseDouble(temp));

        String description = current.substring(current.indexOf("description=") + "description=".length());
        description = description.substring(0, description.indexOf(","));

        String windSpeed = current.substring(current.indexOf("wind_speed=") + "wind_speed=".length());
        windSpeed = windSpeed.substring(0, windSpeed.indexOf(","));

        WeatherObject daily = new WeatherObject(temp, description, windSpeed);
        return daily;
    }

    // Original methods from WeatherData class...
    // ...


    public String[] getForecastSpan() {
        int dayOfTheWeekVal;

        Date now = new Date();

        SimpleDateFormat dayOfTheWeekAsDate = new SimpleDateFormat("EEEE");
        String dayOfTheWeek = dayOfTheWeekAsDate.format(now);

        switch (dayOfTheWeek) {
            case "Monday":
                dayOfTheWeekVal = 0;
                break;
            case "Tuesday":
                dayOfTheWeekVal = 1;
                break;
            case "Wednesday":
                dayOfTheWeekVal = 2;
                break;
            case "Thursday":
                dayOfTheWeekVal = 3;
                break;
            case "Friday":
                dayOfTheWeekVal = 4;
                break;
            case "Saturday":
                dayOfTheWeekVal = 5;
                break;
            case "Sunday":
                dayOfTheWeekVal = 6;
                break;
            default:
                dayOfTheWeekVal = 0;
                break;
        }


        String[] daysOfTheWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        String[] upcomingDays = new String[4];

        for (int i = 1; i < 4; i++) {
            if (dayOfTheWeekVal + i > 6) {
                int temp = 6 % dayOfTheWeekVal;
                upcomingDays[i - 1] = daysOfTheWeek[0 + temp];
            } else {
                upcomingDays[i - 1] = daysOfTheWeek[dayOfTheWeekVal + i];
            }
        }
        return upcomingDays;
    }

    public WeatherObject getCurrent() {
        return this.current;
    }
    public Map<String, WeatherObject> getDaily() {

        return this.daily;
    }
    public Map<String, WeatherObject> getHourly() {
        return this.hourly;
    }
}
