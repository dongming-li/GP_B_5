package gp_b_5.shapetd.Terrains;

public class TerrainObstacle extends TerrainType{

	public TerrainObstacle() {
		super(Terrain.T_OBSTACLE);
	}

	@Override
	public String toString() {
		return "obstacle";
	}
}
