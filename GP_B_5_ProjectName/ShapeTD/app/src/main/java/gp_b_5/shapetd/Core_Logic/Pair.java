package gp_b_5.shapetd.Core_Logic;

/**
 * Created by Joseph Nunez on 10/27/2017.
 */

public class Pair
{
    private int x;
    private int y;
    public Pair(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public Pair()
    {
        this.x = 0;
        this.y = 0;
    }

    public int getPairX()
    {
        return this.x;
    }
    public int getPairY()
    {
        return this.y;
    }
    public void setPairX(int x)
    {
        this.x = x;
    }
    public void setPairY(int y)
    {
        this.y = y;
    }
    public void setPair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
