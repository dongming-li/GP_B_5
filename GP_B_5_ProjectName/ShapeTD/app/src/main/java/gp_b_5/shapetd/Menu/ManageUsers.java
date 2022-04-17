package gp_b_5.shapetd.Menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import gp_b_5.shapetd.Login_Classes.ServerURLs;
import gp_b_5.shapetd.Login_Classes.VolleySingleton;
import gp_b_5.shapetd.R;

public class ManageUsers extends AppCompatActivity {

    private ListView l_Results;
    private ArrayList<String> l_users = new ArrayList<>();
    private String selectedUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        l_Results = (ListView) findViewById(R.id.Leaderboard_Results);

        JsonObjectRequest l_Request = new JsonObjectRequest(Request.Method.GET, ServerURLs.MANAGEUSERS_INIT, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            JSONArray jArray = response.getJSONArray("users");
                            for(int i = 0; i < jArray.length(); i++) {
                                JSONObject jObj = jArray.getJSONObject(i);
                                String username = jObj.getString("userName");
                                addToUserList(username);
                            }
                            drawUserList();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(l_Request);
    }
}
