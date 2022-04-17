package gp_b_5.shapetd.JUnit_Tests;

import junit.framework.TestCase;

import java.util.ArrayList;

import gp_b_5.shapetd.Core_Logic.GameEntity;
import gp_b_5.shapetd.Enemies.EnemyFast;
import gp_b_5.shapetd.Enemies.EnemyHeavy;
import gp_b_5.shapetd.Enemies.EnemyMedium;
import gp_b_5.shapetd.Towers.TowerCircle;
import gp_b_5.shapetd.Towers.TowerHexagon;
import gp_b_5.shapetd.Towers.TowerPentagon;
import gp_b_5.shapetd.Towers.TowerSquare;
import gp_b_5.shapetd.Towers.TowerTriangle;
import gp_b_5.shapetd.Towers.TowerBase;

/**
 * Created by Joe on 11/5/2017.
 */

public class JUnit_GameQueue extends TestCase {

    private EnemyFast EF;
    private EnemyHeavy  EH;
    private EnemyMedium EM;
    private TowerCircle TC;
    private TowerHexagon TH;
    private TowerPentagon TP;
    private TowerTriangle TT;
    private TowerSquare TS;
    private TowerBase Base;

    ArrayList<GameEntity> GEList;

    protected void setUp()
    {
        EF = new EnemyFast();
        EH = new EnemyHeavy();
        EM = new EnemyMedium();
        TC = new TowerCircle();
        TH = new TowerHexagon();
        TP = new TowerPentagon();
        TT = new TowerTriangle();
        TS = new TowerSquare();
        Base = new TowerBase();

        GEList = new ArrayList<>();

        GEList.add(EF);
        GEList.add(EH);
        GEList.add(EM);
        GEList.add(TC);
        GEList.add(TH);
        GEList.add(TP);
        GEList.add(TT);
        GEList.add(TS);
    }
    public void testHasTarget() {
        TC.setTarget(EF);
        assertEquals(TC.hasTarget(), true);
    }
    public void testHasMultiTarget() {
        TC.setTarget(EF);
        TH.setTarget(EF);
        TP.setTarget(EF);
        TS.setTarget(EF);
        TT.setTarget(EF);

        assertEquals(TC.hasTarget(), true);
        assertEquals(TH.hasTarget(), true);
        assertEquals(TP.hasTarget(), true);
        assertEquals(TS.hasTarget(), true);
        assertEquals(TT.hasTarget(), true);
    }
    public void testGetTarget() {
        TC.setTarget(EF);
        assertEquals(TC.getTarget(), EF);
    }
    public void testGetMultiTarget() {
        TC.setTarget(EF);
        TH.setTarget(EF);
        TP.setTarget(EF);
        TS.setTarget(EF);
        TT.setTarget(EF);

        assertEquals(TC.getTarget(), EF);
        assertEquals(TH.getTarget(), EF);
        assertEquals(TP.getTarget(), EF);
        assertEquals(TS.getTarget(), EF);
        assertEquals(TT.getTarget(), EF);
    }
    public void testShootNoTarget() {
        assertEquals(TC.shoot(), false);
    }
    public void testShootTarget() {
        TC.setTarget(EF);
        assertEquals(TC.shoot(), true);
    }
    public void testDamageEnemy() {
        assertEquals(EF.getHealth(), 20);
        TC.setTarget(EF);
        TC.shoot();
        assertEquals(EF.getHealth(), 10);
    }
    public void testMultiDamageEnemy() {
        TC.setTarget(EF);
        TH.setTarget(EF);
        TP.setTarget(EF);
        TS.setTarget(EF);
        TT.setTarget(EF);

        TC.shoot(); // 10
        TH.shoot(); // 30
        TP.shoot(); // 20
        TS.shoot(); // 20
        TT.shoot(); // 10

        // 20 - 10 - 30 - 20 - 20 - 10 = -70
        assertEquals(EF.getHealth(), -70);
    }
    public void testEnemyIsDead() {
        TC.setTarget(EF);
        TH.setTarget(EF);
        TP.setTarget(EF);
        TS.setTarget(EF);
        TT.setTarget(EF);

        TC.shoot(); // 10
        TH.shoot(); // 30
        TP.shoot(); // 20
        TS.shoot(); // 20
        TT.shoot(); // 10

        assertEquals(EF.isEnemyDead(), true);
    }
    public void testEnemyNotDead() {
        TC.setTarget(EF);

        TC.shoot(); // 10

        assertEquals(EF.isEnemyDead(), false);
    }
    public void testBaseHealth() {
        assertEquals(Base.getHealth(), 100);
    }
    public void testDamageBase() {
        Base.damageBase(1);
        assertEquals(Base.getHealth(), 99);
    }
    public void testIsEnemyInRange() {
        TC.setPair(10, 10);
        assertEquals(TC.getPairX(), 10);
        assertEquals(TC.getPairY(), 10);
        assertEquals(TC.getRange(), 3);

        EF.setPair(8, 11);
        assertEquals(EF.getPairX(), 8);
        assertEquals(EF.getPairY(), 11);

        assertEquals(TC.isEnemyInRange(EF), true);
    }
    public void testSetTargetByRange() {
        TP.setPair(4, 4);
        EM.setPair(2, 2);

        TP.setTargetByRange(EM);

        assertEquals(TP.getTarget(), EM);
    }
}
