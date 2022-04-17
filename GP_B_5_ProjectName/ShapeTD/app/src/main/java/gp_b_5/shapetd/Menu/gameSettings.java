package gp_b_5.shapetd.Menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import gp_b_5.shapetd.Login_Classes.Entry;
import gp_b_5.shapetd.Login_Classes.Login;
import gp_b_5.shapetd.R;

/**
 * Created by Joe on 11/13/2017.
 */

public class gameSettings extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
    public void userLogout(View view) {
        SharedPreferences userLoginPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor userLoginPrefEdit = userLoginPref.edit();
        userLoginPrefEdit.clear();
        userLoginPrefEdit.commit();

        Toast.makeText(getApplicationContext(), "Successfully Logged Out!", Toast.LENGTH_SHORT).show();

        Intent entryIntent = new Intent(gameSettings.this, Entry.class);
        startActivity(entryIntent);
    }
}
