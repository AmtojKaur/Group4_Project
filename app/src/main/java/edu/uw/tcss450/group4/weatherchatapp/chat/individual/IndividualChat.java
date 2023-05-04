package edu.uw.tcss450.group4.weatherchatapp.chat.individual;

import java.io.Serializable;

public class IndividualChat implements Serializable {
    private final String mMessage;

    /**
     * Helper class for building Credentials.
     *
     * @author Charles Bryan
     */
    public static class Builder {
        private final String mMessage;

        public Builder(String message) {
            this.mMessage = message;
        }

        public IndividualChat build() {
            return new IndividualChat(this);
        }
    }

    /**
     * Constructor
     * @param builder
     */
    private IndividualChat(final Builder builder) {
        this.mMessage = builder.mMessage;
    }

    public String getContact() {
        return mMessage;
    }
}
