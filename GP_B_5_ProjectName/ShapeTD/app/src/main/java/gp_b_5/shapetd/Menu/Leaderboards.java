package gp_b_5.shapetd.Menu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import gp_b_5.shapetd.Login_Classes.ServerURLs;
import gp_b_5.shapetd.Login_Classes.VolleySingleton;
import gp_b_5.shapetd.R;

import static android.R.id.list;

public class Leaderboards extends Activity implements AdapterView.OnItemSelectedListener
{
    private String sort     = "";
    private String select   = "";
    private ListView l_Results;
    private ArrayList<String> l_users = new ArrayList<>();
    private TextView selectedSort;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        Spinner l_keys = (Spinner) findViewById(R.id.Leaderboard_Key_Select);
        l_keys.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter_key = ArrayAdapter.createFromResource(this, R.array.Leaderboard_Key, android.R.layout.simple_spinner_item);
        adapter_key.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        l_keys.setAdapter(adapter_key);

        Spinner l_sort = (Spinner) findViewById(R.id.Leaderboard_Key_Sort);
        l_sort.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter_sort = ArrayAdapter.createFromResource(this, R.array.Leaderboard_Sort, android.R.layout.simple_spinner_item);
        adapter_sort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        l_sort.setAdapter(adapter_sort);

        l_Results = (ListView) findViewById(R.id.Leaderboard_Results);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        Spinner selectedSpinner = (Spinner) parent;
        if(selectedSpinner.getId() == R.id.Leaderboard_Key_Sort) {
            sort = parent.getItemAtPosition(position).toString();
        }
        else if(selectedSpinner.getId() == R.id.Leaderboard_Key_Select)
        {
            select = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void onButtonGo(View view) {
        String urlQuery = "";
        if(select.equals("Kills"))
        {
            if(sort.equals("Ascending"))
            {
                urlQuery = ServerURLs.LEADERBOARD_KILL_A;
            }
            else
            {
                urlQuery = ServerURLs.LEADERBOARD_KILL_D;
            }
        }
        if(select.equals("Level"))
        {
            if(sort.equals("Ascending"))
            {
                urlQuery = ServerURLs.LEADERBOARD_LEVEL_A;
            }
            else
            {
                urlQuery = ServerURLs.LEADERBOARD_LEVEL_D;
            }
        }
        if(select.equals("Experience"))
        {
            if(sort.equals("Ascending"))
            {
                urlQuery = ServerURLs.LEADERBOARD_EXPERIENCE_A;
            }
            else
            {
                urlQuery = ServerURLs.LEADERBOARD_EXPERIENCE_D;
            }
        }
        JsonObjectRequest l_Request = new JsonObjectRequest(Request.Method.GET, urlQuery, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            JSONArray jArray = response.getJSONArray("users");
                            for(int i = 0; i < jArray.length(); i++) {
                                JSONObject jObj = jArray.getJSONObject(i);
                                String username = jObj.getString("userName");
                                String option = jObj.getString(select.toLowerCase());
                                String combo = padLeaderString(username, option);
                                addToLeaderboard(combo);
                            }

                        drawLeaderboard();
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
    private void addToLeaderboard(String s)
    {
        l_users.add(s);
    }
    private void drawLeaderboard() {
        ArrayList<String> temp = (ArrayList<String>) l_users.clone();
        l_users.clear();
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, temp);
        l_Results.setAdapter(listViewAdapter);
    }
    public static String padLeaderString(String s1, String s2) {
        return String.format("%15s %-15s", s1, s2);
    }
}
