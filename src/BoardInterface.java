/**
 * You should define a constructor with no parameters
 */
public interface BoardInterface extends Cloneable
{

    /**
     * Add a ship to the board with the top/left position specified
     *
     * @param ship The ship to add to the board
     *
     * @param position The top/left of the ship position
     *
     * @param isVertical True if ship is to be placed vertically, otherwise the ship should
     * be placed horizontally
     *
     * @throws InvalidPositionException if the not all of the ship squares fit onto the board
     *
     * @throws ShipOverlapException if the any of the squares of the ship to be added would overlap
     * with an already placed ship
     *
     */
    void placeShip(ShipInterface ship, Position position, boolean isVertical) throws InvalidPositionException, ShipOverlapException;

    /**
     * Update the board state by shooting at the given position
     *
     * @param position The position to shoot at
     *
     * @throws InvalidPositionException if the shot position is not on the board
     */
    void shoot(Position position) throws InvalidPositionException;

    /**
     * Find the status at the given position
     *
     * @param position the position to find out about
     *
     * @return The status at the given board position
     *
     * @throws InvalidPositionException if the position is not on the board
     */
    ShipStatus getStatus(Position position) throws InvalidPositionException;

    /**
     * Find if all the ships on the board have been sunk
     *
     * @return True If and only if all the ships have been sunk
     */
    boolean allSunk();

    /**
     *
     * @return A string representation of the board, suitable for printing to the screen
     *
     */
    String toString();

    /**
     * Make a deep clone of the current object. Only clones of boards are to be passed to a
     * player by a game, so that the player does not affect the game state if shots are tried out
     *
     * @return the cloned object
     */
    BoardInterface clone();

}