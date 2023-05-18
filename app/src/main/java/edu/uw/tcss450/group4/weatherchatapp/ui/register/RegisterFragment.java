package edu.uw.tcss450.group4.weatherchatapp.ui.register;

import static edu.uw.tcss450.group4.weatherchatapp.ui.connections.utils.PasswordValidator.checkClientPredicate;
import static edu.uw.tcss450.group4.weatherchatapp.ui.connections.utils.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450.group4.weatherchatapp.ui.connections.utils.PasswordValidator.checkPwdDigit;
import static edu.uw.tcss450.group4.weatherchatapp.ui.connections.utils.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450.group4.weatherchatapp.ui.connections.utils.PasswordValidator.checkPwdLowerCase;
import static edu.uw.tcss450.group4.weatherchatapp.ui.connections.utils.PasswordValidator.checkPwdSpecialChar;
import static edu.uw.tcss450.group4.weatherchatapp.ui.connections.utils.PasswordValidator.checkPwdUpperCase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.appcompat.widget.AppCompatButton;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.group4.weatherchatapp.databinding.FragmentRegisterBinding;
import edu.uw.tcss450.group4.weatherchatapp.ui.connections.utils.PasswordValidator;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author Amtoj Kaur
 * @version 3 May 2023
 */
public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    private RegisterViewModel mRegisterModel;
    private PasswordValidator mNameValidator = checkPwdLength(1);

    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    private PasswordValidator mPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(binding.editPassword2.getText().toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));
    private PasswordValidator mPassWordValidatorMatch =
            checkClientPredicate(pwd -> pwd.equals(binding.editPassword2.getText().toString()));

    private PasswordValidator mPassWordValidatorLen = checkPwdLength(7);

    private PasswordValidator mPasswordValidatorSpecial = checkPwdSpecialChar();

    private PasswordValidator mPassWordValidatorSpace = checkExcludeWhiteSpace();

    private PasswordValidator mPassWordValidatorDigit = checkPwdDigit();

    private PasswordValidator mPassWordValidatorCaps = checkPwdLowerCase().or(checkPwdUpperCase());



    /**
     * Empty public constructor
     */
    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegisterModel = new ViewModelProvider(getActivity())
                .get(RegisterViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonRegister.setOnClickListener(this::attemptRegister);
        mRegisterModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }

    /**
     * Method that calls helper method to validate user's first name.
     * @param button the button pressed to register
     */
    private void attemptRegister(final View button) {
        validateFirst();
    }

    /**
     * Method that validates user's first name.
     */
    private void validateFirst() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.editFirst.getText().toString().trim()),
                this::validateLast,
                result -> binding.editFirst.setError("Please enter a first name."));
    }

    /**
     * Method that validates user's last name.
     */
    private void validateLast() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.editLast.getText().toString().trim()),
                this::validateEmail,
                result -> binding.editLast.setError("Please enter a last name."));
    }

    /**
     * Method that validates user's email address.
     */
    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.editEmail.getText().toString().trim()),
                this::validatePasswordLen,
                result -> binding.editEmail.setError("Please enter a valid Email address."));
    }

    /**
     * Method that validates user's password length.
     */
    private void validatePasswordLen() {
        mPassWordValidatorLen.processResult(
                mPassWordValidatorLen.apply(binding.editPassword1.getText().toString()),
                this::validatePasswordSpecial,
                result -> binding.editPassword1.setError("Please enter more than 6 characters."));
    }

    /**
     * Method that validates user's password contains special character.
     */
    private void validatePasswordSpecial() {
        mPasswordValidatorSpecial.processResult(
                mPasswordValidatorSpecial.apply(binding.editPassword1.getText().toString()),
                this::validatePasswordSpace,
                result -> binding.editPassword1.setError("Please enter a special character."));
    }

    /**
     * Method that validates user's password does not contain space.
     */
    private void validatePasswordSpace() {
        mPassWordValidatorSpace.processResult(
                mPassWordValidatorSpace.apply(binding.editPassword1.getText().toString()),
                this::validatePasswordDigit,
                result -> binding.editPassword1.setError("Please make sure there is no space."));
    }

    /**
     * Method that validates user's password does not contain space.
     */
    private void validatePasswordDigit() {
        mPassWordValidatorDigit.processResult(
                mPassWordValidatorDigit.apply(binding.editPassword1.getText().toString()),
                this::validatePasswordCaps,
                result -> binding.editPassword1.setError("Password must contain at least one number."));
    }
    /**
     * Method that validates user's password does not contain space.
     */
    private void validatePasswordCaps() {
        mPassWordValidatorCaps.processResult(
                mPassWordValidatorCaps.apply(binding.editPassword1.getText().toString()),
                this::validatePasswordsMatch,
                result -> binding.editPassword1.setError("Password must contain capital letter."));
    }

    /**
     * Method that validates if the user's passwords match.
     */
    private void validatePasswordsMatch() {
        PasswordValidator matchValidator =
                checkClientPredicate(
                        pwd -> pwd.equals(binding.editPassword2.getText().toString().trim()));

        mEmailValidator.processResult(
                matchValidator.apply(binding.editPassword1.getText().toString().trim()),
                this::verifyAuthWithServer,
                result -> binding.editPassword1.setError("Passwords must match."));
    }

    /**
     * Method that validates user's password.
     *//*
    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.editPassword1.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.editPassword1.setError("Please enter a valid Password."));
    }*/

    /**
     * Method that sends email and password to register view model.
     */
    private void verifyAuthWithServer() {
        mRegisterModel.connect( binding.editFirst.getText().toString(),
                binding.editLast.getText().toString(),
                binding.editEmail.getText().toString(),
                binding.editPassword1.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        // result of connect().
    }

    /**
     * Method that navigates from the register to log in fragment, once register
     * is successful.
     */
    private void navigateToLogin() {
        RegisterFragmentDirections.ActionRegisterFragmentToSignInFragment directions =
                RegisterFragmentDirections.actionRegisterFragmentToSignInFragment();

        directions.setEmail(binding.editEmail.getText().toString());
        directions.setPassword(binding.editPassword1.getText().toString());

        Navigation.findNavController(getView()).navigate(directions);

    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to SignInViewModel.
     *
     * @param response the Response from the server
     */
    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.editEmail.setError(
                            "Error Authenticating: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                // Show a popup here
                new AlertDialog.Builder(getContext())
                        .setTitle("Registration Successful")
                        .setMessage("Registration was successful! Please check your email for a verification link.")
                        .setPositiveButton(android.R.string.ok, (dialog, which) -> navigateToLogin())
                        .show();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}