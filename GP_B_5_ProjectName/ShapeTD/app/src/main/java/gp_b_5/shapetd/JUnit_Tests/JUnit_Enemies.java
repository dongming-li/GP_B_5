package gp_b_5.shapetd.JUnit_Tests;

import junit.framework.TestCase;

import gp_b_5.shapetd.Enemies.EnemyFast;
import gp_b_5.shapetd.Enemies.EnemyHeavy;
import gp_b_5.shapetd.Enemies.EnemyMedium;

/**
 * Created by Joseph Nunez on 10/26/2017.
 */

public class JUnit_Enemies extends TestCase
{
    private EnemyFast EF;
    private EnemyHeavy  EH;
    private EnemyMedium EM;

    protected void setUp()
    {
        EF = new EnemyFast();
        EH = new EnemyHeavy();
        EM = new EnemyMedium();
    }
    public void testEnemySpeed()
    {
        assertEquals(EF.getSpeed(), 10);
        assertEquals(EH.getSpeed(), 4);
        assertEquals(EM.getSpeed(), 7);
    }
    public void testEnemyHealth()
    {
        assertEquals(EF.getHealth(), 20);
        assertEquals(EH.getHealth(), 50);
        assertEquals(EM.getHealth(), 35);
    }
    public void testEnemySetHealth()
    {
        assertEquals(EF.getHealth(), 20);
        assertEquals(EH.getHealth(), 50);
        assertEquals(EM.getHealth(), 35);

        EF.setHealth(40);
        EH.setHealth(100);
        EM.setHealth(70);

        assertEquals(EF.getHealth(), 40);
        assertEquals(EH.getHealth(), 100);
        assertEquals(EM.getHealth(), 70);
    }
    public void testEnemyDamage()
    {
        assertEquals(EF.getHealth(), 20);
        assertEquals(EH.getHealth(), 50);
        assertEquals(EM.getHealth(), 35);

        //EF.damageEnemy(10);
        //EH.damageEnemy(10);
        //EM.damageEnemy(10);

        assertEquals(EF.getHealth(), 10);
        assertEquals(EH.getHealth(), 40);
        assertEquals(EM.getHealth(), 25);

        assertEquals(EF.isEnemyDead(), false);
        assertEquals(EH.isEnemyDead(), false);
        assertEquals(EM.isEnemyDead(), false);
    }
    public void testEnemyDies()
    {
        assertEquals(EF.getHealth(), 20);
        assertEquals(EH.getHealth(), 50);
        assertEquals(EM.getHealth(), 35);

        //EF.damageEnemy(20);
        //EH.damageEnemy(50);
        //EM.damageEnemy(35);

        assertEquals(EF.getHealth(), 0);
        assertEquals(EH.getHealth(), 0);
        assertEquals(EM.getHealth(), 0);

        assertEquals(EF.isEnemyDead(), true);
        assertEquals(EH.isEnemyDead(), true);
        assertEquals(EM.isEnemyDead(), true);
    }
    public void testGetPairX()
    {
        assertEquals(EF.getPairX(), 0);
        assertEquals(EH.getPairX(), 0);
        assertEquals(EM.getPairX(), 0);
    }
    public void testGetPairY()
    {
        assertEquals(EF.getPairY(), 0);
        assertEquals(EH.getPairY(), 0);
        assertEquals(EM.getPairY(), 0);
    }
    public void testSetPairX()
    {
        assertEquals(EF.getPairX(), 0);
        assertEquals(EH.getPairX(), 0);
        assertEquals(EM.getPairX(), 0);

        EF.setPairX(100);
        EH.setPairX(200);
        EM.setPairX(400);

        assertEquals(EF.getPairX(), 100);
        assertEquals(EH.getPairX(), 200);
        assertEquals(EM.getPairX(), 400);
    }
    public void testSetPairY()
    {
        assertEquals(EF.getPairY(), 0);
        assertEquals(EH.getPairY(), 0);
        assertEquals(EM.getPairY(), 0);

        EF.setPairY(1000);
        EH.setPairY(2000);
        EM.setPairY(4000);

        assertEquals(EF.getPairY(), 1000);
        assertEquals(EH.getPairY(), 2000);
        assertEquals(EM.getPairY(), 4000);
    }
    public void testSetPair()
    {
        assertEquals(EF.getPairX(), 0);
        assertEquals(EH.getPairX(), 0);
        assertEquals(EM.getPairX(), 0);
        assertEquals(EF.getPairY(), 0);
        assertEquals(EH.getPairY(), 0);
        assertEquals(EM.getPairY(), 0);

        EF.setPair(1, 2);
        EH.setPair(3, 4);
        EM.setPair(5, 6);

        assertEquals(EF.getPairX(), 1);
        assertEquals(EH.getPairX(), 3);
        assertEquals(EM.getPairX(), 5);
        assertEquals(EF.getPairY(), 2);
        assertEquals(EH.getPairY(), 4);
        assertEquals(EM.getPairY(), 6);
    }
}
