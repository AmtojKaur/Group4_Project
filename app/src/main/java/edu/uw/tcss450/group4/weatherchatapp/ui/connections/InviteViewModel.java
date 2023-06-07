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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.model.UserInfoViewModel;
import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatPreview;

public class InviteViewModel extends AndroidViewModel {

    // store user data
    private final int mUserID = UserInfoViewModel.getUserID();
    private int mOtherUserID;
    private String mUserEmail;
    private String mOtherUserEmail;

    private final MutableLiveData<List<UserObject>> mInviteList;
    private List<UserObject> mUsersInvited = new ArrayList<>();

    public InviteViewModel(@NonNull Application application) {
        super(application);
        mInviteList = new MutableLiveData<>();
        mInviteList.setValue(new ArrayList<>());
    }

    public void addInviteListObserver(@NonNull LifecycleOwner owner,
                                      @NonNull Observer<? super List<UserObject>> observer) {
        mInviteList.observe(owner, observer);
    }

    //
    // Getters and Setters
    //

    public int getUserID() {
        return mUserID;
    }

    public int getOtherUserID() {
        return mOtherUserID;
    }

    public void setOtherUserID(int id) {
        mOtherUserID = id;
    }

    public void setUserEmail(String email) {
        mUserEmail = email;
    }

    public void setOtherUserEmail(String email) {
        mOtherUserEmail = email;
    }

    public List<UserObject> getUsersInvitedList() {
        return mUsersInvited;
    }

    //
    // Client Functionality
    //

    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR: Invite VM", error.toString());
    }

    private void handleResult(final JSONObject result) {
        System.out.println("handle result");
        if (result.length() > 0) {
            System.out.println(result.names().toString());

            // GET user id
            if (result.has("id")) {
                try {
                    setOtherUserID(result.getInt("id"));
                    Log.e("not an error", String.valueOf(getOtherUserID()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // GET sent
            else if (result.has("requests")) {
                try {
                    Log.e("requests", "list");

                    for (int i = 0; i < result.getJSONArray("requests").length(); i++) {
                        String sub = result.getJSONArray("requests").getString(i);
                        String otherUserID = (sub.substring(12))
                                .replaceAll("[}]", "");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            else {
                Log.e("ERROR handleResult", "wrong params");
            }
        }
    }

    public void connectGETuserID(String email) {
        String url =
                "https://amtojk-tcss450-labs.herokuapp.com/users/id/" + email;

        Request request = new JsonObjectRequest(
                Request.Method.GET,
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


    public void connectGETsent() {
        String url =
                "https://amtojk-tcss450-labs.herokuapp.com/contacts/sent/" + mUserID;

        // request
        Request request = new JsonObjectRequest(
                Request.Method.GET,
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

    public void connectPOST(int memberid_A, int memberid_B) {
        System.out.println(memberid_A + " " + memberid_B);
        String url =
                "https://amtojk-tcss450-labs.herokuapp.com/contacts/";

        // body parameters
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("memberID_A", memberid_A);
            parameters.put("memberID_B", memberid_B);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // request
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
}
