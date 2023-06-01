//package edu.uw.tcss450.group4.weatherchatapp.model;
//
//import android.app.Application;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LifecycleOwner;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.Observer;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.nio.charset.Charset;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
//import edu.uw.tcss450.group4.weatherchatapp.R;
//import edu.uw.tcss450.group4.weatherchatapp.io.RequestQueueSingleton;
//import me.pushy.sdk.Pushy;
//
//public class PushyTokenViewModel extends AndroidViewModel{
//
//    private final MutableLiveData<String> mPushyToken;
//    private final MutableLiveData<JSONObject> mResponse;
//
//    public PushyTokenViewModel(@NonNull Application application) {
//        super(application);
//        mPushyToken = new MutableLiveData<>();
//        mPushyToken.setValue("");
//        mResponse = new MutableLiveData<>();
//        mResponse.setValue(new JSONObject());
//    }
//
//    /**
//     * Register as an observer to listen for the PushToken.
//     * @param owner the fragments lifecycle owner
//     * @param observer the observer
//     */
//    public void addTokenObserver(@NonNull LifecycleOwner owner,
//                                 @NonNull Observer<? super String> observer) {
//        mPushyToken.observe(owner, observer);
//    }
//
//    public void addResponseObserver(@NonNull LifecycleOwner owner,
//                                    @NonNull Observer<? super JSONObject> observer) {
//        mResponse.observe(owner, observer);
//    }
//
//    public void retrieveToken() {
//        if (!Pushy.isRegistered(getApplication().getApplicationContext())) {
//
//            Log.d("PUSH VIEW MODEL", "FETCHING NEW TOKEN");
//            new RegisterForPushNotificationsAsync().execute();
//
//        } else {
//            Log.d("PUSH VIEW MODEL", "USING OLD TOKEN");
//            mPushyToken.setValue(
//                Pushy.getDeviceCredentials(getApplication().getApplicationContext()).token);
//        }
//    }
//
//    /**
//     * This is the method described in the Pushy documentation. Note the Android class
//     * AsyncTask is deprecated as of Android Q. It is fine to use here and for this
//     * quarter. In your future Android development, look for an alternative solution.
//     */
//    private class RegisterForPushNotificationsAsync extends AsyncTask<Void, Void, String> {
//        protected String doInBackground(Void... params) {
//            String deviceToken;
//            try {
//                // Assign a unique token to this device
//                deviceToken = Pushy.register(getApplication().getApplicationContext());
//            }
//            catch (Exception e) {
//                // Return exc to onPostExecute
//                return e.getMessage();
//            }
//            // Success
//            return deviceToken;
//        }
//
//        @Override
//        protected void onPostExecute(String token) {
//            if (token.isEmpty()) {
//                // Show error in log - You should add error handling for the user.
//                Log.e("ERROR RETRIEVING PUSHY TOKEN", token);
//            } else {
//                mPushyToken.setValue(token);
//            }
//        }
//    }
//
//    /**
//     * Send this Pushy device token to the web service.
//     * @param jwt
//     * @throws IllegalStateException when this method is called before the token is retrieve
//     */
//    public void sendTokenToWebservice(final String jwt) {
//        if (mPushyToken.getValue().isEmpty()) {
//            throw new IllegalStateException("No pushy token. Do NOT call until token is retrieved");
//        }
//
//        String url = getApplication().getResources().getString(R.string.base_url) +
//                "auth";
//
//        JSONObject body = new JSONObject();
//        try {
//            body.put("token", mPushyToken.getValue());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        Request request = new JsonObjectRequest(
//                Request.Method.PUT,
//                url,
//                body, //push token found in the JSONObject body
//                mResponse::setValue,
//                this::handleError) {
//
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> headers = new HashMap<>();
//                // add headers <key,value>
//                headers.put("Authorization", jwt);
//                return headers;
//            }
//        };
//
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                10_000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        //Instantiate the RequestQueue and add the request to the queue
//        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
//                .addToRequestQueue(request);
//    }
//
//    private void handleError(final VolleyError error) {
//        if (Objects.isNull(error.networkResponse)) {
//            try {
//                mResponse.setValue(new JSONObject("{" +
//                        "error:\"" + error.getMessage() +
//                        "\"}"));
//            } catch (JSONException e) {
//                Log.e("JSON PARSE", "JSON Parse Error in handleError");
//            }
//        }
//        else {
//            String data = new String(error.networkResponse.data, Charset.defaultCharset());
//            try {
//                mResponse.setValue(new JSONObject("{" +
//                        "code:" + error.networkResponse.statusCode +
//                        ", data:" + data +
//                        "}"));
//            } catch (JSONException e) {
//                Log.e("JSON PARSE", "JSON Parse Error in handleError");
//            }
//        }
//    }
//}
