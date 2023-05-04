package edu.uw.tcss450.group4.weatherchatapp.ui.chat;

import java.io.Serializable;

/**
 * Class to encapsulate a Chat Preview.
 * Building and Object requires a contact name,
 * last sent or received message, and the time of message
 * sent or received.
 *
 * @author Chloe Duncan
 * @version 3 May 2023
 */
public class ChatPreview implements Serializable {
    private final String mContact;
    private final String mPreviewMsg;
    private final String mTimeOfMsg;

    /**
     * Helper class for building Credentials.
     *
     * @author Charles Bryan
     * @author Chloe Duncan
     */
    public static class Builder {
        private final String mContact;
        private final String mPreviewMsg;
        private final String mTimeOfMsg;

        public Builder(String contact, String message, String time) {
            this.mContact = contact;
            this.mPreviewMsg = message;
            this.mTimeOfMsg = time;
        }

        public ChatPreview build() {
            return new ChatPreview(this);
        }
    }

    /**
     * Private constructor used by the Builder helper class.
     * @param builder the Builder of this ChatPreview
     */
    private ChatPreview(final Builder builder) {
        this.mContact = builder.mContact;
        this.mPreviewMsg = builder.mPreviewMsg;
        this.mTimeOfMsg = builder.mTimeOfMsg;
    }

    /**
     * Getter method to access the contact or name of the chat.
     * @return String contact or name of the ChatPreview
     */
    public String getContact() {
        return mContact;
    }

    /**
     * Getter method to access the last message sent or received.
     * @return the last message associated with the ChatPreview
     */
    public String getPreviewMsg() {
        return mPreviewMsg;
    }

    /**
     * Getter method to access the time of the last message sent or received.
     * @return the last time associated with the ChatPreview
     */
    public String getTimeOfMsg() {
        return mTimeOfMsg;
    }
}