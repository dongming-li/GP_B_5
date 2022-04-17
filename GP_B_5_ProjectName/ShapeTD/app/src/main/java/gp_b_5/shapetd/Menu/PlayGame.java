package gp_b_5.shapetd.Menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import gp_b_5.shapetd.Core_Logic.GameEntity;
import gp_b_5.shapetd.R;
import gp_b_5.shapetd.Towers.TowerCircle;
import gp_b_5.shapetd.backend.Cell;
import gp_b_5.shapetd.backend.TileMapTouchListener;
import gp_b_5.shapetd.backend.TileMapView;

public class PlayGame extends AppCompatActivity implements TileMapTouchListener{

    static public int NumSquaresOnGridSide = 10;
    static public final int NumSquaresOnViewSide = 8;
    static public final int NumTypes = 4;     //
    static private Random mRandomObject = new Random (System.currentTimeMillis ());
    public int mapSelect = 0;

    public ArrayList<GameEntity> entityQueue = new ArrayList<GameEntity>();

/* Property Grid */
    /**
     * This variable holds the value of the Grid property.
     */

    //private int [][] pGrid;

    private int [][] pGridNew;
    /**
     * Get the value of the Grid property.
     *
     * @return int [][]
     */
    /**@Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                editNote(info.id);
                return true;
            case R.id.delete:
                deleteNote(info.id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
   **/
        public int[][] readMapFile(String fileName) throws FileNotFoundException
    {
        int[][] fieldArray = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));
            String line;
            line = reader.readLine();
            fieldArray = new int[line.length()][line.length()];
            NumSquaresOnGridSide = line.length();
            int i = 0;
            do
            {
                for (int j = 0; j < line.length(); j++)
                {
                    fieldArray[i][j] = Character.getNumericValue(line.charAt(j));
                }

                i++;
            }while ((line = reader.readLine()) != null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**File file = new File("C:\\cs309\\GP_B_5_ProjectName\\ShapeTD\\app\\src\\main\\res\\testMaps");
        Scanner scanner = new Scanner(file);
        int i = 0;
        while (scanner.hasNextLine())
        {
            String fileLine = scanner.nextLine();
            for (int j = 0; j < fileLine.length(); j++)
            {
                fieldArray[i][j] = fileLine.charAt(j);
            }
            i++;
        }**/
        return fieldArray;
    }
    public int [][] getGrid ()
    {
        //if (pGrid == null) {}
        return pGridNew;
    } // end getGrid

    /**
     * Set the value of the Grid property.
     *
     * @param newValue int [][]
     */

//    public void setGrid (int [][] newValue)
//    {
//        pGrid = newValue;
//    } // end setGrid

    public void setGrid (int [][] newValue)
    {
        pGridNew = newValue;
    } // end setGrid
/* end Property Grid */

/* Property GridView */
    /**
     * This variable holds the value of the GridView property.
     */

    private TileMapView pGridView;

    /**
     * Get the value of the GridView property.
     *
     * @return GameBoardView
     */

    public TileMapView getGridView ()
    {
        //if (pGridView == null) {}
        return pGridView;
    } // end getGridView

    /**
     * Set the value of the GridView property.
     *
     * @param newValue GameBoardView
     */

    public void setGridView (TileMapView newValue)
    {
        pGridView = newValue;
    } // end setGridView
/* end Property GridView */

/**
 */
