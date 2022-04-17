package gp_b_5.shapetd.backend;

/**
 * Created by Zachary Evans on 9/24/17.
 */

import gp_b_5.shapetd.Core_Logic.GameEntity;
import gp_b_5.shapetd.Core_Logic.Pair;

import static gp_b_5.shapetd.backend.Cell.TerrainType.Build;

/**
 * Data type for the cells that make up the gameboard. Should have fields that indicate whether the space is a empty and if not,
 * what type of structure is on the cell (tower/obstacle/path)
 *
 * Sorta combining/conflicting with Nick's Terrain abstract class but we'll see.
 *
 * Need to:
 *      Define type of cell (terrainType)
 *      Coordinates
 *      key?
 *
 * Using Pair class to define coordinates
 *
 * UPDATE: going to make effort to eliminate Terrain Class and simply integrate those attributes to this class
 *
 */

public class Cell{

    //make work to eliminate the need of this variable
    private TerrainType terrain;
    //variable to determine if the cell has been selected
    private boolean isSelected;

    //variables to hold selected/unselected images (not sure if needed but they're here)
    private Integer imageIDUnselected;
    private Integer imageIDSelected;

    //holds coordinates of the cell
    private Pair coords;

    //list of enemies? tower? something like that
    private GameEntity entity; //hold cell's entity attributes

    //holds the enum value
    private TerrainType type;

    boolean isTower = false;

    /**
     * Terrain attributes ported to this class
     */

    public enum TerrainType{

        Build("Build", true, false), Path("Path", false, true), Obstacle("Obstacle", false, false);

        String terrainName;
        boolean buildable;
        boolean walkable;

        TerrainType(String terrainName, boolean build, boolean walk){
            this.terrainName = terrainName;
            buildable = build;
            walkable = walk;
        }
    }

    //terrain ID for cell - 0 is empty buildable terrain, 1 is pathway for enemy to walk on, 2 is an obstacle
    private int terrainID;

    private boolean walkable;

    private boolean buildable;

    private String terrainName;




    //generic constructor, always set default terrain to empty
    public Cell(){
        terrainID = 0;
        coords = new Pair(0, 0);
        type = Build;
        walkable = type.walkable;
        buildable = type.buildable;
        terrainName = type.terrainName;
    }

    //Constructor that takes in coordinates, takes in terrain as integer.
    public Cell(int id, int x, int y){
        terrainID = id;
        coords = new Pair(x, y);
        //set type of terrain, default to empty
        switch(id){
            case 0:
                type = Build;
                break;
            case 1:
                type = TerrainType.Path;
                break;
            case 2:
                type = TerrainType.Obstacle;
                break;
            default:
                type = Build;
                terrainID = 0;
                break;
        }
        //set attributes from enum for easy access outside of class
        walkable = type.walkable;
        buildable = type.buildable;
        terrainName = type.terrainName;

    }

    //constructor that takes in coordinates, takes in terrain as enum type
    public Cell(TerrainType terrainType, int x, int y){
        type = terrainType;
        coords = new Pair(x, y);
        //set type of terrain, default
        switch(type){
            case Build:
                terrainID = 0;
                break;
            case Path:
                terrainID = 1;
                break;
            case Obstacle:
                terrainID = 2;
                break;
            default:
                terrainID = 0;
                type = Build;
        }

        //set attributes from enum for easy access outside of class
        walkable = type.walkable;
        buildable = type.buildable;
        terrainName = type.terrainName;
    }

    //set terrain by ID;
    public void setTerrain(int id) {
        switch(id){
            case 0:
                type = Build;
                break;
            case 1:
                type = TerrainType.Path;
                break;
            case 2:
                type = TerrainType.Obstacle;
                break;
            default:
                type = Build;
                terrainID = 0;
                break;
        }
        setBuildable(type.buildable);
        setWalkable(type.walkable);
        terrainName = type.terrainName;
    }

    public void setTerrain(TerrainType type){
        switch(type){
            case Build:
                terrainID = 0;
                break;
            case Path:
                terrainID = 1;
                break;
            case Obstacle:
                terrainID = 2;
                break;
            default:
                terrainID = 0;
                type = Build;
        }
        setBuildable(type.buildable);
        setWalkable(type.walkable);
        terrainName = type.terrainName;

    }

    /**
     * Getters and Setters
     */

    public String getTerrainName(){
        return terrainName;
    }

    public int getTerrainID(){
        return terrainID;
    }

    public TerrainType getTerrainType(){
        return type;
    }

    public boolean getWalkable(){
        return walkable;
    }

    public void setWalkable(boolean walk){
        walkable = walk;
    }

    public boolean getBuildable(){
        return buildable;
    }

    public void setBuildable(boolean build){
        buildable = build;
    }

    public int getXCoord(){
        return coords.getPairX();
    }

    public void setXCoord(int x){
        coords.setPairX(x);
    }

    public void setYCoord(int y){
        coords.setPairY(y);
    }

    public Pair getCoords(){
        return coords;
    }

    public void setCoords(Pair coordinates){
        coords = coordinates;
    }

    public int getYCoord(){
        return coords.getPairY();
    }

    public boolean isSelected(){
        return isSelected;
    }

    public void setSelection(boolean select){
        isSelected = select;
    }
}