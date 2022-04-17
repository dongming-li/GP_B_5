package com.example.josephnunez.my_first_app;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Joseph Nunez on 9/6/2017.
 */

public class game
{
    public static String scramble(Random random, String inputString )
    {
        // Convert your string into a simple char array:
        char a[] = inputString.toCharArray();

        // Scramble the letters using the standard Fisher-Yates shuffle,
        for( int i=0 ; i<a.length ; i++ )
        {
            int j = random.nextInt(a.length);
            // Swap letters
            char temp = a[i]; a[i] = a[j];  a[j] = temp;
        }
        if(inputString.equals(new String(a)))
        {
            scramble(random, inputString);
        }

        return new String( a );
    }
    public static String getRandomWord(Random random, ArrayList words)
    {
        int length = words.size();
        int j = random.nextInt(length);
        return words.get(j).toString();
    }
}
