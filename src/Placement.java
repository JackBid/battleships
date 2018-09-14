
public class Placement
{
    private Position position;
    private boolean isVertical;
    
    public Placement(Position position, boolean isVertical){
        this.position = position;
        this.isVertical = isVertical;
    }
    
    public Position getPosition(){
        return position;
    }
    
    public boolean isVertical(){
        return isVertical;
    }
}
