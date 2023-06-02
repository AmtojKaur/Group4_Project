package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

public class IncomingViewModel extends AndroidViewModel {

    private int mUserID;
    private final MutableLiveData<List<UserObject>> mUserList;

    private List<UserObject> mUsersIncoming = new ArrayList<>();
    public List<Integer> incomingList = new ArrayList<>();

    public IncomingViewModel(@NonNull Application application) {
        super(application);
        mUserList = new MutableLiveData<>();
        mUserList.setValue(new ArrayList<>());
    }

    public void addIncomingFriendsListObserver(@NonNull LifecycleOwner owner,
                                               @NonNull Observer<? super List<UserObject>> observer) {
        mUserList.observe(owner, observer);
    }

    public void setUserID(int id) {
        mUserID = id;
    }

    public List<UserObject> getIncomingUsers() {
        return mUsersIncoming;
    }

    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", error.toString());
    }

}
