
public class ShipOverlapException extends Exception
{
    public ShipOverlapException()
    {
    }

    public ShipOverlapException(String message){
        super(message);
    }

    public ShipOverlapException(Throwable cause){
        super(cause);
    }
    
    public ShipOverlapException(String message, Throwable cause){
        super(message, cause);
    }
}
