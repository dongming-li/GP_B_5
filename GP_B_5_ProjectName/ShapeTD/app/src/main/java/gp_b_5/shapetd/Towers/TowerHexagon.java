package gp_b_5.shapetd.Towers;

import gp_b_5.shapetd.Core_Logic.GameEntity;

/**
 * Created by Nick on 9/28/2017.
 */

public class TowerHexagon extends GameEntity {

    public TowerHexagon(){ super(Tower.T_HEXAGON);}

    public void upgrade(){
        int damage = this.getDamage() + 1;
        int speed = this.getSpeed() + 1;
        this.setSpeed(speed);
        this.setDamage(damage);
    }
}
