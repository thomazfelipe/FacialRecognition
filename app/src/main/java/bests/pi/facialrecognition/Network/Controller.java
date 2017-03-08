package bests.pi.facialrecognition.Network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by thomaz on 08/03/17.
 */

public class Controller {

    protected static Controller Instance;
    protected RequestQueue requestQueue;
    protected static Context context;

    public Controller(Context c){
        context = c;
        requestQueue = getRequestQueue();
    }
    public static synchronized Controller getInstance(Context context){
        if(Instance == null)
        {
            Instance = new Controller(context);
        }
        return Instance;
    }
    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }
    public <T> void addToRequestQuee(Request<T> request){

        requestQueue.add(request);
    }
}
