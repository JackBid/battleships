import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class ComputerPlayer implements PlayerInterface {

    private String name;
    private Random rand = new Random();
    private ArrayList<Position> shots;
    private Stack<Position> toSearch;
    private Position lastHit;

    ComputerPlayer(String name){
        this.name = name;
        shots = new ArrayList<Position>();
        toSearch = new Stack<Position>();
    }

    public Placement choosePlacement(ShipInterface ship, BoardInterface board) throws PauseException{
        boolean validPlacement = false;
        boolean isVertical;
        if(Math.random() < 0.5){
            isVertical = false;
        }else{
            isVertical = true;
        }
        int x=0, y=0;
        while(!validPlacement) {
            // pick random coords
            if(!isVertical){
                x = rand.nextInt(10 - ship.getSize()-1) + 1 ;
                y = rand.nextInt(10) + 1;
            }else{
                x = rand.nextInt(10) + 1;
                y = rand.nextInt(10 - ship.getSize()-1) + 1 ;
            }

            validPlacement = true;

            // check positions will be empty
            for (int i = 0; i < ship.getSize(); i++) {
                try {
                    if (!isVertical && board.getStatus(new Position(x + i, y)) != ShipStatus.NONE) {
                        validPlacement = false;
                    }
                    if(isVertical && board.getStatus(new Position(x, y+i)) != ShipStatus.NONE){
                        validPlacement = false;
                    }
                } catch (InvalidPositionException e) {
                    e.printStackTrace();
                }

            }
        }
        try{
            Placement p  = new Placement(new Position(x,y), isVertical);
            return p;
        }catch(InvalidPositionException e){
            e.printStackTrace();
        }

        return null;
    }

    public Position chooseShot() throws PauseException {
        boolean foundShot = false;
        // search around hit
        while(toSearch.size() > 0){
            if(contains(shots, toSearch.peek())){
                toSearch.pop();
            }else {
                shots.add(toSearch.peek());
                return toSearch.pop();
            }
        }

        // Guess a random shot
        while(!foundShot) {
            foundShot = true;
            try {
                Position pos = new Position(rand.nextInt(10) + 1, rand.nextInt(10) + 1);
                if (contains(shots, pos)) {
                    foundShot = false;
                }else{
                    shots.add(pos);
                    return pos;
                }
            } catch (InvalidPositionException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void shotResult(Position position, ShotStatus status) {
        System.out.println("\n" + position.toString() + " was a " + status + ".\n");
        if(status == ShotStatus.HIT){
            int x = position.getX();
            int y = position.getY();
            try{
                if(lastHit == null){
                    lastHit = position;
                    if(y-1 > 0) {
                        toSearch.push(new Position(x, y - 1));
                    }
                    if(y+1 < 11) {
                        toSearch.push(new Position(x, y + 1));
                    }
                    if(x-1 > 0) {
                        toSearch.push(new Position(x - 1, y));
                    }
                    if(x+1 < 11) {
                        toSearch.push(new Position(x + 1, y));
                    }
                }else if(lastHit.getX() == x){
                    if(y-1 > 0) {
                        toSearch.push(new Position(x, y - 1));
                    }
                    if(y+1 < 11) {
                        toSearch.push(new Position(x, y + 1));
                    }
                    lastHit = position;
                }else if(lastHit.getY() == y){
                    if(x+1 < 11) {
                        toSearch.push(new Position(x + 1, y));
                    }
                    if(x-1 > 0) {
                        toSearch.push(new Position(x - 1, y));
                    }
                    lastHit = position;
                }
            }catch(Exception e){

            }
        }
        if(status == ShotStatus.SUNK){
            lastHit = null;
        }
    }

    public void opponentShot(Position position) {
        System.out.println("Opponent shot at: " + position.toString());
    }
    public boolean contains(ArrayList<Position> shots, Position toSearch){
            for(Position p : shots){
                if(p.toString().equals(toSearch.toString())){
                    return true;
                }
            }
        return false;
    }

    public String toString(){
        return name;
    }

    public ArrayList<Position> getShots(){
        return shots;
    }
    public void setShots(ArrayList<Position> shots) {
        this.shots = shots;
    }
}
