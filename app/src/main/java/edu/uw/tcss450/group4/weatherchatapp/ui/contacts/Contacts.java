package edu.uw.tcss450.group4.weatherchatapp.ui.contacts;

import java.io.Serializable;

public class Contacts implements Serializable {

    private final String mFirstName;
    private final String mLastName;
    private final String mUsername;
    private final int mContactId;
    private boolean mIsActive;

    public static class ContactsBuilder {
        private final String mFirstName;
        private final String mLastName;
        private final String mUsername;
        private final int mContactId;
        private final boolean mIsActive;


        public ContactsBuilder(String theFirstname, String theLastName, String theUsername,
                               int theContactId, boolean theIsActive) {
            this.mFirstName = theFirstname;
            this.mLastName = theLastName;
            this.mUsername = theUsername;
            this.mContactId = theContactId;
            this.mIsActive = theIsActive;
        }

        public Contacts build() {
            return new Contacts(this);
        }
    }

    private Contacts(final ContactsBuilder builder) {
        this.mFirstName = builder.mFirstName;
        this.mLastName = builder.mLastName;
        this.mUsername = builder.mUsername;
        this.mContactId = builder.mContactId;
        this.mIsActive = builder.mIsActive;
    }


        public String getmFirstName() {
            return mFirstName;
        }

        public String getmLastName() {
            return mLastName;
        }

        public String getmUsername() {
            return mUsername;
        }

        public int getmContactId() {
            return mContactId;
        }
        public boolean getmIsActive() {
            return mIsActive;
        }

        public void setAccountActivity(boolean active) {
            mIsActive = active;
        }
}
