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

/**
 * Created by Joseph Nunez on 11/4/2017.
 */

public class JUnit_GameEntity extends TestCase {
    private EnemyFast EF;
    private EnemyHeavy EH;
    private EnemyMedium EM;

    private TowerCircle     TC;
    private TowerHexagon    TH;
    private TowerPentagon   TP;
    private TowerTriangle   TT;
    private TowerSquare     TS;

    private ArrayList<GameEntity> GE;

    protected void setUp() {
        EF = new EnemyFast();
        EH = new EnemyHeavy();
        EM = new EnemyMedium();

        TC = new TowerCircle();
        TH = new TowerHexagon();
        TP = new TowerPentagon();
        TT = new TowerTriangle();
        TS = new TowerSquare();

        GE = new ArrayList<>();
    }
    public void testGEArrayList() {
        GE.add(EF);
        GE.add(EH);
        GE.add(EM);
        GE.add(TC);
        GE.add(TH);
        GE.add(TP);
        GE.add(TT);
        GE.add(TS);

        assertEquals(GE.get(0), EF);
        assertEquals(GE.get(1), EH);
        assertEquals(GE.get(2), EM);
        assertEquals(GE.get(3), TC);
        assertEquals(GE.get(4), TH);
        assertEquals(GE.get(5), TP);
        assertEquals(GE.get(6), TT);
        assertEquals(GE.get(7), TS);
    }
}



















