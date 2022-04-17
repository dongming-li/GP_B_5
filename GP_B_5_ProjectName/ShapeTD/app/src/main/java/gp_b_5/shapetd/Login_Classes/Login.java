package gp_b_5.shapetd.Login_Classes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import gp_b_5.shapetd.Menu.MainMenu;
import gp_b_5.shapetd.R;

import static gp_b_5.shapetd.R.id.userName;
import static gp_b_5.shapetd.R.id.userPass;
import static gp_b_5.shapetd.R.id.userRemember;

public class Login extends AppCompatActivity {

    private EditText edit_userName, edit_userPass;
    private CheckBox saveLogin;
    private SharedPreferences userLoginPref;
    private SharedPreferences.Editor prefEditor;
    private boolean saveLoginBool;
    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userLoginPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        prefEditor = userLoginPref.edit();

        edit_userName = (EditText) findViewById(userName);
        edit_userPass = (EditText) findViewById(userPass);
        saveLogin = (CheckBox) findViewById(userRemember);

        saveLoginBool = userLoginPref.getBoolean("saveLogin", false);
        if(saveLoginBool == true) {
            edit_userName.setText(userLoginPref.getString("userName", ""));
            edit_userPass.setText(userLoginPref.getString("userPass", ""));
            saveLogin.setChecked(true);
        }
    }

    /**
     * @param view
     */
    public void registerNewUser(View view)
    {
        Intent registerIntent = new Intent(Login.this, Register.class);
        startActivity(registerIntent);
    }

    /**
     * @param view
     */
    public void loginUser(View view)
    {
        if(view != null)
        {
            InputMethodManager inputMGR = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMGR.hideSoftInputFromWindow(view.getWindowToken(),0);
        }

        final String userName = edit_userName.getText().toString().trim();
        final String userPass = edit_userPass.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            edit_userName.setError("Username can't be blank");
            edit_userName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(userPass)) {
            edit_userPass.setError("Password can't be blank");
            edit_userPass.requestFocus();
            return;
        }
        if(saveLogin.isChecked()) {
            prefEditor.putBoolean("saveLogin", true);
        }else if (!saveLogin.isChecked()) {
            saveLogin.setChecked(false);
            prefEditor.clear();
            prefEditor.apply();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerURLs.URL_Login,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonObj = new JSONObject(response);

                            if(!jsonObj.getBoolean("error"))
                            {
                                Toast.makeText(getApplicationContext(), jsonObj.getString("message"), Toast.LENGTH_SHORT).show();

                                edit_userName.setText("");
                                edit_userPass.setText("");

                                prefEditor.putString("userName", userName);
                                prefEditor.putString("userPass", userPass);
                                prefEditor.putString("userLevel", jsonObj.getString("Level"));
                                prefEditor.putString("userExperience", jsonObj.getString("Experience"));
                                prefEditor.putString("userKills", jsonObj.getString("Kills"));
                                prefEditor.putString("userAccountType", jsonObj.getString("AccountType"));
                                prefEditor.apply();

                                startActivity(new Intent(getApplicationContext(), MainMenu.class));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError e)
                        {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })  {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError
                            {
                                Map<String, String> params = new HashMap<>();
                                params.put("userName", userName);
                                params.put("userPass", userPass);
                                return params;
                            }
                        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}
