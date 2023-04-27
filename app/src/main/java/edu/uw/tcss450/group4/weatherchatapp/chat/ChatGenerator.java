package edu.uw.tcss450.group4.weatherchatapp.chat;

import java.util.Arrays;
import java.util.List;

/**
 * This class is used to create Dummy Chats. Use it for development.
 * In future, get actual Chat data.
 */

public class ChatGenerator {
    private static final ChatPreview[] CHATS;
    public static final int COUNT = 5;

    static {
        CHATS = new ChatPreview[COUNT];
        for (int i = 0; i < CHATS.length; i++) {
            CHATS[i] = new ChatPreview
                    .Builder("Jane", "Hey there", "4:05 pm")
                    .build();
        }
    }

    public static List<ChatPreview> getChatList() {
        return Arrays.asList(CHATS);
    }

    public static ChatPreview[] getCHATS() {
        return Arrays.copyOf(CHATS, CHATS.length);
    }

    /**
     * Empty private constructor.
     */
    private ChatGenerator() {}
}
