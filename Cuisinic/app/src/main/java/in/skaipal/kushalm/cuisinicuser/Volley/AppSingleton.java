package in.skaipal.kushalm.cuisinicuser.Volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;

public class AppSingleton {
    private static AppSingleton mAppSingletonInstance;
    private static Context mContext;
    private ImageLoader mImageLoader = new ImageLoader(this.mRequestQueue, new ImageCache() {
        private final LruCache<String, Bitmap> cache = new LruCache(20);

        public Bitmap getBitmap(String str) {
            return (Bitmap) this.cache.get(str);
        }

        public void putBitmap(String str, Bitmap bitmap) {
            this.cache.put(str, bitmap);
        }
    });
    private RequestQueue mRequestQueue = getRequestQueue();

    private AppSingleton(Context context) {
        mContext = context;
    }

    public static synchronized AppSingleton getInstance(Context context) {
        synchronized (AppSingleton.class) {
            if (mAppSingletonInstance == null) {
                mAppSingletonInstance = new AppSingleton(context);
            }
            context = mAppSingletonInstance;
        }
        return context;
    }

    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return this.mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String str) {
        request.setTag(str);
        getRequestQueue().add(request);
    }

    public ImageLoader getImageLoader() {
        return this.mImageLoader;
    }

    public void cancelPendingRequests(Object obj) {
        if (this.mRequestQueue != null) {
            this.mRequestQueue.cancelAll(obj);
        }
    }
}
