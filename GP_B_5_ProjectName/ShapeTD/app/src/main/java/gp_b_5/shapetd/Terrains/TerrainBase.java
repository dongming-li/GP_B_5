package gp_b_5.shapetd.Terrains;

/**
 * Created by Nick on 9/21/2017.
 */

public class TerrainBase extends TerrainType {
    int health;

    public TerrainBase() {
        super(Terrain.T_BASE);
        health = 100;
    }

    /**
     * Returns current base health.
     * @return health
     */
    public int getHealth(){
        return health;
    }

    /**
     * Reduces base health by given amount.
     * Returns 0 when health is still greater than 0, else
     * returns -1.
     * @param d given damage amount
     * @return value if base is still alive
     */
    public int damageBase(int d){
        health -= d;

        if(health <= 0){
            return -1;
        }

        return 0;
    }

    @Override
    public String toString() {
        return "base";
    }
}
