package gp_b_5.shapetd.Login_Classes;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


/**
 * Created by Joseph Nunez on 9/24/2017.
 */

public class VolleySingleton
{
    private static  VolleySingleton volleyInstance;
    private         RequestQueue    volleyRequestQueue;
    private static Context          volleyContext;

    private VolleySingleton(Context context)
    {
        volleyContext = context;
        volleyRequestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context)
    {
        if(volleyInstance == null)
        {
            volleyInstance = new VolleySingleton(context);
        }
        return volleyInstance;
    }

    public RequestQueue getRequestQueue()
    {
        if(volleyRequestQueue == null)
        {
            volleyRequestQueue = Volley.newRequestQueue(volleyContext.getApplicationContext());
        }
        return volleyRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request)
    {
        getRequestQueue().add(request);
    }
}






