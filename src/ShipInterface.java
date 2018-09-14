/**
 * The Ship class represents individual ships in the game of battleships.
 *
 * Write a constructor that takes an integer (the size of the ship) as a parameter
 */
public interface ShipInterface
{
    int getSize();

    boolean isSunk();

    /**
     * Update the status of the ship by firing at the offset specified. 
     * After the method is called then the status at the offset will either be HIT (if at least one square remains INTACT) 
     * or SUNK (if all the other squares were also HIT before the method was called). If a square was already HIT before
     * this method was called, it remains HIT
     *
     * @param offset The offset from the top/left of the ship. 
     *
     * @throws InvalidPositionException When the parameter is less than zero or 
     * greater than/equal to the size of the ship 
     */
    void shoot(int offset) throws InvalidPositionException;

    /**
     * Find the status of a square of the ship
     *
     * @param offset The offset from the top/left of the ship.
     *
     * @return the status of that square: INTACT, HIT or SUNK
     *
     * @throws InvalidPositionException When the parameter is less than zero or 
     * greater than/equal to the size of the ship 
     */
    ShipStatus getStatus(int offset) throws InvalidPositionException;
}