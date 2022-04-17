package gp_b_5.shapetd.Terrains;

/**
 * Created by Nick on 9/21/2017.
 */

public class TerrainSpawn extends TerrainType {

    public TerrainSpawn() {
        super(Terrain.T_SPAWN);
    }

    @Override
    public String toString() {
        return "spawn";
    }
}
