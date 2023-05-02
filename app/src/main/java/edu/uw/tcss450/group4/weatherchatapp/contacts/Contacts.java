package edu.uw.tcss450.group4.weatherchatapp.contacts;

import java.io.Serializable;

public class Contacts implements Serializable {

    private final String mFirstName;
    private final String mLastName;
    private final String mUsername;
    private final int mContactId;

    public static class ContactsBuilder {
        private final String mFirstName;
        private final String mLastName;
        private final String mUsername;
        private final int mContactId;

        public ContactsBuilder(String theFirstname, String theLastName, String theUsername, int theContactId) {
            this.mFirstName = theFirstname;
            this.mLastName = theLastName;
            this.mUsername = theUsername;
            this.mContactId = theContactId;
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
}
