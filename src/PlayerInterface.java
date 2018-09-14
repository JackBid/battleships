/**
 * You should define a constructor with a single String parameter, which defines the display name of the player
 *
 *
 * This type represents individual players in the game of battleships.
 * The methods provided will be used by the Game class as the game is played. 
 * The game will initially call choosePlacement once for each ship. 
 * Once all ships are placed then the game will repeatedly (in turn) call the chooseShot method. 
 * After each chooseShot method the game will inform the player of the result of the shot with the shotResult method.
 * The player will also be informed of opponent's shots with the opponentShot method. This could be used to update a local copy of the game board for display to the user.
 *
 */

public interface PlayerInterface
{
    /**
     * @param ship The ship to be placed
     *
     * @param board (A clone of) the current board state to which the ship will be added
     *
     * @return The placement (position and orientation) of the ship specified by the player
     *
     * @throws PauseException At any stage the user can choose to enter "pause" (case insensitive) to make the game return to 
     * the main menu
     *
     */
    Placement choosePlacement(ShipInterface ship, BoardInterface board) throws PauseException;

    /**
     * @return The shot chosen by the player
     *
     * @throws PauseException At any stage the user can choose to enter "pause" (case insensitive) to make the game return to 
     * the main menu
     */
    Position chooseShot() throws PauseException;

    /**
     * After the game calls the chooseShot method, it then calls this method with the result of the shot.
     * The player may choose to keep track of the results of previous shots in its state
     *
     * @param position The position of the shot
     *
     * @param status The result of the shot
     */
    void shotResult(Position position, ShotStatus status);

    /**
     * After the game has received the opponent's shot this method is called. It does not 
     * affect the progress of the game, but may be of interest to the player (particularly a human player).
     * @param position The position that the opponent chose for their shot.
     */
    void opponentShot(Position position);

    /**
     * @return A string representation of the player i.e. the display name provided as 
     * a parameter to the constructor.
     */
    String toString();

}