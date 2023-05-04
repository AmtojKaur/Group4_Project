package edu.uw.tcss450.group4.weatherchatapp.chat.individual;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class IndividualChatGenerator {
    private static final ArrayList<IndividualChat> CHATS;
    private static int COUNT = 1;

    private static String[] dummyMessage = {"Hi", "This is our first chat", "I'm working on Heroku", "Dummy message", "ok", "hello", "sup"};

    static {
        CHATS = new ArrayList<>();

        for (int i = 0; i < COUNT; i++) {
            CHATS.add(new IndividualChat
                    .Builder(dummyMessage[i])
                    .build());
        }
    }

    public static List<IndividualChat> getChatList() {
        return CHATS;
    }

    public static IndividualChat addChat() {
        return new IndividualChat
                .Builder(dummyMessage[1])
                .build();
    }

//    public static ChatPreview[] getCHATS() {
//        return Arrays.copyOf(CHATS, CHATS.length);
//    }

    /**
     * Empty private constructor.
     */
    private IndividualChatGenerator() {
        Log.d("Individual Chat Generator", "Generating individual chats for recycler view");
    }
}
