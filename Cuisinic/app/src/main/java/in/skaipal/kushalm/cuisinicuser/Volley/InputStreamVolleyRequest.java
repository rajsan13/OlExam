package in.skaipal.kushalm.cuisinicuser.Volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import java.util.HashMap;
import java.util.Map;

public class InputStreamVolleyRequest extends Request<byte[]> {
    private final Listener<byte[]> mListener;
    private Map<String, String> mParams;
    public Map<String, String> responseHeaders;

    public InputStreamVolleyRequest(int i, String str, Listener<byte[]> listener, ErrorListener errorListener, HashMap<String, String> hashMap) {
        super(i, str, errorListener);
        setShouldCache(0);
        this.mListener = listener;
        this.mParams = hashMap;
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return this.mParams;
    }

    protected void deliverResponse(byte[] bArr) {
        this.mListener.onResponse(bArr);
    }

    protected Response<byte[]> parseNetworkResponse(NetworkResponse networkResponse) {
        this.responseHeaders = networkResponse.headers;
        return Response.success(networkResponse.data, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }
}
