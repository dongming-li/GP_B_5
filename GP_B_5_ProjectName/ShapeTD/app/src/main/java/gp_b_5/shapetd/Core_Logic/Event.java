package gp_b_5.shapetd.Core_Logic;

import java.util.Comparator;

/**
 * Created by Nick on 10/28/2017.
 */

public class Event implements Comparable{
    public enum eventType {
        E_TOWER, E_ENEMY
    }

    private eventType et;
    private int turn;
    GameEntity entity;
    Pair p;

    public Event(eventType et, GameEntity g, int i, Pair xy){
        this.et = et;
        this.entity = g;

        this.turn = i;
        this.p.setPair(xy.getPairX(), xy.getPairY());

        if(g.isEnemy()){
            this.et = eventType.E_ENEMY;
        }
        else if(g.isTower()){
            this.et = eventType.E_TOWER;
        }
    }

    public eventType getEventType(){
        return this.et;
    }
    Pair getPair(){ return this.p; }

    public int getTurn(){
        return this.turn;
    }

    public void setTurn(int i){
        this.turn = i;
    }

    public void increaseTurn(int i){
        this.turn += i;
    }



    @Override
    public int compareTo(Object o){
        Event t = (Event) o;

        return this.turn - t.turn;
    }
}

class EventComparator implements Comparator<Event> {
    public int compare(Event e1, Event e2) {
        int c = e1.compareTo(e2);

        return ((c == 0) ? e1.getEventType().compareTo(e2.getEventType()) : c);
    }
}