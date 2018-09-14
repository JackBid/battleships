import javafx.geometry.Pos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;


public class Game implements GameInterface{

    private PlayerInterface p1;
    private PlayerInterface p2;
    private Board b1;
    private Board b2;
    private int turn = 0;
    private boolean placed = false;
    private ArrayList<Integer> p1ships;
    private ArrayList<Integer> p2ships;

    public static void main(String[] args) throws ShipOverlapException, InvalidPositionException, PauseException {
        System.out.println("Welcome to battleships!");
        // default game
        Game g = new Game(new HumanConsolePlayer("Player1"), new ComputerPlayer("Player2"));
        // main menu deals with selecting players, saving and loading, can be returned to at any time with pause
        mainMenu(g);
    }
    /*
    Game: Initialise game varaibles when object is created
    @param p1 - the player object of the first player
    @param p2 - the player object of the second player
    */
    public Game(PlayerInterface p1, PlayerInterface p2){
        this.p1 = p1;
        this.p2 = p2;
        b1 = new Board();
        b2 = new Board();
        p1ships = new ArrayList<Integer>();
        p2ships = new ArrayList<Integer>();
        for(int i=5; i>=1; i--){
            if(i==2){
                p1ships.add(3);
                p2ships.add(3);
            }else if(i==1){
                p1ships.add(2);
                p2ships.add(2);
            }else {
                p1ships.add(i);
                p2ships.add(i);
            }
        }
    }

