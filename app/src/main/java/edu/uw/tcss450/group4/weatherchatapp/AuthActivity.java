package edu.uw.tcss450.group4.weatherchatapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Class that is used to hold fragments related to login and sign up.
 *
 * @author Amtoj Kaur
 * @version 3 May 2023
 */
public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }
}