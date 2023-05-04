package edu.uw.tcss450.group4.weatherchatapp.chat;

import java.io.Serializable;

public class ChatPreview implements Serializable {

    private final String mContact;
    private final String mPreviewMsg;
    private final String mTimeOfMsg;

    /**
     * Helper class for building Credentials.
     *
     * @author Charles Bryan
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
     * Constructor
     * @param builder
     */
    private ChatPreview(final Builder builder) {
        this.mContact = builder.mContact;
        this.mPreviewMsg = builder.mPreviewMsg;
        this.mTimeOfMsg = builder.mTimeOfMsg;
    }

    public String getContact() {
        return mContact;
    }

    public String getPreviewMsg() {
        return mPreviewMsg;
    }

    public String getTimeOfMsg() {
        return mTimeOfMsg;
    }
}
