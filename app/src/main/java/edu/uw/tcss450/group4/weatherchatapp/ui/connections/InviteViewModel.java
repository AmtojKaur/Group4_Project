package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InviteViewModel extends AndroidViewModel {

    private int mUserID;
    private String mEmail;

    public boolean notifyUpdatedUserList = false;

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
        System.out.println("mUsersInvited: " + mUsersInvited);
        return mUsersInvited;
    }

    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", error.toString());
    }

    private void handleResult(final JSONObject result) {
        if (result.length() > 0) {
            if (result.has("request")) {
                try {
                    int primaryKey = result.getJSONObject("request").getInt("primarykey");
                    int memberid_a = result.getJSONObject("request").getInt("memberid_a");
                    int memberid_b = result.getJSONObject("request").getInt("memberid_b");
                    int friendshipStatus = result.getJSONObject("request").getInt("verified");

                    setUserID(memberid_a);

                    if (friendshipStatus == 1) {
                        Toast.makeText(getApplication().getBaseContext(),
                                "Already friends",
                                Toast.LENGTH_LONG).show();
                    }

                    else if (friendshipStatus == 0) {
                        Toast.makeText(getApplication().getBaseContext(),
                                "Friend request sent!",
                                Toast.LENGTH_LONG).show();

                        inviteList.add(memberid_b);
                        UserObject post = new UserObject(
                                primaryKey,
                                String.valueOf(memberid_b),
                                null);
                        if (mUserList.getValue().stream().noneMatch(element -> element.key == (primaryKey))) {
                            mUserList.getValue().add(post);
                            mUsersInvited.add(post);
                            notifyUpdatedUserList = true;
                            //notifyItemInserted(mChats.size() - 1);
                            // debug feature
                            // see if invite list increased in size
                            System.out.println(mUsersInvited.size());
                            //mUserList.setValue(mUserList.getValue());
                        }
                    }

                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            }
        }
    }

    public void connectGET() {
        String url =
                "https://amtojk-tcss450-labs.herokuapp.com/contacts/" + mUserID;

        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,  // no body for GET
                this::handleResult,
                this::handleError);

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    public void connectPOST(String userEmail, String inviteEmail) {
        String url =
                "https://amtojk-tcss450-labs.herokuapp.com/contacts/request";

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("fromEmail", userEmail);
            parameters.put("toEmail", inviteEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                parameters,  // body for POST
                this::handleResult,
                this::handleError);

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
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
