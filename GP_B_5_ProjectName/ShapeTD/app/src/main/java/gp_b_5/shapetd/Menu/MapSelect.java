package gp_b_5.shapetd.Menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gp_b_5.shapetd.R;

public class MapSelect extends AppCompatActivity {

    public int mapIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_select);
    }

    public void tenByTen(View view)
    {
        mapIndex = 1;
        Intent playGameIntent = new Intent(MapSelect.this, PlayGame.class);
        playGameIntent.putExtra("mapSelect", mapIndex);
        startActivity(playGameIntent);

    }
    public void twentyFiveByTwentyFive(View view)
    {
        mapIndex = 2;
        Intent playGameIntent = new Intent(MapSelect.this, PlayGame.class);
        playGameIntent.putExtra("mapSelect", mapIndex);
        startActivity(playGameIntent);
    }
    public void fiftyByFifty(View view)
    {
        mapIndex = 3;
        Intent playGameIntent = new Intent(MapSelect.this, PlayGame.class);
        playGameIntent.putExtra("mapSelect", mapIndex);
        startActivity(playGameIntent);
    }
}
