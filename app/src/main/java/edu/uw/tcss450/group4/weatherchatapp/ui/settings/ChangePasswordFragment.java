package edu.uw.tcss450.group4.weatherchatapp.ui.settings;

import android.app.Application;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.model.UserInfoViewModel;

/**
 * @Author: AJ Garcia
 * @version 1 JUN 2023
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {

    private EditText mOldPasswordEditText;
    private EditText mNewPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private Button mChangePasswordButton;
    private UserInfoViewModel mUserInfoViewModel;
    private MutableLiveData<JSONObject> mResponse;
    private ChangePasswordViewModel mChangePasswordViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mOldPasswordEditText = view.findViewById(R.id.edit_old_password);
        mNewPasswordEditText = view.findViewById(R.id.edit_new_password);
        mConfirmPasswordEditText = view.findViewById(R.id.edit_confirm_password);
        mChangePasswordButton = view.findViewById(R.id.button_change_password);
        mUserInfoViewModel = new ViewModelProvider(requireActivity()).get(UserInfoViewModel.class);
        mChangePasswordViewModel = new ViewModelProvider(requireActivity()).get(ChangePasswordViewModel.class);
        mChangePasswordViewModel.addResponseObserver(getViewLifecycleOwner(), this::observeResponse);

        mChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = mOldPasswordEditText.getText().toString();
                String newPassword = mNewPasswordEditText.getText().toString();
                String confirmPassword = mConfirmPasswordEditText.getText().toString();

                if (newPassword.equals(confirmPassword)) {
                    String email = mUserInfoViewModel.getEmail();
                    changePassword(email, oldPassword, newPassword);
                } else {
                    Toast.makeText(getContext(), "New passwords do not match.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        mChangePasswordViewModel.connect(email, oldPassword, newPassword);
    }

    private void observeResponse(JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    String errorMessage = response.getJSONObject("data").getString("message");

                    switch(errorMessage) {
                        case "Missing fields.":
                            showErrorDialog("Error", "Please fill in all fields.");
                            break;
                        case "Incorrect password":
                            showErrorDialog("Error", errorMessage);
                            break;
                        case "No user found with that email":
                            showErrorDialog("Error", errorMessage);
                            break;
                        default:
                            // Server errors
                            showErrorDialog("Error", errorMessage);
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                new AlertDialog.Builder(getContext())
                        .setTitle("Success")
                        .setMessage("Password changed successfully")
                        .setPositiveButton(android.R.string.ok, null)
                        //.setPositiveButton(android.R.string.ok, (dialog, which) -> navigateToHome())
                        .show();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }

    private void showErrorDialog(String title, String message) {
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}