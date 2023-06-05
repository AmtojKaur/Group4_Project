package edu.uw.tcss450.group4.weatherchatapp.ui.chat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uw.tcss450.group4.weatherchatapp.ui.connections.UserObject;

public class ChatNewChatViewModel extends AndroidViewModel {

   private MutableLiveData<JSONObject> mResponse;
    /**
     * Public constructor to declare a new MutableLiveData object and
     * sets its value to a List.
     * @param application the base class for maintaining global application state
     *                    for a ChatPreview object
     */
    public ChatNewChatViewModel(@NonNull Application application) {
        super(application);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Class that checks if the list of Contact objects has changed.
     * @param owner the events of the Android life cycle
     * @param observer the live data associated with the list of UserObjects objects
     */
    public void addChatNewChatObserver(@NonNull LifecycleOwner owner,
                                           @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }

    public void createChat(final String jwt, final String chatName) {
        String url = "https://amtojk-tcss450-labs.herokuapp.com/chats";
        JSONObject body = new JSONObject();
        try{
            body.put("name", chatName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                mResponse::setValue,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", jwt);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);

    }
}