    /*
    mainMenu: Used fo selecting players, saving, loading and can be returned to and any time with pause command
    @param g - the game object to set varaibles for
     */
    public static void mainMenu(Game g){
        String player1 = "";
        String player2 = "";
        String name1 = "Player_1";
        String name2 = "Player_2";

        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("");
            System.out.println("1. Set Players (human vs computer by default)");
            System.out.println("2. Load game");
            System.out.println("3. Continue game");
            System.out.println("4. Save game");
            System.out.println("5. Start/continue game");
            System.out.println("6. Exit");
            System.out.print("Enter 1, 2, 3, 4, 5 or 6: ");
            String input = sc.next();
            if(input.equals("1")){
                // set players
                // reset variables as a game could have just been played
                g.b1 = new Board();
                g.b2 = new Board();
                g.placed = false;
                g.p1ships.clear();
                g.p2ships.clear();
                for(int i=5; i>=1; i--){
                    if(i==2){
                        g.p1ships.add(3);
                        g.p2ships.add(3);
                    }else if(i==1){
                        g.p1ships.add(2);
                        g.p2ships.add(2);
                    }else {
                        g.p1ships.add(i);
                        g.p2ships.add(i);
                    }
                }
                player1 = g.setPlayer("Player 1");
                name1 = g.setName("Player 1");
                player2 = g.setPlayer("Player 2");
                name2 = g.setName("Player 2");
            }else if(input.equals("2")){
                // load game
                try {
                    g.loadGame("data.txt");
                } catch(IOException e){
                    e.printStackTrace();
                }
            }else if(input.equals("3")){
                // play game
                g.play();
            }else if(input.equals("4")){
                // save game
                try {
                    g.saveGame("data.txt");
                    System.out.println("Game saved.");
                }catch (IOException e){
                    e.printStackTrace();
                }
            }else if(input.equals("5")){
                // start/continue game
                // if names havent been set for default players
                if(player1.equals("") && player2.equals("") && name1.equals("Player_1") && name2.equals("Player_2")){
                    name1 = g.setName("Player 1");
                    name2 = g.setName("Player 2");
                }
                // change player objects based on input
                if(player1.equals("human") && player2.equals("human")){
                    g.p1 = new HumanConsolePlayer(name1);
                    g.p2 = new HumanConsolePlayer(name2);
                }else if(player1.equals("computer") && player2.equals("human")){
                    g.p1 = new ComputerPlayer(name1);
                    g.p2 = new HumanConsolePlayer(name2);
                }else if(player1.equals("computer") && player2.equals("computer")){
                    g.p1 = new ComputerPlayer(name1);
                    g.p2 = new ComputerPlayer(name2);
                }else{
                    g.p1 = new HumanConsolePlayer(name1);
                    g.p2 = new ComputerPlayer(name2);
                }
                g.play();
            }else if(input.equals("6")){
                // exit
                System.exit(0);
            }else{
                System.out.println("Unknown input.");
            }
        }
    }

    /*
    setPlayer: ask if the player is human or computer
    @param name - the name of the player
    @return the string "human" or "computer"
     */
    public String setPlayer(String name){
        Scanner sc = new Scanner(System.in);
        System.out.print("Is " + name + " a human or computer? (Type human or computer): ");
        String player = sc.next().toLowerCase();
        while(!player.equals("human") && !player.equals("computer")){
            System.out.print("Is " + name + " a human or computer? (Type human or computer): ");
            player = sc.next().toLowerCase();
        }
        return player;
    }
    /*
    setName: get a name for a player and return it
    @param player - the string "player 1" or "player 2"
    @return - the name of the player
    */
    public String setName(String player){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a name for " + player + ": ");
        String name = sc.nextLine().toLowerCase();
        name = name.replaceAll("\\s+", "_");
        return name;
    }

    /*
    play: contains the game loop, sets ship placements and deals with shooting
    @return the player object of the winning player
     */
    public PlayerInterface play() {
        // if the ships aren't placed
        if(!placed) {
            chooseShips(p1, b1, p1ships);
            chooseShips(p2, b2, p2ships);
        }
        // while neither ship is sunk
        while(!b1.allSunk() && !b2.allSunk()){
            PlayerInterface player;
            Board playersBoard, enemyBoard;
            // decide whether it is player 1's or player 2's go based on the turn number
            if(turn%2== 0){
                player = p1;
                playersBoard = b1;
                enemyBoard = b2;
            }else{
                player = p2;
                playersBoard = b2;
                enemyBoard = b1;
            }
            // print whos go it is and the board if it is human
            System.out.println("\n######################################\n" + player.toString() + "'s go:" + "\n######################################\n");
            System.out.println();
            if(player instanceof HumanConsolePlayer) {
                System.out.println("Your enemy's board: \n" + enemyBoard.toString().replace('S', '~'));
                System.out.println("Your current board: \n" + playersBoard.toString());
            }

            // Get position to shoot at
            boolean shotChosen = false;
            while(!shotChosen) {
                try {
                    // Choose shot
                    Position shot = player.chooseShot();

                    // Update board
                    enemyBoard.shoot(shot);
                    shotChosen = true;

                    // Get status of shot
                    ShipStatus shipStatus = enemyBoard.getStatus(shot);

                    if(shipStatus == ShipStatus.NONE){
                        player.shotResult(shot, ShotStatus.MISS);
                    }else if(shipStatus == ShipStatus.SUNK){
                        player.shotResult(shot, ShotStatus.SUNK);
                    }else{
                        player.shotResult(shot, ShotStatus.HIT);
                    }
                } catch (PauseException e) {
                    placed = true;
                    mainMenu(this);
                } catch (InvalidPositionException e){
                    System.out.println(("ERROR: Invalid position selected, choose again."));
                }
            }


            //p2.opponentShot(shot);
            Scanner sc = new Scanner(System.in);
            System.out.print("\nEnter any character to continue... ");
            sc.next();
            clearScreen();

            turn++;
        }
        if(b1.allSunk()){
            System.out.println(p2.toString() + " has won!");
            return p1;
        }else if(b2.allSunk()){
            System.out.println(p1.toString() + " has won!");
            return  p2;
        }
        return null;
    }
    /*
    chooseShips: for a given player, board and ships it makes the player choose a position and applies this to the board
    @param p - the player who is choosing a position
    @param b - the board that the ship is being added to
    @param ships - a list of ships to add
     */
    public void chooseShips(PlayerInterface p, BoardInterface b, ArrayList<Integer> ships){
        for (int i : ships) {
            Ship temp = new Ship(i);
            boolean placed = false;
            while(!placed) {
                try {
                    Placement placement = p.choosePlacement(temp, b.clone());
                    temp.setPlacement(placement);
                    b.placeShip(temp, placement.getPosition(), placement.isVertical());
                    placed = true;
                } catch (InvalidPositionException e){
                    System.out.println("ERROR: Invalid position selected, choose again.");
                } catch (ShipOverlapException e){
                    System.out.println("ERROR: Ships overlap, choose again.");
                }catch (PauseException e){
                    placed = true;
                    mainMenu(this);
                }
            }
        }
        if(p instanceof HumanConsolePlayer) {
            System.out.println(b.toString());
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter any character to continue... ");
        sc.next();
        clearScreen();
    }
    /*
    clearScreen - clears the screen so you cant see the enemy's board
    */
    public void clearScreen(){
        for(int i=0; i<20; i++){
            System.out.println("\b");
        }
    }
    /*
    saveGame - saves the game to a text file
    @param filename - the name of the file
    @throws IOException - thrown if error saving to file
     */
    public void saveGame(String filename) throws IOException{
        ArrayList<PlayerInterface>players = new ArrayList<PlayerInterface>();
        players.add(p1);
        players.add(p2);
        FileWriter write = new FileWriter(filename, false);
        PrintWriter print = new PrintWriter(write);

        for(PlayerInterface p : players){
            ArrayList<String> ships = new ArrayList<String>();
            Board b = new Board();
            if(p == p1){
                b = b1;
            }else if(p == p2){
                b = b2;
            }
            for(Ship ship : b.getShips()){
                ships.add(ship.getPlacement().getPosition().getX() + " " + ship.getPlacement().getPosition().getY() + " " + ship.getPlacement().isVertical());
            }
            if(p instanceof HumanConsolePlayer){
                print.println("human " + p.toString());
                for(String data : ships){
                    print.println(data);
                }
                String shots = "shots: ";
                for(Position pos : ((HumanConsolePlayer) p).getShots()){
                    shots += pos.toString() + " ";
                }
                print.println(shots);
            }else if(p instanceof  ComputerPlayer){
                print.println("computer " + p.toString());
                for(String data : ships){
                    print.println(data);
                }
                String shots = "shots: ";
                for(Position pos : ((ComputerPlayer) p).getShots()){
                    shots += pos.toString() + " ";
                }
                print.println(shots);
            }
            print.println("");
        }
        print.close();
    }
    /*
    loadGame - loads the game from a text file
    @para filename - the name of the file to load
     */
    public void loadGame(String filename) throws IOException{

        ArrayList<Placement> p1placements = new ArrayList<Placement>();
        ArrayList<Placement> p2placements = new ArrayList<Placement>();
        ArrayList<Position> p1shots = new ArrayList<Position>();
        ArrayList<Position> p2shots = new ArrayList<Position>();
        boolean searchingp1 = true;
        String name1 = "Player 1";
        String name2 = "Player 2";

        // reset boards
        b1 = new Board();
        b2 = new Board();
        // reset ship arrays
        p1ships = new ArrayList<Integer>();
        p2ships = new ArrayList<Integer>();

        for(int i=5; i>=1; i--){
            if(i==2){
                p1ships.add(3);
                p2ships.add(3);
            }else if(i==1){
                p1ships.add(2);
                p2ships.add(2);
            }else {
                p1ships.add(i);
                p2ships.add(i);
            }
        }



        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);
            String player1 = "", player2 = "";
            ArrayList<String> lines = new ArrayList<>();
            // add every line into arrayList lines
            while(sc.hasNextLine()){
                lines.add(sc.nextLine());
            }

            for(String s : lines) {
                // split the line into an array based on spaces
                String[] line = s.split(" ");

                // get the player type
                if (line[0].equals("human") || line[0].equals("computer")) {
                    if (searchingp1) {
                        player1 = line[0];
                        name1 = line[1];
                    } else {
                        player2 = line[0];
                        name2 = line[1];
                    }
                    // get the placements
                }else if (line.length == 3 && !line[0].equals("shots:")) {
                    if(searchingp1) {
                        p1placements.add(new Placement(new Position(Integer.parseInt(line[0]), Integer.parseInt(line[1])), Boolean.valueOf(line[2])));
                    }else{
                        p2placements.add(new Placement(new Position(Integer.parseInt(line[0]), Integer.parseInt(line[1])), Boolean.valueOf(line[2])));
                    }
                    // get the shots
                }else if (line[0].equals("shots:")) {
                    for (int i = 1; i < line.length; i++) {
                        String[] coords = line[i].split(",");
                        if (searchingp1) {
                            p1shots.add(new Position(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
                        } else {
                            p2shots.add(new Position(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
                        }
                    }
                    // if there is a blank line we are onto next plater
                }else if(line[0].equals("")){
                    searchingp1 = false;
                }
            }

            if(player1.equals("human") && player2.equals("human")){
                p1 = new HumanConsolePlayer(name1);
                p2 = new HumanConsolePlayer(name2);
                ((HumanConsolePlayer)p1).setShots(p1shots);
                ((HumanConsolePlayer)p2).setShots(p2shots);
            }else if(player1.equals("human") && player2.equals("computer")){
                p1 = new HumanConsolePlayer(name1);
                p2 = new ComputerPlayer(name2);
                ((HumanConsolePlayer)p1).setShots(p1shots);
                ((ComputerPlayer)p2).setShots(p2shots);
            }else if(player1.equals("computer") && player2.equals("human")){
                p1 = new ComputerPlayer(name1);
                p2 = new HumanConsolePlayer(name2);
                ((ComputerPlayer)p1).setShots(p1shots);
                ((HumanConsolePlayer)p2).setShots(p2shots);
            }else if(player1.equals("computer") && player2.equals("computer")){
                p1 = new ComputerPlayer(name1);
                p2 = new ComputerPlayer(name2);
                ((ComputerPlayer)p1).setShots(p1shots);
                ((ComputerPlayer)p2).setShots(p2shots);
            }


            // place the ships on b1
            for(int i=0; i<p1placements.size(); i++){
                Ship temp = new Ship(p1ships.get(0));
                temp.setPlacement(p1placements.get(i));
                b1.placeShip(temp, p1placements.get(i).getPosition(), p1placements.get(i).isVertical());
                p1ships.remove(0);
            }
            if(p1ships.size() != 0) {
                chooseShips(p1, b1, p1ships);
            }
            // place the ships on b2
            for(int i=0; i<p2placements.size(); i++){
                Ship temp = new Ship(p2ships.get(0));
                temp.setPlacement(p2placements.get(i));
                b2.placeShip(temp, p2placements.get(i).getPosition(), p2placements.get(i).isVertical());
                p2ships.remove(0);
            }
            if(p2ships.size() != 0) {
                chooseShips(p2, b2, p2ships);
            }

            // shoot at all the shots
            for(Position shot : p1shots){
                b2.shoot(shot);

                // Get status of shot
                ShipStatus shipStatus = b2.getStatus(shot);

                if(shipStatus == ShipStatus.NONE){
                    p1.shotResult(shot, ShotStatus.MISS);
                }else if(shipStatus == ShipStatus.SUNK){
                    p1.shotResult(shot, ShotStatus.SUNK);
                }else{
                    p1.shotResult(shot, ShotStatus.HIT);
                }
            }

            for(Position shot : p2shots){
                b1.shoot(shot);

                // Get status of shot
                ShipStatus shipStatus = b1.getStatus(shot);

                if(shipStatus == ShipStatus.NONE){
                    p2.shotResult(shot, ShotStatus.MISS);
                }else if(shipStatus == ShipStatus.SUNK){
                    p2.shotResult(shot, ShotStatus.SUNK);
                }else{
                    p2.shotResult(shot, ShotStatus.HIT);
                }
            }

            System.out.println(b1.toString());
            System.out.println(b2.toString());

            placed = true;
            play();

        }catch (Exception e){
            System.out.println("Error loading game, might not have been saved before.");
        }

    }
}
