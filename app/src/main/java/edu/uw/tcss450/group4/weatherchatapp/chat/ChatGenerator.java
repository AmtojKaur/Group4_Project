package edu.uw.tcss450.group4.weatherchatapp.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used to create Dummy Chats. Used for development.
 */

public class ChatGenerator {
    private static final ArrayList<ChatPreview> CHATS;
    public static int COUNT = 5;

    static {
        CHATS = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            CHATS.add(new ChatPreview
                    .Builder("Jane", "Hey there, this is John.", "12:00 am")
                    .build());
        }

    }

    public static List<ChatPreview> getChatList() {
        //return Arrays.asList(CHATS);
        return CHATS;
    }

//    public static ChatPreview[] getCHATS() {
//        return Arrays.copyOf(CHATS, CHATS.length);
//    }

    public void setCount(int num) {
        COUNT = num;
    }

    /**
     * Empty private constructor.
     */
    private ChatGenerator() {}
}
