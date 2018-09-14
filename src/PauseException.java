
public class PauseException extends Exception
{
    public PauseException()
    {
    }

    public PauseException(String message){
        super(message);
    }

    public PauseException(Throwable cause){
        super(cause);
    }
    
    public PauseException(String message, Throwable cause){
        super(message, cause);
    }
}
