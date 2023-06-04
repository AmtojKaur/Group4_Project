package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.app.Application;
import android.util.Log;

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
                    JSONObject jsonInvite = data.getJSONObject(i);

                    System.out.println(jsonInvite);

                    int primaryKey = jsonInvite.getInt("primaryKey");
                    int memberID_A = jsonInvite.getInt("memberid_a");
                    int memberID_B = jsonInvite.getInt("memberid_b");

                    String email = jsonInvite.getString("email");
                    String first = jsonInvite.getString("firstname");
                    String last = jsonInvite.getString("lastname");

                    // 0:unverified; 1:verified
                    int status = jsonInvite.getInt("verified");

                    if (status == 0) {
                        inviteList.add(memberID_B);
                        UserObject post = new UserObject(
                                memberID_B,
                                first + last,
                                email);
                        if (mUserList.getValue().stream().noneMatch(element -> element.key == (memberID_B))) {
                            mUserList.getValue().add(post);
                            mUsersInvited.add(post);
                            // debug feature
                            // see if invite list increased in size
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

    public void connectPOST(String userEmail, String inviteEmail) throws JSONException, AuthFailureError {
        String url =
                "https://amtojk-tcss450-labs.herokuapp.com/contacts/request";

        System.out.println(userEmail);

        System.out.println(inviteEmail);

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("fromEmail", "amgarcia@uw.edu");
            parameters.put("toEmail", inviteEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                parameters,
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
