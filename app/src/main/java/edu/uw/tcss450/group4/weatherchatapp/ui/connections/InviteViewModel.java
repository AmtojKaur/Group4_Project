package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InviteViewModel extends AndroidViewModel {

    private int mUserID;
    private final MutableLiveData<List<UserObject>> mUserList;

    private List<UserObject> mUsersInvited = new ArrayList<>();
    public List<Integer> inviteList = new ArrayList<>();

    public InviteViewModel(@NonNull Application application) {
        super(application);
        mUserList = new MutableLiveData<>();
        mUserList.setValue(new ArrayList<>());
    }

    public void addInviteFriendListObserver(@NonNull LifecycleOwner owner,
                                            @NonNull Observer<? super List<UserObject>> observer) {
        mUserList.observe(owner, observer);
    }

    public void setUserID(int id) {
        mUserID = id;
    }

    public List<UserObject> getInvitedUsers() {
        return mUsersInvited;
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
                inviteList.clear();
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
                        inviteList.add(memberid);
                        UserObject post = new UserObject(
                                memberid,
                                "Name: " + first + " " + last,
                                "Email: " + email);
                        if (mUserList.getValue().stream().noneMatch(element -> element.key == (memberid))) {
                            mUserList.getValue().add(post);
                            mUsersInvited.add(post);
                            System.out.println(mUsersInvited.size());
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



    // SEND FRIEND REQUEST
    // GET /users endpoint
    // verify user associated with email exists in list of all current users
    // GET all users and compare email
    // if email matches, get userID
    // POST /contacts/request friend request associated with userID

    // DELETE FRIEND REQUEST
    // DELETE /contacts endpoint
    // status is 0, for unconfirmed contacts
}
