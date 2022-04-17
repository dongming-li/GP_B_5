package gp_b_5.shapetd.Menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import gp_b_5.shapetd.Login_Classes.Login;
import gp_b_5.shapetd.Login_Classes.User;
import gp_b_5.shapetd.R;

public class MainMenu extends AppCompatActivity {
    User currUser;
    private TextView    userAccountTypeBox;
    private String      StringAccount = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        SharedPreferences userLoginPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        currUser = new User(userLoginPref.getString("userName", ""),
                userLoginPref.getString("userLevel", ""),
                userLoginPref.getString("userExperience", ""),
                userLoginPref.getString("userKills", ""),
                userLoginPref.getString("userAccountType", "")
        );
        setContentView(R.layout.activity_main_menu);
//        Toast.makeText(getApplicationContext(), currUser.getUserName() + " "
//                + currUser.getLevel() + " " + currUser.getExperience() + " "
//                + currUser.getKills() + " " + currUser.getAccountType(), Toast.LENGTH_LONG).show();
        userAccountTypeBox = (TextView) findViewById(R.id.userAccountType);
        switch (currUser.getAccountType()) {
            case "1":
                StringAccount = "Normal";
                break;
            case "2":
                StringAccount = "Map Maker";
                break;
            case "3":
                StringAccount = "Admin";
                break;
        }
        userAccountTypeBox.setText("Account type: " + StringAccount);
    }

    public void Play (View view)
    {
        Intent mapSelectIntent = new Intent(MainMenu.this, MapSelect.class);
        startActivity(mapSelectIntent);
    }

    public void MapBuilder (View view)
    {
        if(currUser.getAccountType().equals("2") || currUser.getAccountType().equals("3")) {
            Intent buildMapIntent = new Intent(MainMenu.this, BuildMap.class);
            startActivity(buildMapIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Access Denied!", Toast.LENGTH_LONG).show();
        }
    }

    public void ViewLeaderBoards (View view)
    {
        Intent LeaderboardsIntent = new Intent(MainMenu.this, Leaderboards.class);
        startActivity(LeaderboardsIntent);
    }

    public void ManageUsers (View view)
    {
        if(currUser.getAccountType().equals("3")) {
            Intent manageUsersIntent = new Intent(MainMenu.this, ManageUsers.class);
            startActivity(manageUsersIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Admin Access Denied!", Toast.LENGTH_LONG).show();
        }
    }
    public void gameSettings(View view) {
        Intent gameSettingsIntent = new Intent(MainMenu.this, gameSettings.class);
        startActivity(gameSettingsIntent);
    }
    public void Exit (View view)
    {
        Intent LoginIntent = new Intent(MainMenu.this, Login.class);
        startActivity(LoginIntent);
    }
}
