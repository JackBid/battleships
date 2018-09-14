import javafx.geometry.Pos;

import java.util.ArrayList;

public class Board implements BoardInterface{
    private static final int size = 10;
    private static final String shipIcon = "S";
    private ArrayList<Ship> ships;
    private String[][] board =  {
            {"~","~","~","~","~","~","~","~","~","~"},
            {"~","~","~","~","~","~","~","~","~","~"},
            {"~","~","~","~","~","~","~","~","~","~"},
            {"~","~","~","~","~","~","~","~","~","~"},
            {"~","~","~","~","~","~","~","~","~","~"},
            {"~","~","~","~","~","~","~","~","~","~"},
            {"~","~","~","~","~","~","~","~","~","~"},
            {"~","~","~","~","~","~","~","~","~","~"},
            {"~","~","~","~","~","~","~","~","~","~"},
            {"~","~","~","~","~","~","~","~","~","~"}};

    public Board() {
        ships = new ArrayList<Ship>();
    }

    public void placeShip(ShipInterface ship, Position position, boolean isVertical) throws InvalidPositionException, ShipOverlapException {

        // get the position to place ship zero indexed
        int x = position.getX()-1;
        int y = position.getY()-1;

        if(!isVertical){
            // check that the ship will fit on the board
            if(x < 0 || x + ship.getSize()-1 > 9 || y < 0 || y > 9){
                throw new InvalidPositionException();
            }

            // check if it will overlap
            for(int i = x; i <= x+ship.getSize()-1; i++){
                if(board[y][i] == shipIcon){
                    throw new ShipOverlapException();
                }
            }

            // update board
            for(int i = 0; i < ship.getSize(); i++){
                board[y][x+i] = "S";
            }
        }else{
            // check that ship will fit on board
            if(y < 0 || y + ship.getSize()-1 > 9 || x < 0 || x > 9){
                throw new InvalidPositionException();
            }

            // check if it will overlap
            for(int i = y; i <= y+ship.getSize()-1; i++){
                if(board[i][x] == shipIcon){
                    throw new ShipOverlapException();
                }
            }
            // update board
            for(int i = 0; i < ship.getSize(); i++){
                board[y+i][x] = "S";
            }
        }
        ships.add((Ship)ship);
    }

    // Update the board state by shooting at the position.
    public void shoot(Position position) throws InvalidPositionException {
        int x = position.getX()-1;
        int y = position.getY()-1;
        if(board[y][x] == "~"){
            board[y][x] = "O";
        }else if(board[y][x] == "S"){
            board[y][x] = "x";
            // shoot at ship.
            for(Ship ship : ships){
                int shipX = ship.getPlacement().getPosition().getX()-1;
                int shipY = ship.getPlacement().getPosition().getY()-1;

                if(!ship.getPlacement().isVertical()&& shipY == y && x >= shipX && x < shipX + ship.getSize()){
                    ship.shoot(x-shipX);
                }
                if(ship.getPlacement().isVertical() && shipX == x && y >= shipY && y < shipY + ship.getSize()){
                    ship.shoot(y-shipY);
                }
            }
        }
    }

    public ShipStatus getStatus(Position position) throws InvalidPositionException {

        int x = position.getX()-1;
        int y = position.getY()-1;

        for(Ship ship : ships){
            int shipX = ship.getPlacement().getPosition().getX()-1;
            int shipY = ship.getPlacement().getPosition().getY()-1;
            if(!ship.getPlacement().isVertical()&& shipY == y && x >= shipX && x < shipX + ship.getSize()){
                return ship.getStatus(x-shipX);
            }
            if(ship.getPlacement().isVertical() && shipX == x && y >= shipY && y < shipY + ship.getSize()){
                return ship.getStatus(y-shipY);
            }
        }

        return ShipStatus.NONE;
    }

    public boolean allSunk() {
        for(ShipInterface ship : ships){
            if(!ship.isSunk()){
                return false;
            }
        }
        return true;
    }

    public String toString() {
        String display = "   1  2  3  4  5  6  7  8  9  10\n";
        for(int i = 0; i < size; i++){
            display += i+1;
            if(i+1 < 10){
                display += "  ";
            }else{
                display += " ";
            }
            for(int j = 0; j < size; j++){
                display += board[i][j] + "  ";
            }
            display += "\n";
        }
        return display;
    }

    public BoardInterface clone() {
        Board clone = new Board();
        clone.ships = this.ships;
        clone.board = this.board;
        return clone;
    }

    public ArrayList<Ship> getShips(){
        return ships;
    }

}
