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
    private static String mEmail;
    private final String mJwt;
    private final int mMemberID;

    /**
     * Private constructor that initializes user email and jwt.
     * @param email the user's email address
     * @param jwt the String where the email is stored
     */
    private UserInfoViewModel(String email, String jwt, int ID) {
        mEmail = email;
        mJwt = jwt;
        mMemberID = ID;
    }

    public static void setEmail(String email) {
        mEmail = email;
    }

    /**
     * Get the email address that is stored in the payload of the JWT this ViewModel holds.
     */
    public static String getEmail() {
        return mEmail;
    }

    /**
     * Get the JWT that this ViewModel holds.
     */
    public String getJwt() {
        return mJwt;
    }

    /**
     * Get the memberID that this ViewModel holds
     * @return
     */
    public int getMemberID() { return mMemberID; }

    /**
     * Helper factory class for verifying user's email.
     */
    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {

        private final String email;
        private final String jwt;
        private final int memberID;

        public UserInfoViewModelFactory(String email, String jwt, int ID) {
            this.email = email;
            this.jwt = jwt;
            this.memberID = ID;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(email, jwt, memberID);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }
}