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

import edu.uw.tcss450.group4.weatherchatapp.R;

public class ConnectionsViewModel extends AndroidViewModel {

    private int mUserID;
    private final MutableLiveData<List<UserObject>> mUserList;
    private List<UserObject> mUsers = new ArrayList<>();
    public List<Integer> contactsList = new ArrayList<>();

    public ConnectionsViewModel(@NonNull Application application) {
        super(application);
        mUserList = new MutableLiveData<>();
        mUserList.setValue(new ArrayList<>());
    }

    public void addConnectionsListObserver(@NonNull LifecycleOwner owner,
                                           @NonNull Observer<? super List<UserObject>> observer) {
        mUserList.observe(owner, observer);
    }

    public void setUserID(int id) {
        mUserID = id;
    }

    public List<UserObject> getUsers() {
        return mUsers;
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
                contactsList.clear();
                for(int i = 0; i < data.length(); i++) {
                    JSONObject jsonContact = data.getJSONObject(i);

                    System.out.println(jsonContact);

                    String first = jsonContact.getString("firstname");
                    String last = jsonContact.getString("lastname");
                    String email = jsonContact.getString("email");
                    String username = jsonContact.getString("username");

                    int memberid = jsonContact.getInt("memberid_b");

                    int accepted = jsonContact.getInt("verified") == 1 ? 1 : 0;
                    if (accepted == 1) {
                        contactsList.add(memberid);
                        UserObject post = new UserObject(
                                memberid,
                                "Name: " + first + " " + last,
                                "Email: " + email);
                        if (mUserList.getValue().stream().noneMatch(element -> element.key == (memberid))) {
                            mUserList.getValue().add(post);
                            mUsers.add(post);
                            System.out.println(mUsers.size());
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

    public void connectGET() {
        String url =
                "https://amtojk-tcss450-labs.herokuapp.com/contacts" + mUserID;

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
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

    public void connectDELETE(int b) {
        String url =
                "https://amtojk-tcss450-labs.herokuapp.com/contacts" + mUserID + "/" + b ;

        JSONObject body = new JSONObject();
        try {
            body.put("memberID_A", mUserID);
            body.put("memberID_B", b);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                body,
                null,
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