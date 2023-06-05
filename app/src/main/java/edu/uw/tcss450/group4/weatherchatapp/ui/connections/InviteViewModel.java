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

import edu.uw.tcss450.group4.weatherchatapp.model.UserInfoViewModel;

public class InviteViewModel extends AndroidViewModel {

    private int mUserID;
    private String mEmail;

    public boolean notifyUpdatedUserList = false;
    public boolean onCreate = false;

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

    public int getUserID() {
        return mUserID;
    }

    public void setUserID(int id) {
        mUserID = id;
        System.out.println("set: " + mUserID);
    }

    public void setUserEmail(String email) {
        mEmail = email;
    }

    public List<UserObject> getInvitedUsers() {
        return mUsersInvited;
    }

    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR: Invite VM", error.toString());
    }

    private void handleResult(final JSONObject result) {
        System.out.println("handle");
        if (result.length() > 0) {
            System.out.println(result.names().toString());

            // GET user ID
            if (result.has("id")) {
                try {
                    setUserID(result.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // GET contacts
            else if (result.has("data")) {
                try {
                    System.out.println("names " + result.names().toString());
                    System.out.println("length " + result.length());
                    System.out.println("array " + result.getJSONArray("data"));
                    System.out.println("array len " + result.getJSONArray("data").length());

                    for (int i = 0; i < 7; i++) {
                        JSONObject object = result.getJSONArray("data").getJSONObject(i);

                        int key = object.getInt("primarykey");
                        int memA = object.getInt("memberid_a");
                        int memB = object.getInt("memberid_b");
                        int verified = object.getInt("verified");

                        if (verified == 0) {
                            System.out.println("unverified");
                            inviteList.add(memB);
                            UserObject post = new UserObject(
                                    key,
                                    String.valueOf(memB),
                                    String.valueOf(memB));
                            if (mUserList.getValue().stream().noneMatch(element -> element.key == (key))) {
                                mUserList.getValue().add(post);
                                mUsersInvited.add(post);
                                mUserList.setValue(mUserList.getValue());
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // POST
            else if (result.has("request")) {
                System.out.println("request");
                try {
                    int primaryKey = result.getJSONObject("request").getInt("primarykey");
                    int memberid_a = result.getJSONObject("request").getInt("memberid_a");
                    int memberid_b = result.getJSONObject("request").getInt("memberid_b");
                    int friendshipStatus = result.getJSONObject("request").getInt("verified");

                    if (onCreate) {
                        setUserID(memberid_a);
                        onCreate = false;
                    }
                    System.out.println("key: " + primaryKey);
                    System.out.println("A: " + memberid_a);
                    System.out.println("B: " + memberid_b);
                    System.out.println("status: " + friendshipStatus);


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
                                String.valueOf(memberid_b));
                        if (mUserList.getValue().stream().noneMatch(element -> element.key == (primaryKey))) {
                            mUserList.getValue().add(post);

                            mUsersInvited.add(post);
                            notifyUpdatedUserList = true;
                            //mUserList.setValue(mUserList.getValue());
                        }
                    }

                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            }

            else {
                System.out.println(result.names().toString());
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

    public void connectGET() {
        mUserID = 63;
        String url =
                "https://amtojk-tcss450-labs.herokuapp.com/contacts/" + mUserID;

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

    public void connectPOST(String userEmail, String inviteEmail) {
        System.out.println("Post: " + userEmail + " " + inviteEmail);
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

    public void connectGETcontacts() {
        String url =
                "https://amtojk-tcss450-labs.herokuapp.com/contacts/" + mUserID;

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

    public void connectGETreceived() {
        String url =
                "https://amtojk-tcss450-labs.herokuapp.com/contacts/received/" + mUserID;

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

    public void connectPUT(int memberid_A, int memberid_B) {
        String url =
                "https://amtojk-tcss450-labs.herokuapp.com/contacts/";

        // body parameters
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("from", memberid_A);
            parameters.put("to", memberid_B);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // request
        Request request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                parameters,  // body for PUT
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

    public void connectPost(int memberid_A, int memberid_B) {
        String url =
                "https://amtojk-tcss450-labs.herokuapp.com/contacts/";

        // body parameters
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("from", memberid_A);
            parameters.put("to", memberid_B);
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

    public void connectDelete(int memberid_A, int memberid_B) {
        String url =
                "https://amtojk-tcss450-labs.herokuapp.com/contacts/";

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("from", memberid_A);
            parameters.put("to", memberid_B);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // request
        Request request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                parameters,  // body for DELETE
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
