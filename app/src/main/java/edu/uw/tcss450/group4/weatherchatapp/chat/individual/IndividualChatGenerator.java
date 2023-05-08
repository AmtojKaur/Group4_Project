package edu.uw.tcss450.group4.weatherchatapp.chat.individual;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to encapsulate an individual chat post.
 * Building an Object requires a message string.
 *
 * @author Chloe Duncan
 * @version 3 May 2023
 */
public class IndividualChatGenerator {
    private static final ArrayList<IndividualChat> CHATS;
    private static final int COUNT = 1;

    private static final String[] dummyMessage = {"Hi", "This is our first chat", "I'm working on Heroku", "Dummy message", "ok", "hello", "sup"};

    static {
        CHATS = new ArrayList<>();

        for (int i = 0; i < COUNT; i++) {
            CHATS.add(new IndividualChat
                    .Builder(dummyMessage[i])
                    .build());
        }
    }

    /**
     * Getter method to access the ArrayList of IndividualChat objects.
     * @return the ArrayList of IndividualChat objects
     */
    public static List<IndividualChat> getChatList() {
        return CHATS;
    }

    /**
     * Method to create a new IndividualChat object in order to be added to the
     * already existing ArrayList of IndividualChat objects.
     * @return one IndividualChat object
     */
    public static IndividualChat addChat() {
        return new IndividualChat
                .Builder(dummyMessage[1])
                .build();
    }

    /**
     * Empty private constructor.
     */
    private IndividualChatGenerator() {
        Log.d("Individual Chat Generator", "Generating individual chats for recycler view");
    }
}
