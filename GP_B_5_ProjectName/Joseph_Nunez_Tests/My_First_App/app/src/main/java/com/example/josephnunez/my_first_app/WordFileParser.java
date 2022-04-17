package com.example.josephnunez.my_first_app;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Joseph Nunez on 9/6/2017.
 */

public class WordFileParser
{
    public static ArrayList<String> parseWordFile(Context mainContext, int wordLength)
    {
        ArrayList<String> words = new ArrayList<>();
        try
        {
            AssetManager assetManager = mainContext.getAssets();
            InputStream is = assetManager.open("WordList.txt");
            BufferedReader br = new BufferedReader((new InputStreamReader(is)));
            String line = null;

            while((line = br.readLine()) != null)
            {
                if(line.length() == wordLength)
                {
                    words.add(line);
                }
            }
            br.close();
            is.close();
        }
        catch (IOException e)
        {
            Log.e("Joe: ", e.toString());
        }
        return words;
    }
}
