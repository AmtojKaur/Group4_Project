package edu.uw.tcss450.group4.weatherchatapp.chat.individual;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

public class IndividualChatViewModel extends AndroidViewModel {
    private MutableLiveData<List<IndividualChat>> mChatList;

    public IndividualChatViewModel(@NonNull Application application) {
        super(application);
        mChatList = new MutableLiveData<>();
        mChatList.setValue(new ArrayList<>());
    }

    public void addIndividualChatListObserver(@NonNull LifecycleOwner owner,
                                              @NonNull Observer<? super List<IndividualChat>> observer) {
        mChatList.observe(owner, observer);
    }
}
