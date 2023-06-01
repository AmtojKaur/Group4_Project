package edu.uw.tcss450.group4.weatherchatapp.ui.weather;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author Abdirizak Ali
 * @version 16 May 2023
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;

import edu.uw.tcss450.group4.weatherchatapp.R;

public class WeatherAddFragment extends Fragment {

    public WeatherAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_add, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonWeather = view.findViewById(R.id.button_weather);
        buttonWeather.setOnClickListener(v -> {
            String zipCode = getZipCodeFromInput();

            // Validate zip code
            if (isValidZipCode(zipCode)) {
                Bundle bundle = new Bundle();
                bundle.putString("zipCode", zipCode);

                // Navigate to WeatherFragment using the generated NavDirections
                NavDirections action = WeatherAddFragmentDirections.actionWeatherAddFragmentToNavigationWeather();
                Navigation.findNavController(view).navigate(action.getActionId(), bundle);
            }
        });
    }


    private String getZipCodeFromInput() {
        String zipCode = "";

        // Retrieve the entered zip code from the TextInputEditText
        View view = getView();
        if (view != null) {
            TextInputEditText editLocation = view.findViewById(R.id.edit_location);
            if (editLocation != null) {
                zipCode = editLocation.getText().toString().trim();
            }
        }

        return zipCode;
    }

    private boolean isValidZipCode(String zipCode) {
        // Validate the zip code (e.g., check length, format, etc.)
        return zipCode.matches("\\d{5}");
    }
}

