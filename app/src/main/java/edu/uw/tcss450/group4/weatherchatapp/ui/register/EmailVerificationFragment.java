package edu.uw.tcss450.group4.weatherchatapp.ui.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.uw.tcss450.group4.weatherchatapp.R;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author Amtoj Kaur
 * @version 3 May 2023
 */
public class EmailVerificationFragment extends Fragment {

    private EditText mEmailEditText;
    private Button mVerifyButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_email_verification, container, false);

        mEmailEditText = view.findViewById(R.id.edit_email_verification);
        mVerifyButton = view.findViewById(R.id.button_verify);

        mVerifyButton.setOnClickListener(v -> {
            String email = mEmailEditText.getText().toString().trim();
            verifyEmail(email);
        });

        return view;
    }

    private void verifyEmail(String email) {
        // TODO: Implement email verification logic using Heroku

        // TODO: make a POST request to Heroku API

        // TODO: make a Toast message to display the result of the POST request


    }

}