package edu.uw.tcss450.group4.weatherchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

import edu.uw.tcss450.group4.weatherchatapp.model.UserInfoViewModel;

/**
 * Class that is the main activity of the app, switching between the
 * various fragments and views.
 *
 * @author Chloe Duncan
 */
public class MainActivity extends AppCompatActivity {

    private UserInfoViewModel mUserInfoViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        String email = prefs.getString("email", "");
        String jwt = prefs.getString("jwt", "");

        UserInfoViewModel.UserInfoViewModelFactory factory =
                new UserInfoViewModel.UserInfoViewModelFactory(email, jwt);
        mUserInfoViewModel = new ViewModelProvider(this, factory).get(UserInfoViewModel.class);
    }


    /**
     * Method that creates an options menu.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_button_settings) {
            Log.d("App Bar Interact", "Settings");

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            navController.navigate(R.id.settingsFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Fragment getForegroundFragment(){
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        return navHostFragment == null ? null : navHostFragment.getChildFragmentManager().getFragments().get(0);
    }
}