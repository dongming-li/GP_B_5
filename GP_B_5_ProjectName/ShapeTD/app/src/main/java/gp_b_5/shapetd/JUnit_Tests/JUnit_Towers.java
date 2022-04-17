package gp_b_5.shapetd.JUnit_Tests;

import junit.framework.TestCase;

import gp_b_5.shapetd.Core_Logic.Pair;
import gp_b_5.shapetd.Towers.TowerCircle;
import gp_b_5.shapetd.Towers.TowerHexagon;
import gp_b_5.shapetd.Towers.TowerPentagon;
import gp_b_5.shapetd.Towers.TowerSquare;
import gp_b_5.shapetd.Towers.TowerTriangle;

/**
 * Created by Joseph Nunez on 10/25/2017.
 */

public class JUnit_Towers extends TestCase
{
    private TowerCircle     TC;
    private TowerHexagon    TH;
    private TowerPentagon   TP;
    private TowerTriangle   TT;
    private TowerSquare     TS;
    protected void setUp()
    {
        TC = new TowerCircle();
        TH = new TowerHexagon();
        TP = new TowerPentagon();
        TT = new TowerTriangle();
        TS = new TowerSquare();
    }
    public void testTowerUpgradeDamage()
    {
        assertEquals(TC.getDamage(), 10);
        assertEquals(TH.getDamage(), 30);
        assertEquals(TP.getDamage(), 20);
        assertEquals(TT.getDamage(), 10);
        assertEquals(TS.getDamage(), 20);

        TC.upgrade();
        TH.upgrade();
        TP.upgrade();
        TT.upgrade();
        TS.upgrade();

        assertEquals(TC.getDamage(), 11);
        assertEquals(TH.getDamage(), 31);
        assertEquals(TP.getDamage(), 21);
        assertEquals(TT.getDamage(), 11);
        assertEquals(TS.getDamage(), 21);
    }
    public void testTowerUpgradeRange()
    {
        assertEquals(TC.getRange(), 1);
        assertEquals(TH.getRange(), 4);
        assertEquals(TP.getRange(), 3);
        assertEquals(TT.getRange(), 3);
        assertEquals(TS.getRange(), 2);

    }
    public void testTowerUpgradeCost()
    {
        assertEquals(TC.getCost(), 50);
        assertEquals(TH.getCost(), 200);
        assertEquals(TP.getCost(), 150);
        assertEquals(TT.getCost(), 75);
        assertEquals(TS.getCost(), 100);
    }
    public void testTowerUpgradeFireRate()
    {
        assertEquals(TC.getSpeed(), 1);
        assertEquals(TH.getSpeed(), 4);
        assertEquals(TP.getSpeed(), 3);
        assertEquals(TT.getSpeed(), 3);
        assertEquals(TS.getSpeed(), 2);
    }
    public void testTowerUpgradeLevel()
    {
        assertEquals(TC.getLevel(), 1);
        assertEquals(TH.getLevel(), 1);
        assertEquals(TP.getLevel(), 1);
        assertEquals(TT.getLevel(), 1);
        assertEquals(TS.getLevel(), 1);
    }
    public void testSetDamage()
    {
        TC.setDamage(100001);
        TH.setDamage(100002);
        TP.setDamage(100003);
        TT.setDamage(100004);
        TS.setDamage(100005);
        assertEquals(TC.getDamage(), 100001);
        assertEquals(TH.getDamage(), 100002);
        assertEquals(TP.getDamage(), 100003);
        assertEquals(TT.getDamage(), 100004);
        assertEquals(TS.getDamage(), 100005);

        TC.setDamage(1);
        TH.setDamage(2);
        TP.setDamage(4);
        TT.setDamage(8);
        TS.setDamage(16);
        assertEquals(TC.getDamage(), 1);
        assertEquals(TH.getDamage(), 2);
        assertEquals(TP.getDamage(), 4);
        assertEquals(TT.getDamage(), 8);
        assertEquals(TS.getDamage(), 16);

        TC.upgrade();
//        TH.upgrade();
        TP.upgrade();
        TT.upgrade();
        TS.upgrade();
        assertEquals(TC.getDamage(), 2);
        assertEquals(TP.getDamage(), 5);
        assertEquals(TT.getDamage(), 9);
        assertEquals(TS.getDamage(), 17);
    }
    public void testSetRange()
    {
        TC.setRange(1001);
        TH.setRange(1002);
        TP.setRange(1003);
        TT.setRange(1004);
        TS.setRange(1005);
        assertEquals(TC.getRange(), 1001);
        assertEquals(TH.getRange(), 1002);
        assertEquals(TP.getRange(), 1003);
        assertEquals(TT.getRange(), 1004);
        assertEquals(TS.getRange(), 1005);

        TC.setRange(1001);
        TH.setRange(1002);
        TP.setRange(1003);
        TT.setRange(1004);
        TS.setRange(1005);
        TC.setRange(1);
        TH.setRange(2);
        TP.setRange(3);
        TT.setRange(4);
        TS.setRange(5);
        TC.setRange(10);
        TH.setRange(30);
        TP.setRange(20);
        TT.setRange(50);
        TS.setRange(40);

        assertEquals(TC.getRange(), 10);
        assertEquals(TH.getRange(), 30);
        assertEquals(TP.getRange(), 20);
        assertEquals(TT.getRange(), 50);
        assertEquals(TS.getRange(), 40);
    }
    public void testSetCost()
    {
        assertEquals(TC.getCost(), 50);
        assertEquals(TH.getCost(), 200);
        assertEquals(TP.getCost(), 150);
        assertEquals(TT.getCost(), 75);
        assertEquals(TS.getCost(), 100);

        TC.setCost(1000);
        TH.setCost(2000);
        TP.setCost(3000);
        TT.setCost(4000);
        TS.setCost(5000);
        assertEquals(TC.getCost(), 1000);
        assertEquals(TH.getCost(), 2000);
        assertEquals(TP.getCost(), 3000);
        assertEquals(TT.getCost(), 4000);
        assertEquals(TS.getCost(), 5000);
    }
    public void testSetLevel()
    {
        assertEquals(TC.getLevel(), 1);
        assertEquals(TH.getLevel(), 1);
        assertEquals(TP.getLevel(), 1);
        assertEquals(TT.getLevel(), 1);
        assertEquals(TS.getLevel(), 1);

        TC.setLevel(9999);
        TH.setLevel(9998);
        TP.setLevel(9997);
        TT.setLevel(9996);
        TS.setLevel(9995);
        assertEquals(TC.getLevel(), 9999);
        assertEquals(TH.getLevel(), 9998);
        assertEquals(TP.getLevel(), 9997);
        assertEquals(TT.getLevel(), 9996);
        assertEquals(TS.getLevel(), 9995);
    }
    public void testSetFireRate()
    {
        assertEquals(TC.getSpeed(), 1);
        assertEquals(TH.getSpeed(), 4);
        assertEquals(TP.getSpeed(), 3);
        assertEquals(TT.getSpeed(), 3);
        assertEquals(TS.getSpeed(), 2);

        TC.setSpeed(20);
        TH.setSpeed(25);
        TP.setSpeed(30);
        TT.setSpeed(35);
        TS.setSpeed(40);
        assertEquals(TC.getSpeed(), 20);
        assertEquals(TH.getSpeed(), 25);
        assertEquals(TP.getSpeed(), 30);
        assertEquals(TT.getSpeed(), 35);
        assertEquals(TS.getSpeed(), 40);
    }
    public void testInitPair()
    {
        Pair p = new Pair(0,0);

        assertEquals(TC.getPairX(), p.getPairX());
        assertEquals(TC.getPairY(), p.getPairY());
        assertEquals(TH.getPairX(), p.getPairX());
        assertEquals(TH.getPairY(), p.getPairY());
        assertEquals(TP.getPairX(), p.getPairX());
        assertEquals(TP.getPairY(), p.getPairY());
        assertEquals(TT.getPairX(), p.getPairX());
        assertEquals(TT.getPairY(), p.getPairY());
        assertEquals(TS.getPairX(), p.getPairX());
        assertEquals(TS.getPairY(), p.getPairY());
    }
    public void testSetPairX()
    {
        assertEquals(TC.getPairX(), 0);
        assertEquals(TH.getPairX(), 0);
        assertEquals(TP.getPairX(), 0);
        assertEquals(TT.getPairX(), 0);
        assertEquals(TS.getPairX(), 0);

        TC.setPairX(10);
        TH.setPairX(20);
        TP.setPairX(40);
        TT.setPairX(80);
        TS.setPairX(160);

        assertEquals(TC.getPairX(), 10);
        assertEquals(TH.getPairX(), 20);
        assertEquals(TP.getPairX(), 40);
        assertEquals(TT.getPairX(), 80);
        assertEquals(TS.getPairX(), 160);
    }
    public void testSetPairY()
    {
        assertEquals(TC.getPairY(), 0);
        assertEquals(TH.getPairY(), 0);
        assertEquals(TP.getPairY(), 0);
        assertEquals(TT.getPairY(), 0);
        assertEquals(TS.getPairY(), 0);

        TC.setPairY(160);
        TH.setPairY(80);
        TP.setPairY(40);
        TT.setPairY(20);
        TS.setPairY(10);

        assertEquals(TC.getPairY(), 160);
        assertEquals(TH.getPairY(), 80);
        assertEquals(TP.getPairY(), 40);
        assertEquals(TT.getPairY(), 20);
        assertEquals(TS.getPairY(), 10);
    }
    public void testSetPair()
    {
        assertEquals(TC.getPairY(), 0);
        assertEquals(TH.getPairY(), 0);
        assertEquals(TP.getPairY(), 0);
        assertEquals(TT.getPairY(), 0);
        assertEquals(TS.getPairY(), 0);
        assertEquals(TC.getPairX(), 0);
        assertEquals(TH.getPairX(), 0);
        assertEquals(TP.getPairX(), 0);
        assertEquals(TT.getPairX(), 0);
        assertEquals(TS.getPairX(), 0);

        TC.setPair(5, 10);
        TH.setPair(15, 20);
        TP.setPair(25, 30);
        TT.setPair(35, 40);
        TS.setPair(45, 50);

        assertEquals(TC.getPairX(), 5);
        assertEquals(TH.getPairX(), 15);
        assertEquals(TP.getPairX(), 25);
        assertEquals(TT.getPairX(), 35);
        assertEquals(TS.getPairX(), 45);
        assertEquals(TC.getPairY(), 10);
        assertEquals(TH.getPairY(), 20);
        assertEquals(TP.getPairY(), 30);
        assertEquals(TT.getPairY(), 40);
        assertEquals(TS.getPairY(), 50);
    }
}
