package com.example.josephnunez.my_first_app;

import android.content.res.AssetManager;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Random;
import android.util.Log;

import static com.example.josephnunez.my_first_app.WordFileParser.parseWordFile;
import static com.example.josephnunez.my_first_app.game.getRandomWord;

public class MainActivity extends AppCompatActivity
{
    Random r = new Random();
    ArrayList<String> words;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("Joe: ", "Testing");

        words = parseWordFile(getBaseContext(), 3);
    }


    public void setGameWord(View view)
    {
        TextView showText = (TextView) findViewById(R.id.textView);
        TextView originalText = (TextView) findViewById(R.id.originalWord);

        String word = getRandomWord(r, words);
        originalText.setText(word);

        word = game.scramble(r, word);

        showText.setText(word);
    }
}
