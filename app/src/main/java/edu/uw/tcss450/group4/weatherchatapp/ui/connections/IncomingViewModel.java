package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private void handleResult(final JSONObject result) {
        try {
            JSONObject root = result;
            if (root.has("rows")) {
                JSONArray data =
                        root.getJSONArray("rows");
                incomingList.clear();
                for(int i = 0; i < data.length(); i++) {
                    JSONObject jsonBlog = data.getJSONObject(i);

                    System.out.println(jsonBlog);

                    String first = jsonBlog.getString("firstname");
                    String last = jsonBlog.getString("lastname");
                    String email = jsonBlog.getString("email");
                    String username = jsonBlog.getString("username");

                    int memberid = jsonBlog.getInt("memberid_b");

                    int accepted = jsonBlog.getInt("verified") == 1 ? 1 : 0;
                    if (accepted == 1) {
                        incomingList.add(memberid);
                        UserObject post = new UserObject(
                                memberid,
                                "Name: " + first + " " + last,
                                "Email: " + email);
                        if (mUserList.getValue().stream().noneMatch(element -> element.key == (memberid))) {
                            mUserList.getValue().add(post);
                             mUsersIncoming.add(post);
                            System.out.println(mUsersIncoming.size());
                        }
                    }

                }
            } else {
                Log.e("ERROR!", "No response");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        mUserList.setValue(mUserList.getValue());
    }

    // GET INCOMING FRIEND REQUESTS
    // GET /contacts endpoint
    // if status 0, display ACCEPT/DECLINE card view
    // if status 1, display ACCEPTED view

    // POST ACCEPTED FRIEND REQUEST
    // POST /contacts
    // sets contact status to 1, making them a contact and not request

    // DELETE FRIEND REQUEST
    // DELETE /contacts endpoint


}
