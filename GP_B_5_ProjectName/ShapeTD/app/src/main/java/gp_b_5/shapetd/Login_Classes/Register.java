package gp_b_5.shapetd.Login_Classes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import gp_b_5.shapetd.R;

import static gp_b_5.shapetd.R.id.check_Admin;
import static gp_b_5.shapetd.R.id.check_Map;
import static gp_b_5.shapetd.R.id.check_Normal;
import static gp_b_5.shapetd.R.id.userName;
import static gp_b_5.shapetd.R.id.userPass;

public class Register extends AppCompatActivity {

    private EditText edit_userName, edit_userPass, edit_userPassConfirm;
    private CheckBox normal_User, admin_User, map_User;
    private int accountType = 0;
    private String userInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edit_userName = (EditText) findViewById(userName);
        edit_userPass = (EditText) findViewById(userPass);
        edit_userPassConfirm = (EditText) findViewById(R.id.userPass2);
        normal_User =   (CheckBox) findViewById(check_Normal);
        admin_User =    (CheckBox) findViewById(check_Admin);
        map_User =      (CheckBox) findViewById(check_Map);
    }
    public void setNormalUser(View view) {
        if(normal_User.isChecked()) {
            admin_User.setChecked(false);
            map_User.setChecked(false);
        }
    }
    public void setAdminUser(View view) {
        if(admin_User.isChecked()) {
            normal_User.setChecked(false);
            map_User.setChecked(false);
            AlertDialog.Builder ADB = new AlertDialog.Builder(this);
            ADB.setTitle("Admin Key");
            final EditText adminInput = new EditText(this);
            adminInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ADB.setView(adminInput);
            ADB.setPositiveButton("Verify", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    userInput = adminInput.getText().toString();
                }
            });
            ADB.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    admin_User.setChecked(false);
                    dialog.cancel();
                }
            });
            ADB.setCancelable(false);
            ADB.show();
        }
    }
    public void setMapUser(View view) {
        if(map_User.isChecked()) {
            admin_User.setChecked(false);
            normal_User.setChecked(false);
        }
    }
    public void registerUser(View view) {
        if(!normal_User.isChecked() && !admin_User.isChecked() && !map_User.isChecked()) {
            admin_User.setError("You must select an account type!");
            admin_User.requestFocus();
            return;
        }
        else if(normal_User.isChecked()) {
            accountType = 1;
        }
        else if(admin_User.isChecked()) {
            if (!userInput.equals("123456")) {
                admin_User.setChecked(false);
                admin_User.setError("Admin Key is incorrect!");
                admin_User.requestFocus();
                return;
            } else {
                accountType = 3;
            }
        }
        else if(map_User.isChecked()) {
            accountType = 2;
        }
        if(view != null)
        {
            InputMethodManager inputMGR = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMGR.hideSoftInputFromWindow(view.getWindowToken(),0);
        }

        final String userName = edit_userName.getText().toString().trim();
        final String userPass = edit_userPass.getText().toString().trim();
        final String userPassConfirm = edit_userPassConfirm.getText().toString().trim();

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
        if (TextUtils.isEmpty(userPassConfirm)) {
            edit_userPassConfirm.setError("Password can't be blank");
            edit_userPassConfirm.requestFocus();
            return;
        }
        if (!userPass.equals(userPassConfirm)) {
            edit_userPass.setError("Passwords must match");
            edit_userPassConfirm.setText("");
            edit_userPass.requestFocus();
            return;
        }
        if(userPass.length()<6)
        {
            edit_userPass.setError("Passwords must be at least 6 characters");
            edit_userPass.requestFocus();
            return;
        }
        StringRequest registerRequest = new StringRequest(Request.Method.POST, ServerURLs.URL_Register,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonOBJ = new JSONObject(response);

                            if (!jsonOBJ.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), jsonOBJ.getString("message"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Login.class));
                            } else {
                                edit_userName.setError(jsonOBJ.getString("message"));
                                edit_userName.requestFocus();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userName", userName);
                params.put("userPass", userPass);
                params.put("accountType", String.valueOf(accountType));
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(registerRequest);
    }
}
