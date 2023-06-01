package edu.uw.tcss450.group4.weatherchatapp.ui.connections;

import android.app.Application;
import android.content.ClipData;
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

import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatPreview;

public class ConnectionsViewModel extends AndroidViewModel {

    /*private final MutableLiveData<List<ChatPreview>> mChatList;

    public ConnectionsViewModel(@NonNull Application application) {
        super(application);
        mChatList = new MutableLiveData<>();
        mChatList.setValue(new ArrayList<>());
    }

    public void addChatListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<ChatPreview>> observer) {
        mChatList.observe(owner, observer);
    }*/
    int currentUserId;
    private MutableLiveData<List<ClipData.Item>> items;

    private List<ClipData.Item> listItems = new ArrayList<>();
    public List<Integer> contactsList = new ArrayList<>();
    public ContactsListViewModel(@NonNull Application application) {
        super(application);
        items = new MutableLiveData<>();
        items.setValue(new ArrayList<>());
    }
    public void setCurrentUserId(int memberId) {
        currentUserId = (memberId);
    }

    public void addBlogListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<ClipData.Item>> observer) {
        items.observe(owner, observer);
    }

    public List<ClipData.Item> getItems() {
        return listItems;
    }
    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", error.toString());
//        throw new IllegalStateException(error.getMessage());
    }

    private void handleResult(final JSONObject result) {
        try {
            JSONObject root = result;
            if (root.has("rows")) {
                JSONArray data =
                        root.getJSONArray("rows");
                contactsList.clear();
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
                        contactsList.add(memberid);
                        ClipData.Item post = new ClipData.Item(memberid, "Name: " + first + " " + last, "Email: " + email,
                                "Username: " + username);
                        if (items.getValue().stream().noneMatch(element -> element.key == (memberid))) {
                            items.getValue().add(post);
                            listItems.add(post);
                            System.out.println(listItems.size());
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
        items.setValue(items.getValue());

    }

    public void connectGET() {
        String url =
                "https://tcss-450-team-7.herokuapp.com/contacts/" + currentUserId;


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
                "https://tcss-450-team-7.herokuapp.com/contacts/" + currentUserId + "/" + b ;

        JSONObject body = new JSONObject();
        try {
            body.put("memberID_A", currentUserId);
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
