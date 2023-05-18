package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentWeatherMapBinding;
import edu.uw.tcss450.group4.weatherchatapp.model.WeatherMapViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * A fragment that's used to display a Google Map and allows for markers to be placed.
 */
public class WeatherMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    /**
     * Binding object for the Location fragment.
     */
    private FragmentWeatherMapBinding binding;

    /**
     * ViewModel object for the Location fragment.
     */
    private WeatherMapViewModel mModel;

    /**
     * A field for the Google Map object for use within the Location fragment.
     */
    private GoogleMap mMap;

    /**
     * A field for the Marker object to get the user-specified location on the Map.
     */
    private Marker mMarker;

    /**
     * Required empty public constructor.
     */
    public WeatherMapFragment() {
    }

    /**
     * Generates and assigns a view binding for the Location layout.
     * @param inflater The LayoutInflater
     * @param container The ViewGroup
     * @param savedInstanceState The data of the UI state
     * @return a ConstraintLayout based on the associated XML class for the Location fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWeatherMapBinding.inflate(inflater);
        return binding.getRoot();
    }

    /**
     * Here, the Location fragment observes for interaction from the user.
     * @param view The View
     * @param savedInstanceState The data of the UI state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mModel = new ViewModelProvider(getActivity())
                .get(WeatherMapViewModel.class);
        mModel.addLocationObserver(getViewLifecycleOwner(), location ->
                binding.textLatLong.setText(location.toString()));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        //add this fragment as the OnMapReadyCallback -> See onMapReady()
        mapFragment.getMapAsync(this);
    }

    /**
     * Here, the Google Map is observing for user activity.
     * @param googleMap The Google Map
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        WeatherMapViewModel model = new ViewModelProvider(getActivity())
                .get(WeatherMapViewModel.class);
        model.addLocationObserver(getViewLifecycleOwner(), location -> {
            if(location != null) {
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setMyLocationEnabled(true);

                final LatLng c = new LatLng(location.getLatitude(), location.getLongitude());
                //Zoom levels are from 2.0f (zoomed out) to 21.f (zoomed in)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(c, 15.0f));
            }
        });
        mMap.setOnMapClickListener(this);
    }


    /**
     * Applies a Marker to the Google Map upon user's tap, then records the location.
     * @param latLng Latitude & Longitude
     */
    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        mMap.clear();
        if (mMarker!=null) {
            mMarker.remove();
            mMarker=null;
        }
        Log.d("LAT/LONG", latLng.toString());
        mMarker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("New Marker"));

        binding.textLatLong.setText(latLng.toString());


        mModel.setLatLng(latLng);
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                        latLng, mMap.getCameraPosition().zoom));



    }
}