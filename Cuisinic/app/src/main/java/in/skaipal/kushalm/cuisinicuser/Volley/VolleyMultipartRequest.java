package in.skaipal.kushalm.cuisinicuser.Volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

public class VolleyMultipartRequest extends Request<NetworkResponse> {
    private final String boundary;
    private final String lineEnd = "\r\n";
    private ErrorListener mErrorListener;
    private Map<String, String> mHeaders;
    private Listener<NetworkResponse> mListener;
    private final String twoHyphens = "--";

    protected class DataPart {
        private byte[] content;
        private String fileName;
        private String type;

        public DataPart(String str, byte[] bArr) {
            this.fileName = str;
            this.content = bArr;
        }

        String getFileName() {
            return this.fileName;
        }

        byte[] getContent() {
            return this.content;
        }

        String getType() {
            return this.type;
        }
    }

    protected Map<String, DataPart> getByteData() throws AuthFailureError {
        return null;
    }

    public VolleyMultipartRequest(int i, String str, Listener<NetworkResponse> listener, ErrorListener errorListener) {
        super(i, str, errorListener);
        i = new StringBuilder();
        i.append("apiclient-");
        i.append(System.currentTimeMillis());
        this.boundary = i.toString();
        this.mListener = listener;
        this.mErrorListener = errorListener;
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        return this.mHeaders != null ? this.mHeaders : super.getHeaders();
    }

    public String getBodyContentType() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("multipart/form-data;boundary=");
        stringBuilder.append(this.boundary);
        return stringBuilder.toString();
    }

    public byte[] getBody() throws AuthFailureError {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            Map params = getParams();
            if (params != null && params.size() > 0) {
                textParse(dataOutputStream, params, getParamsEncoding());
            }
            params = getByteData();
            if (params != null && params.size() > 0) {
                dataParse(dataOutputStream, params);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("--");
            stringBuilder.append(this.boundary);
            stringBuilder.append("--");
            stringBuilder.append("\r\n");
            dataOutputStream.writeBytes(stringBuilder.toString());
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            return Response.success(networkResponse, HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (Throwable e) {
            return Response.error(new ParseError(e));
        }
    }

    protected void deliverResponse(NetworkResponse networkResponse) {
        this.mListener.onResponse(networkResponse);
    }

    public void deliverError(VolleyError volleyError) {
        this.mErrorListener.onErrorResponse(volleyError);
    }

    private void textParse(DataOutputStream dataOutputStream, Map<String, String> map, String str) throws IOException {
        try {
            map = map.entrySet().iterator();
            while (map.hasNext()) {
                Entry entry = (Entry) map.next();
                buildTextPart(dataOutputStream, (String) entry.getKey(), (String) entry.getValue());
            }
        } catch (DataOutputStream dataOutputStream2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Encoding not supported: ");
            stringBuilder.append(str);
            throw new RuntimeException(stringBuilder.toString(), dataOutputStream2);
        }
    }

    private void dataParse(DataOutputStream dataOutputStream, Map<String, DataPart> map) throws IOException {
        map = map.entrySet().iterator();
        while (map.hasNext()) {
            Entry entry = (Entry) map.next();
            buildDataPart(dataOutputStream, (DataPart) entry.getValue(), (String) entry.getKey());
        }
    }

    private void buildTextPart(DataOutputStream dataOutputStream, String str, String str2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--");
        stringBuilder.append(this.boundary);
        stringBuilder.append("\r\n");
        dataOutputStream.writeBytes(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("Content-Disposition: form-data; name=\"");
        stringBuilder.append(str);
        stringBuilder.append("\"");
        stringBuilder.append("\r\n");
        dataOutputStream.writeBytes(stringBuilder.toString());
        dataOutputStream.writeBytes("\r\n");
        str = new StringBuilder();
        str.append(str2);
        str.append("\r\n");
        dataOutputStream.writeBytes(str.toString());
    }

    private void buildDataPart(DataOutputStream dataOutputStream, DataPart dataPart, String str) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--");
        stringBuilder.append(this.boundary);
        stringBuilder.append("\r\n");
        dataOutputStream.writeBytes(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("Content-Disposition: form-data; name=\"");
        stringBuilder.append(str);
        stringBuilder.append("\"; filename=\"");
        stringBuilder.append(dataPart.getFileName());
        stringBuilder.append("\"");
        stringBuilder.append("\r\n");
        dataOutputStream.writeBytes(stringBuilder.toString());
        if (dataPart.getType() != null && dataPart.getType().trim().isEmpty() == null) {
            str = new StringBuilder();
            str.append("Content-Type: ");
            str.append(dataPart.getType());
            str.append("\r\n");
            dataOutputStream.writeBytes(str.toString());
        }
        dataOutputStream.writeBytes("\r\n");
        str = new ByteArrayInputStream(dataPart.getContent());
        dataPart = Math.min(str.available(), 1048576);
        byte[] bArr = new byte[dataPart];
        int read = str.read(bArr, 0, dataPart);
        while (read > 0) {
            dataOutputStream.write(bArr, 0, dataPart);
            dataPart = Math.min(str.available(), 1048576);
            read = str.read(bArr, 0, dataPart);
        }
        dataOutputStream.writeBytes("\r\n");
    }
}
