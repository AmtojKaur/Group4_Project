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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.group4.weatherchatapp.ui.chat.ChatPreview;

public class ConnectionsViewModel extends AndroidViewModel {

    private int currentUserId;
    private final MutableLiveData<List<ChatPreview>> mChatList;

    public ConnectionsViewModel(@NonNull Application application) {
        super(application);
        mChatList = new MutableLiveData<>();
        mChatList.setValue(new ArrayList<>());
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    public void addChatListObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<ChatPreview>> observer) {
        mChatList.observe(owner, observer);
    }

    public void connectGET() {
        String url = "YOUR_API_ENDPOINT" + currentUserId;

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    List<ChatPreview> chatPreviews = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject chatJson = response.getJSONObject(i);
                            int userID = chatJson.getInt("userID");
                            String contact = chatJson.getString("contact");
                            // Extract other necessary information from the JSON response

                            ChatPreview chatPreview = new ChatPreview(userID, contact);
                            chatPreviews.add(chatPreview);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mChatList.setValue(chatPreviews);
                },
                error -> {
                    Log.e("ConnectionsViewModel", "Error retrieving chat list: " + error.getMessage());
                }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
    }

    public void connectDELETE(int userID) {
        String url = "YOUR_API_ENDPOINT" + currentUserId + "/" + userID;

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("userID", userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                requestBody,
                null,
                error -> {
                    Log.e("ConnectionsViewModel", "Error deleting connection: " + error.getMessage());
                }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
    }
}
