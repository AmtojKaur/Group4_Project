package edu.uw.tcss450.group4.weatherchatapp.chat;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

public class ChatListViewModel extends AndroidViewModel {

    private MutableLiveData<List<ChatPreview>> mChatList;
    private ChatListFragment mChatListFragment;

    public ChatListViewModel(@NonNull Application application) {
        super(application);
        mChatList = new MutableLiveData<>();
        mChatList.setValue(new ArrayList<>());
    }

    public void addChatListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<ChatPreview>> observer) {
        mChatList.observe(owner, observer);
    }

//    public void newChat() {
//        Log.d("Add chat", "NEW CHAT");
//        mChatList.getValue().add(new ChatPreview
//                .Builder("Jane", "Hey there, this is John.", "12:00 am")
//                .build());
//    }
//
//    public void deleteChat() {
//        mChatList.getValue().remove(mChatList.getValue().size() - 1);
//    }


    // TODO: implement ViewModel

    // Individual or “Group” chat with an existing Connection(s)
    // Start a chat
    // Send and receive messages
    // Messages must be stored (server side)
    // “See” when the other person is typing (EC)
    // Store previous messages locally (EC)
    // Continue an individual chat with an existing Connection
    // Open a new chat request from an existing Connection
    // Send/receive Images (EC)
}
