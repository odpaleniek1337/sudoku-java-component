package exceptions;

public class NoBoardTableException extends DatabaseException {

    public NoBoardTableException(){

    }
    public NoBoardTableException(String message) {
        super(message);
    }
    public NoBoardTableException(String message, Throwable cause){
        super(message, cause);
    }
    public NoBoardTableException(Throwable cause){
        super(cause);
    }
}
