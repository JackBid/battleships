import javafx.geometry.Pos;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class HumanConsolePlayer implements PlayerInterface{

    private String name;
    private ArrayList<Position> shots;

    public HumanConsolePlayer(String name){
        this.name = name;
        shots = new ArrayList<Position>();
}

    public String toString(){
        return name;
    }

    public Placement choosePlacement(ShipInterface ship, BoardInterface board) throws PauseException{
        int x;
        int y;
        boolean isVertical;

        while(true) {

                String display = board.toString();
                System.out.println("\n" + display + "\n" + name + " choose your placement for ship of " + ship.getSize() + " length.");
                Scanner sc = new Scanner(System.in);
            try {
                System.out.print("Enter x: ");
                String s = sc.next();
                if (s.toLowerCase().equals("pause")) {
                    throw new PauseException("User paused");
                } else {
                    x = Integer.parseInt(s);
                }

                System.out.print("Enter y: ");
                s = sc.next();
                if (s.toLowerCase().equals("pause")) {
                    throw new PauseException("User paused");
                } else {
                    y = Integer.parseInt(s);
                }
                System.out.print("Is vertical? (T/F)");
                s = sc.next();
                if (s.toLowerCase().equals("pause")) {
                    throw new PauseException("User paused");
                } else if (s.equals("T")) {
                    isVertical = true;
                } else {
                    isVertical = false;
                }

                Placement p = new Placement(new Position(x, y), isVertical);
                return p;
            } catch (InvalidPositionException e) {
                System.out.println("ERROR: Invalid position selected, choose again.");
            } catch(NumberFormatException e){
                System.out.println("ERROR: Invalid number chosen");
            }
        }
    }

    public Position chooseShot() throws PauseException {
        int x;
        int y;
        Position pos = null;
        Scanner sc = new Scanner(System.in);
        System.out.println(name + " choose your shot:");
        boolean shotChosen = false;
        while(!shotChosen) {
            try {
                System.out.print("Enter x: ");
                String s = sc.next();
                if (s.toLowerCase().equals("pause")) {
                    throw new PauseException("User paused");
                }else {
                    x = Integer.parseInt(s);
                }
                System.out.print("Enter y: ");
                s = sc.next();
                if(s.toLowerCase().equals("pause")){
                    throw new PauseException("User paused");
                }else {
                    y = Integer.parseInt(s);
                }
                pos = new Position(x, y);
                shotChosen = true;
            } catch (InvalidPositionException e) {
                System.out.println("ERROR: Invalid position selected, choose again.");
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Invalid number chosen");
            }
        }
        shots.add(pos);
        return pos;
    }

    public void shotResult(Position position, ShotStatus status) {
        System.out.println("\n" + position.toString() + " was a " + status + ".\n");
    }

    public void opponentShot(Position position) {
        System.out.println("Opponent shot at: " + position.toString());
    }
    public void setShots(ArrayList<Position> shots) {
        this.shots = shots;
    }
    public ArrayList<Position> getShots(){
        return shots;
    }
}
