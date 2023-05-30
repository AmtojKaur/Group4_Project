package edu.uw.tcss450.group4.weatherchatapp.ui.signin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentForgotPasswordBinding;

/**
 * @author AJ Garcia
 * @version 25 May 2023
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment {
    private FragmentForgotPasswordBinding binding;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSendResetEmail.setOnClickListener(this::sendResetEmail);
    }

    private void sendResetEmail(final View button) {
        String email = binding.editEmail.getText().toString().trim();
        // Validate the email exists
        if (emailIsValid(email)) {
            String url = "https://amtojk-tcss450-labs.herokuapp.com/passwordReset";

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("email", email);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Send the request to server
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        // pop up box saying email sent
                        public void onResponse(JSONObject response) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Success")
                                    .setMessage("Please check your email for sign in instructions.")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .show()
                                    .setOnDismissListener(dialog -> {
                                        Navigation.findNavController(button).navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToSignInFragment());
                                    });
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(jsonObjectRequest);
        }
    }

    private boolean emailIsValid(String email) {
        // TODO: make sure the email exists in the database
        return true;
    }

}