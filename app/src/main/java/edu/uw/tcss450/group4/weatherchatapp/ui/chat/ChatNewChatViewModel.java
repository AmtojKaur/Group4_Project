package edu.uw.tcss450.group4.weatherchatapp.ui.chat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.ui.connections.UserObject;

public class ChatNewChatViewModel extends AndroidViewModel {

    private final MutableLiveData<List<UserObject>> mUserList;
    /**
     * Public constructor to declare a new MutableLiveData object and
     * sets its value to a List.
     * @param application the base class for maintaining global application state
     *                    for a ChatPreview object
     */
    public ChatNewChatViewModel(@NonNull Application application) {
        super(application);
        mUserList = new MutableLiveData<>();
        mUserList.setValue(new ArrayList<>());
    }

    /**
     * Class that checks if the list of Contact objects has changed.
     * @param owner the events of the Android life cycle
     * @param observer the live data associated with the list of UserObjects objects
     */
    public void addChatNewChatObserver(@NonNull LifecycleOwner owner,
                                           @NonNull Observer<? super List<UserObject>> observer) {
        mUserList.observe(owner, observer);
    }
}
