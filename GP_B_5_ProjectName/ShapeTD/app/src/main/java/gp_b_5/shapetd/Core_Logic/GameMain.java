package gp_b_5.shapetd.Core_Logic;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Created by Nick on 10/22/2017.
 */

public class GameMain {
    PriorityQueue<Event> eventQueue = new PriorityQueue<Event>(50, new EventComparator());
    ArrayList<GameEntity> enemyWave;
    ArrayList<Pair> pathWay;
    int pathCount = 0;
    GameEntity base;

    private enum gameState {
        S_LOAD, S_PLAY, S_LOSE, S_WIN
    }
    private gameState state = gameState.S_LOAD;

    final short MAX_WAVE = 10;
    final short MAX_FPS = 30;

    int gold, kills = 0, experience = 0;
    short curWave = 1, curEenemyMax;

    int count = 0;
    boolean spawnedAllEnemies = false;


    /**
     *
     */
    public static void main(String []arg){
        GameMain m = new GameMain();

        m.run();
    }
    /**
     *
     */


    Thread thread = new Thread();
    private int fps = 0;

    public GameMain(){
        thread.start();
    }

    public void run(){
        System.out.println("Success");

        int count = 0;
        Event current;
        long lastFrame = System.currentTimeMillis();
        int frames = 0;

        //Frames Per Second
        while(true){
            //redraw();
            System.out.println(fps+"");

            if(state == gameState.S_LOAD){
                createWave();
            }
            else if(state == gameState.S_PLAY){

                while(count >= eventQueue.peek().getTurn()){
                    current = eventQueue.remove();

                    if(current.getEventType() == Event.eventType.E_TOWER){
                        if(current.entity.hasTarget()) {
                            towerShoot(current.entity);
                        }
                    }
                    else if(current.getEventType() == Event.eventType.E_TOWER){
                        moveEnemy(current.entity);

                        if(!spawnedAllEnemies && spawnEnemy() == 1) {
                            spawnedAllEnemies = true;
                        }
                    }

                    //Put event back in queue with increased count

                    current.increaseTurn(current.entity.getSpeed());
                    eventQueue.add(current);
                }








            }
            else if(state == gameState.S_LOSE){

                return;
            }
            else if(state == gameState.S_WIN){

                return;
            }

            frames++;
            count++;

            if(System.currentTimeMillis() - 1000 >= lastFrame){
                fps = frames;
                frames = 0;
                lastFrame = System.currentTimeMillis();
            }

            try {
                Thread.sleep(1000/MAX_FPS);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void addPathCoor(int x, int y){
        pathWay.add(new Pair(x, y));
        pathCount++;
    }

    public void addBaseCoor(int x, int y){ base.setPair(new Pair(x,  y)); }

    GameEntity placeTower(GameEntity.Tower t, Pair p){
        GameEntity n = new GameEntity(t);

        if(gold >= n.getCost()){
            //check map
            /*
            if(map[p.getPairX()][p.getPairY()] != tower || obstacle){
                map[][] = t

            */
            gold -= n.getCost();


            Event e = new Event(Event.eventType.E_TOWER, n, count+1, p);
            eventQueue.add(e);

            return n;
        }

        return null;
    }

    void createWave(){
        if(curWave != MAX_WAVE) {
            curEenemyMax = curWave;

            for (int i = 0; i < curEenemyMax; i++) {
                Random r = new Random();
                int t = r.nextInt(3);
                GameEntity e;

                if(t == 0) {
                    e = new GameEntity(GameEntity.Enemy.E_FAST);

                }
                else if(t == 1){
                    e = new GameEntity(GameEntity.Enemy.E_MEDIUM);
                }
                else{
                    e = new GameEntity(GameEntity.Enemy.E_HEAVY);
                }


                enemyWave.add(e);
            }
        }
    }

    int spawnEnemy(){
        int i = 0;
        while(enemyWave.get(i).getPathPos() != -1){
            i++;
            if(i > curEenemyMax){
                return 1;
            }
        }

        enemyWave.get(i).setPathPos(0);


        checkEnemyRange(enemyWave.get(i));

        return 0;
    }

    boolean checkRange(GameEntity e, GameEntity t){
        return ((t.getPairX() - e.getPairX())^2 + (t.getPairY() - e.getPairY())^2) <= (t.getRange()^2);
    }

    void moveEnemy(GameEntity e){
        e.increasePathPos();
        e.setPair(pathWay.get(e.getPathPos()));





        if(e.getPathPos() == pathCount-1){
            if(base.damageBase(e.getDamage()) < 0){
                state = gameState.S_LOSE;
            }

            e.destroyEnemy();
        }
        else{
            checkEnemyRange(e);
        }
    }

    void checkEnemyRange(GameEntity e){
        /*
        check map area within 4 tiles
        if tower !hasTarget => setTarget(e)
        else
            if checkRange(tower, tower.target)
            else tower.setTarget(e)
        */
    }

    void towerShoot(GameEntity t){
        if(checkRange(t.getTarget(), t)){
            t.shoot();

            if(t.getTarget().isEnemyDead()){
                for(Event e : eventQueue){
                    if(e.getEventType() == Event.eventType.E_ENEMY && e.entity.equals(t.getTarget())){
                        eventQueue.remove(e);
                        break;
                    }
                }

            }
        }
    }

    int getGold(){
        return this.gold;
    }
    int getCurWave(){
        return this.curWave;
    }
}
