package bests.pi.facialrecognition.Network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;


/**
 * Created by thomaz on 07/03/17.
 */

public class RequestLogin extends Request<JSONObject> {
    private Response.Listener<JSONObject> listener;
    private Map<String, String> params;

    public RequestLogin(int method, String url,
                              Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
    }
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }
}