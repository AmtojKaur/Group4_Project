package edu.uw.tcss450.group4.weatherchatapp.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Class that prepares and manages data associated with the user's app login and register data.
 *
 * @author Amtoj Kaur
 * @version 3 May 2023
 */
public class UserInfoViewModel extends ViewModel {
    private static String mUserEmail;
    private final String mJwt;

    private static int mUserID;

    /**
     * Private constructor that initializes user email and jwt.
     * @param email the user's email address
     * @param jwt the String where the email is stored
     */
    private UserInfoViewModel(String email, String jwt) {
        mUserEmail = email;
        mJwt = jwt;
    }

    public static void setUserEmail(String email) {
        mUserEmail = email;
    }

    public static void setUserID(int id) {
        mUserID = id;
    }

    /**
     * Get the email address that is stored in the payload of the JWT this ViewModel holds.
     */
    public static String getEmail() {
        return mUserEmail;
    }

    public static int getUserID() {
        return mUserID;
    }

    /**
     * Get the JWT that this ViewModel holds.
     */
    public String getmJwt() {
        return mJwt;
    }

    /**
     * Helper factory class for verifying user's email.
     */
    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {

        private final String email;
        private final String jwt;

        /**
         * Constructor that initializes user email and JWT.
         * @param email the user's email
         * @param jwt the String where the email is being stored
         */
        public UserInfoViewModelFactory(String email, String jwt) {
            this.email = email;
            this.jwt = jwt;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(email, jwt);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }
}