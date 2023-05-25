package edu.uw.tcss450.group4.weatherchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.ui.home.HomeFragment;
import edu.uw.tcss450.group4.weatherchatapp.ui.home.HomeFragmentDirections;
import edu.uw.tcss450.group4.weatherchatapp.ui.settings.SettingsFragment;

/**
 * Class that is the main activity of the app, switching between the
 * various fragments and views.
 *
 * @author Chloe Duncan
 */
public class MainActivity extends AppCompatActivity {

    public boolean settings = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Method that creates an options menu.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_button_settings) {
            Log.d("App Bar Interact", "Settings");

            //Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.container);

            if (getForegroundFragment() instanceof HomeFragment) {
                Navigation.findNavController(this,R.id.constraintLayout).navigate(
                        HomeFragmentDirections.actionNavigationHomeToNavigationChat()
                        );
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public Fragment getForegroundFragment(){
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        return navHostFragment == null ? null : navHostFragment.getChildFragmentManager().getFragments().get(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean getSettings() {
        return settings;
    }
}