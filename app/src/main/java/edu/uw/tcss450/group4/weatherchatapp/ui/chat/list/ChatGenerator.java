package edu.uw.tcss450.group4.weatherchatapp.ui.chat.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatPreview;

/**
 * This class is used to create Dummy Chat overviews.
 * Used for development, mainly Sprint 1.
 *
 * @author Chloe Duncan
 * @version 3 May 2023
 */
public class ChatGenerator {
    private static final ArrayList<ChatPreview> CHATS;
    private static final int COUNT = 7;
    private static final String[] dummyNames = {"Jane", "John", "Amtoj", "AJ", "Andrew", "Zak", "Chloe"};
    private static final String[] dummyMessage = {"Hi", "This is our first chat", "I'm working on Heroku", "Dummy message", "ok", "hello", "sup"};
    private static final String[] dummyTime = {"9:25 pm", "12:00 pm", "8:06 am", "1:00 am", "Yesterday", "Saturday", "4/25"};

    static {
        CHATS = new ArrayList<>();

        for (int i = 0; i < COUNT; i++) {
            CHATS.add(new ChatPreview
                    .Builder(dummyNames[i], dummyMessage[i], dummyTime[i])
                    .build());
        }
    }

    /**
     * Getter class for accessing the ArrayList of ChatPreview objects.
     * @return ArrayList of ChatPreview objects
     */
    public static List<ChatPreview> getChatList() {
        return CHATS;
    }

    public static List<ChatPreview> getSortedNameChatList() {
        List<ChatPreview> sortedList = new ArrayList<>();
        for (int i = 0; i < CHATS.size() - 1; i++) {
            sortedList.add(CHATS.get(i));
        }
        sortedList.sort(new Comparator<ChatPreview>() {
            @Override
            public int compare(ChatPreview o1, ChatPreview o2) {
                return o1.getContact().compareTo(o2.getContact());
            }
        });
        return sortedList;
    }

    /**
     * Class that creates one ChatPreview object in order to be added to the
     * already existing ArrayList of ChatPreview objects.
     * @return one ChatPreview object
     */
    public static ChatPreview addChat() {
        return new ChatPreview
                .Builder(dummyNames[1], dummyMessage[1], dummyTime[1])
                .build();
    }

    /**
     * Empty private constructor.
     */
    private ChatGenerator() {}
}
