package gp_b_5.shapetd.Towers;

import gp_b_5.shapetd.Core_Logic.GameEntity;

/**
 * Created by Nick on 9/28/2017.
 */

public class TowerSquare extends GameEntity {

    public TowerSquare(){ super(Tower.T_SQUARE);}

    public void upgrade(){
        int damage = this.getDamage() + 1;
        int speed = this.getSpeed() + 1;
        this.setSpeed(speed);
        this.setDamage(damage);
    }
}
