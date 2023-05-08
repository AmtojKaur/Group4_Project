package edu.uw.tcss450.group4.weatherchatapp.io;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.collection.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Class that handles queue of requests.
 *
 * @author Amtoj Kaur
 * @version 3 May 2023
 */
public class RequestQueueSingleton {
    private static RequestQueueSingleton instance;
    private static Context context;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    /**
     * Private constructor
     * @param context the context
     */
    private RequestQueueSingleton(Context context) {
        RequestQueueSingleton.context = context;
        mRequestQueue = getmRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    /**
     * Getter method
     * @param context the context
     * @return the current instance of RequestQueSingleton, or a new one if null
     */
    public static synchronized RequestQueueSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new RequestQueueSingleton(context);
        }
        return instance;
    }

    /**
     * Getter method
     * @return the queue of requests
     */
    public RequestQueue getmRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Method that adds request to request queue.
     * @param req the request being added
     * @param <T> the data type of the request
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getmRequestQueue().add(req);
    }

    /**
     * Getter method
     * @return the ImageLoader
     */
    public ImageLoader getmImageLoader() {
        return mImageLoader;
    }
}