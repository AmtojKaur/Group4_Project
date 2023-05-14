package edu.uw.tcss450.group4.weatherchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import edu.uw.tcss450.group4.weatherchatapp.R;

/**
 * Class that is the main activity of the app, switching between the
 * various fragments and views.
 *
 * @author Chloe Duncan
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bottom navigation bar
        BottomNavigationView navView = findViewById(R.id.bottom_nav);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_connections, R.id.navigation_chat, R.id.navigation_weather)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    /**
     * Method that creates an options menu.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    /**
     * Method that sets the menu items of the options menu
     * and handles action when an item is selected.
     * @param item is menu item in options menu.
     * @return true if selected item, false otherwise.
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_edit_profile:
                Log.d("EDIT_PROFILE", "Clicked");
                userSettings();
                return true;
            case R.id.action_settings:
                Log.d("SETTINGS", "Clicked");
                return true;
            case R.id.action_log_out:
                Log.d("LOG_OUT", "Clicked");
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Method that navigates user to user settings fragment when button is clicked in settings
     * tool bar.
     */
    private void userSettings() {
        // navigate to a settings fragment or other view type
        // NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // navController.navigate(R.id.nav_user_settings);
    }

    /**
     * Method that navigates user to log out fragment when button is clicked in settings
     * tool bar.
     */
    private void logOut() {
        // to be implemented
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}