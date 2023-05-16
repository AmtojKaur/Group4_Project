package edu.uw.tcss450.group4.weatherchatapp.ui.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentEmailVerificationBinding;
import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentRegisterBinding;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author Amtoj Kaur
 * @version 3 May 2023
 */
public class EmailVerificationFragment extends Fragment {

    private FragmentEmailVerificationBinding binding;
    private EmailVerificationViewModel mRegisterModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email_verification, container, false);
    }
}