
public class Position
{
    private int x, y;
    
    public Position(int x, int y) throws InvalidPositionException{
        setX(x);
        setY(y);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    /**
     * @param x the x position of the location. 1 is on the left, 10 is on the right
     * @throws InvalidPositionException if the parameter is less than 0 or more than 10
     */
    public void setX(int x) throws InvalidPositionException{
        if(x<1 || x>10){
            throw new InvalidPositionException("" + x);
        }
        this.x = x;
    }

    /**
     * 
     * @param y the y position of the location. 1 is at the top, 10 is at the bottom
     * @throws InvalidPositionException if the parameter is less than 1 or more than 10
     */
    public void setY(int y) throws InvalidPositionException{
        if(y<1 || y>10){
            throw new InvalidPositionException("" + y);
        } 
        this.y = y;
    }
    

    public String toString(){
        return x + "," + y;
    }

}
