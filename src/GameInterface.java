import java.io.IOException;

/**
 * GameInterface represents the game state including the boards and the players
 *
 * Requires a constructor with two parameters: the two player objects. 
 *
 * Also requires a main method which allows the user to choose the player names and types and start the game. 
 * The main method menu should allow users to: create the players (human or computer); load a game; continue a game; save the game; start a new game; exit the program.
 *
 * If providing a GUI then the same options need to be available through the GUI.
 **/

public interface GameInterface
{
    /**
     * Play the game until completion or pause. Should work either for a new game or the continuation of a paused game. 
     * This method should get ask players for ship placements. The board that is passed to the players when choosing placements should be a clone of the game board so that they can try out moves without affecting the state of the game. Once ships are all placed the players should be asked one after another for their choice of shot via their getShot method. 
     * When a shot has been accepted by the game the player should be informed of the result of the shot. 
     *
     * At any stage during game play a player can choose to pause a game (with a PauseException), which should cause the play method to return
     *
     * @return the winning player if there is one, or null if not (the game has been paused by a player). If a player tries to take an illegal shot or place a ship illegally then they forfeit the game and the other player immediately wins.
     *
     **/
    PlayerInterface play();

    /**
     * Save the current state of the game (including the boards and players) into a file so it can be re-loaded and game play continued. You choose what the format of the file is. Java object serialization is not the preferred solution.
     *
     * @param filename the name of the file in which to save the game state
     *
     * @throws IOException when an I/O problem occurs while saving
     **/
    void saveGame(String filename) throws IOException;

    /**
     * Load the game state from the given file
     *
     * @param filename  the name of the file from which to load the game state
     *
     * @throws IOException when an I/O problem occurs or the file is not in the correct format (as used by saveGame())
     **/
    void loadGame(String filename) throws IOException;

}