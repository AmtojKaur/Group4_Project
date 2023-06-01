package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatObject;

public class ConnectionsViewModel extends AndroidViewModel {

    private final MutableLiveData<List<ChatObject>> mChatList;

    public ConnectionsViewModel(@NonNull Application application) {
        super(application);
        mChatList = new MutableLiveData<>();
        mChatList.setValue(new ArrayList<>());
    }

    public void addChatListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<ChatObject>> observer) {
        mChatList.observe(owner, observer);
    }
}
