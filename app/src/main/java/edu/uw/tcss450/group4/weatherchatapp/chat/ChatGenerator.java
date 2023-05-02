package edu.uw.tcss450.group4.weatherchatapp.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * This class is used to create Dummy Chats. Used for development.
 */

public class ChatGenerator {
    private static final ArrayList<ChatPreview> CHATS;
    private static int COUNT = 7;

    private static Random rand;

    private static String[] dummyNames = {"Jane", "John", "Amtoj", "AJ", "Andrew", "Zak", "Chloe"};
    private static String[] dummyMessage = {"Hi", "This is our first chat", "I'm working on Heroku", "Dummy message", "ok", "hello", "sup"};
    private static String[] dummyTime = {"9:25 pm", "12:00 pm", "8:06 am", "1:00 am", "Yesterday", "Saturday", "4/25"};


    static {
        CHATS = new ArrayList<>();

        for (int i = 0; i < COUNT; i++) {
            CHATS.add(new ChatPreview
                    .Builder(dummyNames[i], dummyMessage[i], dummyTime[i])
                    .build());
        }
    }

    public static List<ChatPreview> getChatList() {
        return CHATS;
    }

    public static ChatPreview addChat() {
        return new ChatPreview
                .Builder(dummyNames[1], dummyMessage[1], dummyTime[1])
                .build();
    }

//    public static ChatPreview[] getCHATS() {
//        return Arrays.copyOf(CHATS, CHATS.length);
//    }

    /**
     * Empty private constructor.
     */
    private ChatGenerator() {}
}
