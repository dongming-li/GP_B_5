package com.example.zachary.tictactoe;

/**
 * Created by zachary on 9/10/17.
 */

public class GameBoard {
    //Declare global variables
    private String[][] theBoard = new String[3][3];

    public GameBoard()
    {
        //initiate the game board with blanks
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                theBoard[i][j] = "";
            }
        }
    }

    public String[][] getBoard()
    {
        return theBoard;
    }

    //takes in a mark and a location and adds marker to the board
    public void placeMark(int xloc, int yloc, String mark)
    {
        if (theBoard[xloc][yloc] == "") theBoard[xloc][yloc] = mark;
    }

    //Determines if there winner by diagonal, then rows and collumns and ensures that the cells are not blank
    public boolean isWinner()
    {
        if (theBoard[0][0] == theBoard[1][1] && theBoard[0][0] == theBoard[2][2] && theBoard[0][0].compareTo("") != 0)
        {
            return true;
        }

        if (theBoard[2][0] == theBoard[1][1] && theBoard[2][0] == theBoard[0][2] && theBoard[2][0].compareTo("") != 0)
        {
            return true;
        }

        //check rows and collums
        for (int i = 0; i < 3; i++)
        {
            if (theBoard[i][0] == theBoard[i][1] && theBoard[i][0] == theBoard[i][2] && theBoard[i][0].compareTo("") != 0)
            {
                return true;
            }
            if (theBoard[0][i] == theBoard[1][i] && theBoard[0][i] == theBoard[2][i] && theBoard[0][i].compareTo("") != 0)
            {
                return true;
            }
        }

        return false;
    }

    public void clear()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                theBoard[i][j] = "";
            }
        }
    }
}
