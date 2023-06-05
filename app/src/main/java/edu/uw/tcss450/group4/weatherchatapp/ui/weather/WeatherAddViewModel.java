package edu.uw.tcss450.group4.weatherchatapp.ui.weather;
/**
 *  @author Abdirizak Ali
 *  @version 16 May 2023
 */
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class WeatherAddViewModel extends ViewModel {

    private MutableLiveData<List<WeatherAddItem>> weatherItemsLiveData;

    public WeatherAddViewModel() {
        weatherItemsLiveData = new MutableLiveData<>();
        weatherItemsLiveData.setValue(new ArrayList<>());
    }

    public LiveData<List<WeatherAddItem>> getWeatherItems() {
        return weatherItemsLiveData;
    }

    public void addWeatherItem(WeatherAddItem item) {
        List<WeatherAddItem> currentItems = weatherItemsLiveData.getValue();
        if (currentItems != null) {
            currentItems.add(item);
            weatherItemsLiveData.setValue(currentItems);
        }
    }
}