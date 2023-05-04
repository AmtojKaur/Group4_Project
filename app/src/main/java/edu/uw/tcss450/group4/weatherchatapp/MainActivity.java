package edu.uw.tcss450.group4.weatherchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Class that is the main activity of the app, switching between the
 * various fragments and views.
 *
 * @author Chloe Duncan
 */
public class MainActivity extends AppCompatActivity {

    private View decorView;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RecyclerView recyclerView = findViewById(R.id.chat_recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        ChatListRecyclerViewAdapter adapter = new ChatListRecyclerViewAdapter(ChatGenerator.getChatList());
//        recyclerView.setAdapter(adapter);
//
//        findViewById(R.id.floating_action_button).setOnClickListener(view -> {
//            ChatGenerator.getChatList().add(ChatGenerator.addChat());
//            adapter.notifyItemInserted(ChatGenerator.getChatList().size() - 1);
//        });

        // hide system status bar and navigation bar
//        decorView = getWindow().getDecorView();
//        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
//            @Override
//            public void onSystemUiVisibilityChange(int visibility) {
//                if (visibility == 0) {
//                    decorView.setSystemUiVisibility(hideSystemBars());
//                }
//            }
//        });

        // bottom navigation bar
        BottomNavigationView navView = findViewById(R.id.bottom_nav);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_contacts, R.id.navigation_chat, R.id.navigation_weather)
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

    /**
     * Helper method that hides the system status bar
     * and system navigation bar.
     * If in focus, both bars will be shown visible.
     * @param hasFocus true if user clicks or swipes on screen.
     */
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            decorView.setSystemUiVisibility(hideSystemBars());
//        }
//    }

    /**
     * Helper method that hides the system status bar
     * and system navigation bar.
     * If in focus, both bars will be shown visible.
     */
     private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
}