package gp_b_5.shapetd.Core_Logic;

/**
 * Created by Joe on 11/5/2017.
 */

public class GameEntity
{
    protected enum Tower {
        T_CIRCLE, T_TRIANGLE, T_SQUARE, T_PENTAGON, T_HEXAGON, T_BASE
    }
    protected enum Enemy {
        E_FAST, E_MEDIUM, E_HEAVY
    }
    private int level, damage, cost, speed, range, health, pathPos;
    private Pair Coords;
    private Tower t_type;
    private Enemy e_type;
    private GameEntity target = null;

    public GameEntity(Tower t){
        this.t_type = t;
        this.e_type = null;

        switch(t){
            case T_CIRCLE:
                this.damage = 10;
                this.cost = 50;
                this.speed = 5;
                this.range = 1;
                this.level = 1;
                Coords = new Pair(0, 0);
                break;
            case T_TRIANGLE:
                this.damage = 10;
                this.cost = 75;
                this.speed = 3;
                this.range = 3;
                this.level = 1;
                Coords = new Pair(0, 0);
                break;
            case T_SQUARE:
                this.damage = 20;
                this.cost = 100;
                this.speed = 4;
                this.range = 2;
                this.level = 1;
                Coords = new Pair(0, 0);
                break;
            case T_PENTAGON:
                this.damage = 20;
                this.cost = 150;
                this.speed = 3;
                this.range = 3;
                this.level = 1;
                Coords = new Pair(0, 0);
                break;
            case T_HEXAGON:
                this.damage = 30;
                this.cost = 200;
                this.speed = 2;
                this.range = 4;
                this.level = 1;
                Coords = new Pair(0, 0);
                break;
            case T_BASE:
                this.health = 100;
                break;
            default:
                break;
        }
    }
    public GameEntity(Enemy e){
        this.e_type = e;
        this.t_type = null;

        switch(e){
            case E_FAST:
                this.speed = 4;
                this.health = 20;
                Coords = new Pair(0, 0);
                break;
            case E_MEDIUM:
                this.speed = 7;
                this.health = 35;
                Coords = new Pair(0, 0);
                break;
            case E_HEAVY:
                this.speed = 10;
                this.health = 50;
                Coords = new Pair(0, 0);
                break;
        }
    }

    public int getDamage()
    {
        return this.damage;
    }
    public int getRange()
    {
        return this.range;
    }
    public int getCost()
    {
        return this.cost;
    }
    public int getLevel()
    {
        return this.level;
    }
    public int getSpeed()
    {
        return this.speed;
    }
    public int getHealth() {
        return this.health;
    }
    public int getPathPos(){ return this.pathPos; }
    public int getPairX() {
        return this.Coords.getPairX();
    }
    public int getPairY() {
        return this.Coords.getPairY();
    }
    public GameEntity getTarget(){ return this.target; }
    public boolean hasTarget(){ return this.target != null;}
    public void setPair(Pair p){ this.Coords = p; }
    public void setPairX(int x) {
        this.Coords.setPairX(x);
    }
    public void setPairY(int y) {
        this.Coords.setPairY(y);
    }
    public void setPair(int x, int y) {
        this.Coords.setPair(x, y);
    }
    public void setPathPos(int i){ this.pathPos = i; }
    public void setHealth(int i) {
        this.health = i;
    }
    public void setDamage(int i)
    {
        this.damage = i;
    }
    public void setRange(int i)
    {
        this.range = i;
    }
    public void setCost(int i)
    {
        this.cost = i;
    }
    public void setLevel(int i)
    {
        this.level = i;
    }
    public void setSpeed(int i) { this.speed = i; }

    public void setTarget(GameEntity e){
        if(e.e_type != null){
            this.target = e;
        }
    }

    public boolean isEnemy(){ return this.e_type != null; }
    public boolean isTower(){ return this.t_type != null; }

    public boolean isEnemyDead() {
        return this.health <= 0;
    }

    //TODO
    public void destroyEnemy(){
        if(this.e_type != null) {

            return;
        }
    }

    public void increasePathPos(){
        if(this.e_type != null) {
            this.pathPos++;
        }
    }

    public int damageBase(int d){
        if(this.t_type != null){
            this.health -= d;

            return this.health;
        }

        return -1;
    }

    public boolean shoot(){
        if(t_type != null){
            if(this.target != null){
                target.health -= this.damage;

                return true;
            }
        }
        return false;
    }

    public boolean isEnemyInRange(GameEntity Enemy) {
        int coordXPos = this.getPairX() + this.range;
        int coordXNeg = this.getPairX() - this.range;
        int coordYPos = this.getPairY() + this.range;
        int coordYNeg = this.getPairY() - this.range;

        int eCordX = Enemy.getPairX();
        int eCordY = Enemy.getPairY();

        for(int i = coordXNeg; i < coordXPos; i++)
        {
            for(int j = coordYNeg; j < coordYPos; j++)
            {
                if((i == eCordX) && ( j == eCordY))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public void setTargetByRange(GameEntity Enemy) {
        if(isEnemyInRange(Enemy)) {
            this.setTarget(Enemy);
        }
    }
}
