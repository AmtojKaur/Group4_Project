package edu.uw.tcss450.group4.weatherchatapp.ui.chat.list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatPreview;

/**
 * Class that prepares and manages data associated with a ChatPreview object.
 *
 * @author Chloe Duncan
 */
public class ChatListViewModel extends AndroidViewModel {

    private final MutableLiveData<List<ChatPreview>> mChatList;

    /**
     * Public constructor to declare a new MutableLiveData object and
     * sets its value to an ArrayList.
     * @param application the base class for maintaining global application state
     *                    for a ChatPreview object
     */
    public ChatListViewModel(@NonNull Application application) {
        super(application);
        mChatList = new MutableLiveData<>();
        mChatList.setValue(new ArrayList<>());
    }

    /**
     * Class that checks if the list of ChatPreview objects has changed.
     * @param owner the events of the Android life cycle
     * @param observer the live data associated with the list of IndividualChat objects
     */
    public void addChatListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<ChatPreview>> observer) {
        mChatList.observe(owner, observer);
    }
}
