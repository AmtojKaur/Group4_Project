package edu.uw.tcss450.group4.weatherchatapp;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * HomeWeatherFragment holds quick weather information that will be seen on the app home screen.
 *
 * @author Andrew Nguyen
 * @version 3 May 2023
 */
public class HomeWeatherFragment extends Fragment {

    private HomeWeatherViewModel mViewModel;

    /**
     * @return a new instance of HomeWeatherFragment.
     */
    public static HomeWeatherFragment newInstance() {
        return new HomeWeatherFragment();
    }

    /**
     * Called when this fragment is created.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return the View object for this fragment.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_home, container, false);
    }

    /**
     * Called when the activity for this fragment is created.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeWeatherViewModel.class);
        // TODO: Use the ViewModel
    }

}