package com.example.zachary.tictactoe;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board = new GameBoard();
    }

    //declare global private variables

    private GameBoard board = null;
    private int moveCount = 0, xloc = 0, yloc = 0;
    private String mark = "X";
    private boolean isOver = false;


    public void resetClick(View v)
    {
        clear();
    }

    //action for when a cell is clicked. Determines which cell has been clicked and passed that
    //information on the virtual game board
    public void cellClick(View v)
    {
        TextView cell = (TextView) findViewById(v.getId());//assume this gives the XML id on activity_main
        //Check the content and make sure the cell is empty and the game isn't over
        String content = (String) cell.getText();
        if (content == "" && !isOver)
        {
            //Find the XY location values of the particular cell that was clicked
            switch (cell.getId())
            {
                case R.id.cell11:
                    xloc = 0; yloc = 0; break;
                case R.id.cell12:
                    xloc = 0; yloc = 1; break;
                case R.id.cell13:
                    xloc = 0; yloc = 2; break;
                case R.id.cell21:
                    xloc = 1; yloc = 0; break;
                case R.id.cell22:
                    xloc = 1; yloc = 1; break;
                case R.id.cell23:
                    xloc = 1; yloc = 2; break;
                case R.id.cell31:
                    xloc = 2; yloc = 0; break;
                case R.id.cell32:
                    xloc = 2; yloc = 1; break;
                case R.id.cell33:
                    xloc = 2; yloc = 2; break;
            }

            //Place the palyer's mark on specific X Y location on virtual board and displayed board
            board.placeMark(xloc,yloc,mark);
            cell.setText(mark);

            moveCount++;

            isOver = checkEnd(mark);

            if (mark.compareTo("X") == 0)
            {
                mark = "O";
            }
            else
            {
                mark = "X";
            }
        }
    }

    public void onRadioButtonClicked(View view)
    {
        //is button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        //check which button was clicked
        switch(view.getId())
        {

            //if user wants to be X just clear the board and wait for move
            case R.id.radio_X:

                if (checked)
                {
                    mark = "X"; clear();
                    break;
                }

            case R.id.radio_O:
                if(checked)
                {
                    mark = "O"; clear();
                    break;
                }
        }
    }

    private boolean checkEnd(String player)
    {
        //checks if virtual board for if there is a winner announce it
        if (board.isWinner())
        {
            announce(true, player);
            return true;
        }

        //check if we maxed out our moves
        else if (moveCount >= 9)
        {
            announce(false, player);
            return true;
        }

        return false;
    }

    private void announce(boolean endState, String player)
    {
        //check if its a win or a draw
        if (endState == true)
        {
            player = player + " wins!";
        }
        else
        {
            player = "It's a draw!";
        }

        //gets app context and setup the toast notification
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, player, Toast.LENGTH_LONG);
        toast.show();
    }

    private void clear()
    {
        //get the id list of all the textview cells
        int[] idList = {R.id.cell11, R.id.cell12, R.id.cell13, R.id.cell21, R.id.cell22, R.id.cell23, R.id.cell31, R.id.cell32, R.id.cell33 };
        TextView cell;
        //For each cell, clear the text with an empty string
        for (int item : idList)
        {
            cell = (TextView) findViewById(item);
            cell.setText("");
        }

        //reset the game state and clear virtual board
        isOver = false;
        moveCount = 0;
        board.clear();
    }
}
