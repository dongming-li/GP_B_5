package gp_b_5.shapetd.Terrains;


public abstract class TerrainType {
	/**
	 * Used to instantiate terrain types
	 */
	public enum Terrain {
		T_PATH, T_BUILD, T_OBSTACLE, T_BASE, T_SPAWN
	}

	boolean build, path;
	int xPos, yPos;
	
	
	public TerrainType(Terrain t){

		switch(t){
			case T_PATH:
				this.build = false;
				this.path = true;
				break;
			case T_BUILD:
				this.build = true;
				this.path = false;
				break;
			case T_OBSTACLE:
				this.build = false;
				this.path = false;
				break;
			case T_BASE:
				this.build = false;
				this.path = true;
				break;
			case T_SPAWN:
				this.build = false;
				this.path = true;
				break;
		}
		
		return;
	}

	/**
	 * Checks if the terrain type can be built on
	 * @return terrain's build boolean
	 */
	boolean isBuildable(){
		return this.build;
	}

	/**
	 * Checks if the terrain type can be walked on
	 * @return terrain's path boolean
	 */
	boolean isWalkable(){
		return this.path;
	}

	public abstract String toString();

}
