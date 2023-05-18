package edu.uw.tcss450.group4.weatherchatapp.ui.chat.individual;

import java.io.Serializable;

/**
 * Class to encapsulate an individual chat post.
 * Building an Object requires a message string.
 *
 * @author Chloe Duncan
 * @version 3 May 2023
 */
public class IndividualChat implements Serializable {
    private final String mMessage;

    /**
     * Helper class for building Credentials.
     *
     * @author Charles Bryan
     * @author Chloe Duncan
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
     * Constructor that uses the helper Credentials class.
     * @param builder is instance of helper Credentials class
     */
    private IndividualChat(final Builder builder) {
        this.mMessage = builder.mMessage;
    }

    /**
     * Getter method for chat message.
     * @return value of the private String mMessage field
     */
    public String getContact() {
        return mMessage;
    }
}
