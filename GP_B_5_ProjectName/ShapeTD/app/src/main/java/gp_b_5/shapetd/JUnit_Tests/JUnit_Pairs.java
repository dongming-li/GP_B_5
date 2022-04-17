package gp_b_5.shapetd.JUnit_Tests;

import junit.framework.TestCase;
import gp_b_5.shapetd.Core_Logic.Pair;

/**
 * Created by Joe on 10/30/2017.
 */

public class JUnit_Pairs extends TestCase
{
    Pair p0;
    Pair p1;
    protected void setUp()
    {
        p0 = new Pair();
        p1 = new Pair(10,30);
    }

    public void testPairInit()
    {
        //Test with empty constructor
        assertEquals(p0.getPairX(), 0);
        assertEquals(p0.getPairY(), 0);

        //Test p1 initialzied with x = 10, y = 30
        assertEquals(p1.getPairX(), 10);
        assertEquals(p1.getPairY(), 30);
    }
    public void testSetPair()
    {
        p0.setPair(64, 128);
        p1.setPair(256, 512);

        assertEquals(p0.getPairX(), 64);
        assertEquals(p0.getPairY(), 128);

        assertEquals(p1.getPairX(), 256);
        assertEquals(p1.getPairY(), 512);
    }
    public void testSetX()
    {
        p0.setPairX(1024);
        p1.setPairX(2048);

        assertEquals(p0.getPairX(), 1024);
        assertEquals(p0.getPairY(), 0);

        assertEquals(p1.getPairX(), 2048);
        assertEquals(p1.getPairY(), 30);
    }
    public void testSetY()
    {
        p0.setPairY(4096);
        p1.setPairY(8192);

        assertEquals(p0.getPairX(),0);
        assertEquals(p0.getPairY(), 4096);

        assertEquals(p1.getPairX(),10);
        assertEquals(p1.getPairY(), 8192);
    }
}
