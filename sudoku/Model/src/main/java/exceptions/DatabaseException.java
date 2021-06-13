package exceptions;

public class DatabaseException extends ProgramException {
    public DatabaseException(){

    }
    public DatabaseException(String message) {
        super(message);
    }
    public DatabaseException(String message, Throwable cause){
        super(message, cause);
    }
    public DatabaseException(Throwable cause){
        super(cause);
    }
}
