package edu.uw.tcss450.group4.weatherchatapp.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.uw.tcss450.group4.weatherchatapp.R;

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

        mChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = mOldPasswordEditText.getText().toString();
                String newPassword = mNewPasswordEditText.getText().toString();
                String confirmPassword = mConfirmPasswordEditText.getText().toString();

                if (newPassword.equals(confirmPassword)) {
//                    String memberID = "1"; // TODO: Replace with actual memberID.
//                    String email = "user@example.com"; // TODO: Replace with actual email.
                    //changePassword(email, oldPassword, newPassword); // TODO: Uncomment when ready.
                    Toast.makeText(getContext(), "Change password works but password was not changed.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "New passwords do not match.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void changePassword(String email, String oldPassword, String newPassword) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://amtojk-tcss450-labs.herokuapp.com/changePW");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("email", email);
                    jsonParam.put("oldPassword", oldPassword);
                    jsonParam.put("newPassword", newPassword);

                    OutputStream os = conn.getOutputStream();
                    os.write(jsonParam.toString().getBytes("UTF-8"));
                    os.close();

                    // Get the response.
                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    StringBuilder sb = new StringBuilder();
                    String output;
                    while ((output = br.readLine()) != null) {
                        sb.append(output);
                    }

                    // Parse the response.
                    JSONObject jsonObj = new JSONObject(sb.toString());
                    String message = jsonObj.getString("message");

                    // Show a toast on the UI thread.
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}