// Methods

    /**
     * onCreate
     */

    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        int intValue = intent.getIntExtra("mapSelect", 0);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        try {
            mapSelect = intValue;
            setupMyGrid (NumSquaresOnGridSide);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        TileMapView gv = (TileMapView) findViewById (R.id.boardview);
        if (gv != null) {
            setGridView (gv);

            gv.setNumSquaresAlongCanvas (NumSquaresOnGridSide);
            gv.setNumSquaresAlongSide (NumSquaresOnViewSide);
            gv.updateGrid (getGrid ());
            gv.setTouchListener (this);
            Context context = getApplicationContext();
            CharSequence text = "Hold cell to get more info!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }


    }

    /**
     * Get 2-d grid of integers that indicate which bitmap is displayed at that point.
     *
     * @param n int - grid size is N x N squares
     * @return int [] []
     */

//    int [] [] randomGridArray (int n) {
//        // Set up with red, blue, and gray squares
//        int [][] grid = new int [n] [n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++) {
//                int index = randomInt (0, NumTypes-1);    // index indicates which image to use
//                grid [i][j] = index;
//            }
//        return grid;
//    }

    Cell [] [] randomGridArray (int n) {
        // Set up with random amount of paths, buildable squares
        Cell [][] grid = new Cell [n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                int terrainID = randomInt (0, NumTypes-1);    // index indicates which image to use
                grid [i][j] = new Cell(terrainID, i, j);
            }
        return grid;
    }

    /**
     * Return a random number in the range: minVal to maxVal.
     *
     */

    public int randomInt (int minVal, int maxVal) {
        Random r = mRandomObject;
        int range = maxVal - minVal;
        int offset = (int) Math.round (r.nextFloat () * range);
        return minVal + offset;
    }

    /**
     * Set up a 2d array of values to display in a GameBoardView.
     * The grid is filled in with random values between 0 and maxValue.
     * <p> The grid is saved with setGrid.
     * Use getGrid to access that object.
     *
     * @param n int - grid size is N x N squares
     * @param
     * @return void
     */


    public void setupMyGrid (int n) throws FileNotFoundException
    {
        int[][] grid = null;
        if (mapSelect == 1)
        {
            grid = readMapFile("map1.txt");
        }
        else if (mapSelect == 2)
        {
            grid = readMapFile("map2.txt");
        }
        else if (mapSelect == 3)
        {
            grid = readMapFile("map3.txt");
        }
        else
        {
            grid = readMapFile("map0.txt");
        }
        setGrid (grid);
    }

/**
 */
// GameBoardTouchListener methods

    /**
     * This method is called when a touch Down action occurs.
     *
     * <p> Note that the location of the down location is not provided, but it is provided when the touch ends
     * and a call is made to onTouchUp.
     */

    public void onTouchDown () {
    }

    /**
     * This method is called when a touch Up action occurs.
     *
     * <p>
     * Index values are 0 based.
     * Values are between 0 and NumSquaresAlongCanvas-1.
     *
     * @param downX int - x value of the down action square
     * @param downY int - y value of the down action square
     * @param upX int - x value of the up action square
     * @param upY int - y value of the up action square
     * @return void
     */

    public void onTouchUp (int downX, int downY, int upX, int upY) {
        TileMapView gv = getGridView ();
        if (gv == null) return;

        boolean isSelected = gv.isSelected (upX, upY);
        gv.clearSelections ();
        if (!isSelected) gv.toggleSelection (upX, upY);
        gv.invalidate ();

        /*if (AppConfig.DEBUG)
            Log.d (Constants.LOG_NAME, "onTouchUp x: " + upX + " y: " + upY + " selected: " + isSelected);*/

    }

    /**
     * This method is called when a touch Up action occurs and the time between down and up
     * exceeds the Android long press timeout value.
     *
     * <p>
     * Index values are 0 based.
     * Values are between 0 and NumSquaresAlongCanvas-1.
     *
     * @param downX int - x value of the down action square
     * @param downY int - y value of the down action square
     * @param upX int - x value of the up action square
     * @param upY int - y value of the up action square
     * @return void
     */

    public void onLongTouchUp (int downX, int downY, int upX, int upY) {
       TileMapView gv = getGridView ();
        if (gv == null) return;

        int oldValue = gv.gridValue (upX, upY);
        int newValue = oldValue;
        if (oldValue == 0)
        {
            newValue = 2;
            TowerCircle newTower = new TowerCircle();
            newTower.setPair(upX, upY);
            entityQueue.add(newTower);
        }
        else if (oldValue == 2) {
            newValue = 0;
            boolean found = false;
            int i = 0;
            while (i < entityQueue.size() && found == false) {
                if ((entityQueue.get(i).getPairX() == upX) && (entityQueue.get(i).getPairY() == upY)) {
                    entityQueue.remove(i);
                    found = true;
                }
                i++;
            }
        }
        gv.setGridValue (upX, upY, newValue);
        gv.invalidate ();


        /*if (AppConfig.DEBUG)
            Log.d (Constants.LOG_NAME, "onLongTouchUp x: " + upX + " y: " + upY + " old value: " + oldValue);*/

    }

    public void Play (View view) {
        Intent mainMenuIntent = new Intent(PlayGame.this, MainMenu.class);
        startActivity(mainMenuIntent);

    }
}

/*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate();
    }
*//*

=======

    }

}

    *//*@Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);*/

