package gp_b_5.shapetd.Login_Classes;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gp_b_5.shapetd.R;

public class Entry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        MediaPlayer mp = MediaPlayer.create(this, R.raw.bulletbrigademp3);

        mp.start();
    }

    public void Login (View view)
    {
        Intent LoginIntent = new Intent(Entry.this, Login.class);
        startActivity(LoginIntent);
    }
    public void Register (View view)
    {
        Intent RegisterIntent = new Intent(Entry.this, Register.class);
        startActivity(RegisterIntent);
    }
}
