package edu.uw.tcss450.group4.weatherchatapp.model;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

public class WeatherMapViewModel extends ViewModel {

    /**
     * Field for current Location of type MutableLiveData<Location>
     */
    private MutableLiveData<Location> mLocation;

    /**
     * Field for current Latitude/Longitude of type MutableLiveData<LatLng>
     */
    private MutableLiveData<LatLng> mLatLng;

    /**
     * Field for the ZIP Code of type MutableLiveData<String>
     */
    private MutableLiveData<String> mZipcode;

    /**
     * Constructor for the Location ViewModel. Initializes our fields.
     */
    public WeatherMapViewModel() {
        mLocation = new MediatorLiveData<>();
        mLatLng = new MediatorLiveData<>();
        mZipcode = new MediatorLiveData<>();
    }

    /**
     * Used to observe the specified location.
     * @param owner The lifecycle owner
     * @param observer The observer
     */
    public void addLocationObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super Location> observer) {
        mLocation.observe(owner, observer);
    }

    /**
     * Will be Used to observe the specified latitude & longitude.
     * @param owner The lifecycle owner
     * @param observer The observer
     */
    public void addLatLngObserver(@NonNull LifecycleOwner owner,
                                  @NonNull Observer<? super LatLng> observer) {
        mLatLng.observe(owner, observer);
    }

    /**
     * Will be Used to observe the specified ZIP Code.
     * @param owner The lifecycle owner
     * @param observer The observer
     */
    public void addZipcodeObserver(@NonNull LifecycleOwner owner,
                                   @NonNull Observer<? super String> observer) {
        mZipcode.observe(owner, observer);
    }

    /**
     * Sets the location.
     * @param location of type Location
     */
    public void setLocation(final Location location) {
        if (!location.equals(mLocation.getValue())) {
            mLocation.setValue(location);
        }
    }

    /**
     * @return the current location of type Location.
     */
    public Location getCurrentLocation() {
        return new Location(mLocation.getValue());
    }

    /**
     * Sets the latitude and longitude.
     * @param latlng of type LatLng
     */
    public void setLatLng(final LatLng latlng) {
        if (!latlng.equals(mLatLng.getValue())) {
            mLatLng.setValue(latlng);
        }
    }

    /**
     * @return the current latitude and longitude of type of type LatLng.
     */
    public LatLng getCurrentLatLng() {
        return new LatLng(mLatLng.getValue().latitude, mLatLng.getValue().longitude);
    }

    /**
     * Sets the ZIP Code.
     * @param zipcode of type String
     */
    public void setZipcode(final String zipcode) {
        if (!zipcode.equals(mZipcode.getValue())) {
            mZipcode.setValue(zipcode);
        }
    }

    /**
     * @return the current ZIP Code of type LiveData.
     */
    public String getCurrentZipcode() {
        return mZipcode.getValue();
    }

}


