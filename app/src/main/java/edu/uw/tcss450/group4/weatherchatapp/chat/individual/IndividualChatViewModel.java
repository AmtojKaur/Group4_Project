package edu.uw.tcss450.group4.weatherchatapp.chat.individual;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that prepares and manages data associated with an IndividualChat object.
 *
 * @author Chloe Duncan
 * @version 3 May 2023
 */
public class IndividualChatViewModel extends AndroidViewModel {
    private final MutableLiveData<List<IndividualChat>> mChatList;

    /**
     * Public constructor to declare a new MutableLiveData object and
     * sets its value to an ArrayList.
     * @param application the base class for maintaining global application state
     *                    for an IndividualChat object
     */
    public IndividualChatViewModel(@NonNull Application application) {
        super(application);
        mChatList = new MutableLiveData<>();
        mChatList.setValue(new ArrayList<>());
    }

    /**
     * Class that checks if the list of IndividualChat objects has changed.
     * @param owner the events of the Android life cycle
     * @param observer the live data associated with the list of IndividualChat objects
     */
    public void addIndividualChatListObserver(@NonNull LifecycleOwner owner,
                                              @NonNull Observer<? super List<IndividualChat>> observer) {
        mChatList.observe(owner, observer);
    }
}
