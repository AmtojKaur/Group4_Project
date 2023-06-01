package edu.uw.tcss450.group4.weatherchatapp.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import okhttp3.Request;

import java.io.IOException;

import edu.uw.tcss450.group4.weatherchatapp.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;


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

        View view = inflater.inflate(R.layout.auth_email_verification_fragment, container, false);

        mEmailEditText = view.findViewById(R.id.edit_email_verification);
        mVerifyButton = view.findViewById(R.id.button_verify);

        mVerifyButton.setOnClickListener(v -> {
            String email = mEmailEditText.getText().toString().trim();
            System.out.println("checking email: " + email);
            verifyEmail(email);
            System.out.println("email verification initiated");
        });

        return view;
    }

    private void verifyEmail(String email) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .build();

        Request request = new Request.Builder()
                .url("https://amtojk-tcss450-labs.herokuapp.com/auth/verify")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Error during email verification: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getActivity().runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        System.out.println("email verification successful");
                        Toast.makeText(getContext(), "Email verification initiated. Please check your email.", Toast.LENGTH_LONG).show();
                    } else {
                        System.out.println("email verification faile");
                        Toast.makeText(getContext(), "Email verification failed. Please try again.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}