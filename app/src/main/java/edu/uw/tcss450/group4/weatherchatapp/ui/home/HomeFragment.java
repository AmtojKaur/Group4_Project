package edu.uw.tcss450.group4.weatherchatapp.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.HomeFragmentBinding;

/**
 * HomeWeatherFragment holds the content fragments that will be seen on the home screen.
 *
 * @author Andrew Nguyen
 * @version 3 May 2023
 */
public class HomeFragment extends Fragment {

    private HomeViewModel mModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
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
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    /**
     * Called when the activity for this fragment is created.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     *
     */
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
//        // TODO: Use the ViewModel
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // view binding variable
        HomeFragmentBinding binding = HomeFragmentBinding.bind(getView());

        // Bottom Nav
        binding.buttonNavHome.setOnClickListener(button -> {
            // do nothing
            Log.d("Button Clicked", "Nav Home");
        });

        binding.buttonNavConnections.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Connections");
            Navigation.findNavController(getView()).navigate(
                    HomeFragmentDirections.actionNavigationHomeToNavigationConnections()
            );
        });

        binding.buttonNavChat.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Chat");
            Navigation.findNavController(getView()).navigate(
                    HomeFragmentDirections.actionNavigationHomeToNavigationChat()
            );
        });

        binding.buttonNavWeather.setOnClickListener(button -> {
            Log.d("Button Clicked", "Nav Weather");
            Navigation.findNavController(getView()).navigate(
                    HomeFragmentDirections.actionNavigationHomeToNavigationWeather()
            );
        });
    }
}