package edu.uw.tcss450.group4.weatherchatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_edit_profile:
                // editProfile();
                Log.d("EDIT_PROFILE", "Clicked");
                return true;
            case R.id.action_settings:
                // openSettings();
                Log.d("SETTINGS", "Clicked");
                return true;
            case R.id.action_log_out:
                // logOut();
                Log.d("LOG_OUT", "Clicked");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}