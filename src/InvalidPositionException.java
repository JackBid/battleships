
public class InvalidPositionException extends Exception
{

    public InvalidPositionException()
    {
    }

    public InvalidPositionException(String message){
        super(message);
    }

    public InvalidPositionException(Throwable cause){
        super(cause);
    }
    
    public InvalidPositionException(String message, Throwable cause){
        super(message, cause);
    }
}
