import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.HashSet;

public class Ship implements ShipInterface{

    private int size;
    private HashSet<Integer> hits;
    private Placement placement;

    public Ship(int size){
        this.size = size;
        hits = new HashSet<Integer>();
    }

    public void setPlacement(Placement p){
        this.placement = p;
    }

    public Placement getPlacement(){
        if(placement != null) {
            return placement;
        }
        return null;
    }

    public int getSize(){
        return size;
    }

    public boolean isSunk(){
        if(hits.size() == size){
            return true;
        }
        return false;
    }

    public void shoot(int offset) throws InvalidPositionException{
        if(offset < 0 || offset >= size){
            throw new InvalidPositionException();
        }
        hits.add(offset);
    }

    public ShipStatus getStatus(int offset) throws InvalidPositionException{
        if(offset < 0 || offset >= size){
            throw new InvalidPositionException();
        }
        if(isSunk()){
            return ShipStatus.SUNK;
        }else if (hits.contains(offset)){
            return ShipStatus.HIT;
        }else{
            return ShipStatus.INTACT;
        }
    }

    public HashSet<Integer> getHits(){
        return hits;
    }

}

