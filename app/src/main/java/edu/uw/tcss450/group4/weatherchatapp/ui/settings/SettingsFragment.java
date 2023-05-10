package edu.uw.tcss450.group4.weatherchatapp.ui.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import edu.uw.tcss450.group4.weatherchatapp.R;

/**
 * SettingsFragment is the fragment where the user sets their preferences.
 *
 * @author Andrew Nguyen
 * @version 9 May 2023
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    /**
     * Called on creation.
     * @param savedInstanceState If the fragment is being re-created from a previous saved state,
     *                           this is the state.
     * @param rootKey            If non-null, this preference fragment should be rooted at the
     *                           {@link PreferenceScreen} with this key.
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}