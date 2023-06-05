package edu.uw.tcss450.group4.weatherchatapp.ui.chat;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450.group4.weatherchatapp.R;
import edu.uw.tcss450.group4.weatherchatapp.io.RequestQueueSingleton;
import edu.uw.tcss450.group4.weatherchatapp.ui.connections.UserObject;

public class ChatNewChatViewModel extends AndroidViewModel {

   private final MutableLiveData<JSONObject> mResponse;

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

    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            try {
                mResponse.setValue(new JSONObject("{" +
                        "error:\"" + error.getMessage() +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in NCVM");
            }
        }
        else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset())
                    .replace('\"', '\'');
            try {
                mResponse.setValue(new JSONObject("{" +
                        "code:" + error.networkResponse.statusCode +
                        ", data:\"" + data +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in NCVM");
            }
        }
    }

    /**
     * A method to connect to a webservice endpoint to create a new chatroom.
     * @param jwt a valid jwt
     * @param chatName the name of the chat room.
     */
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
                this::handleError);

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);

    }

    public void putMembers(final String jwt, int[] memberIds, int chatID) throws JSONException {
        String url = "https://amtojk-tcss450-labs.herokuapp.com/chats" + chatID;
        JSONObject body = new JSONObject();
        JSONArray members = new JSONArray(memberIds);

        try {
            body.put("members", members);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        System.out.println(body.toString());

        Request request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                body, //push token found in the JSONObject body
                mResponse::setValue,
                this::handleError);
    }
}
