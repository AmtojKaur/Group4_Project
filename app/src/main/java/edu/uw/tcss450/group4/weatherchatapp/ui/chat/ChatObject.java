package edu.uw.tcss450.group4.weatherchatapp.ui.chat;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

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
public class ChatObject implements Serializable {
    private final int mMessageId;
    private final String mMessage;
    private final String mSender;
    private final String mTimeStamp;
    private boolean showMenu = false;
    private boolean acceptedRequest = false;

    public ChatObject(int messageId, String message, String sender, String timeStamp) {
        mMessageId = messageId;
        mMessage = message;
        mSender = sender;
        mTimeStamp = timeStamp;
    }

    /**
     * Helper class for building Credentials.
     *
     * @author Charles Bryan
     * @author Chloe Duncan
     */
//    public static class Builder {
//        private final String mContact;
//        private final String mPreviewMsg;
//        private final String mTimeOfMsg;
//
//        public Builder(String contact, String message, String time) {
//            this.mContact = contact;
//            this.mPreviewMsg = message;
//            this.mTimeOfMsg = time;
//        }
//
//        public ChatObject build() {
//            return new ChatObject(this);
//        }
//    }

    /**
     * Private constructor used by the Builder helper class.
     * @param builder the Builder of this ChatPreview
     */
//    private ChatObject(final Builder builder) {
//        this.mContact = builder.mContact;
//        this.mPreviewMsg = builder.mPreviewMsg;
//        this.mTimeOfMsg = builder.mTimeOfMsg;
//    }



    /**
     * Getter method to access the contact or name of the chat.
     * @return String contact or name of the ChatPreview
     */
    public int getMessageID() {
        return mMessageId;
    }

    /**
     * Getter method to access the last message sent or received.
     * @return the last message associated with the ChatPreview
     */
    public String getMessage() {
        return mMessage;
    }

    public String getSender() {
        return mSender;
    }

    /**
     * Getter method to access the time of the last message sent or received.
     * @return the last time associated with the ChatPreview
     */
    public String getTimeOfMsg() {
        return mTimeStamp;
    }

    public boolean isShowMenu() {
        return showMenu;
    }

    public boolean isAcceptedFriendRequest() {
        return acceptedRequest;
    }

    public void setShowMenu(boolean set) {
        showMenu = set;
    }

    public void setAcceptedFriendRequest(boolean set) {
        acceptedRequest = set;
    }

    /**
     * Static factory method to turn a properly formatted JSON String into a
     * ChatMessage object.
     * @param cmAsJson the String to be parsed into a ChatMessage Object.
     * @return a ChatMessage Object with the details contained in the JSON String.
     * @throws JSONException when cmAsString cannot be parsed into a ChatMessage.
     */
    public static ChatObject createFromJsonString(final String cmAsJson) throws JSONException {
        final JSONObject msg = new JSONObject(cmAsJson);
        return new ChatObject(msg.getInt("messageid"),
                msg.getString("message"),
                msg.getString("email"),
                msg.getString("timestamp"));
    }

    /**
     * Provides equality solely based on MessageId.
     * @param other the other object to check for equality
     * @return true if other message ID matches this message ID, false otherwise
     */
    @Override
    public boolean equals(@Nullable Object other) {
        boolean result = false;
        if (other instanceof ChatObject) {
            result = mMessageId == ((ChatObject) other).mMessageId;
        }
        return result;
    }
}