package exceptions;

public class ProgramException extends Exception {

    public ProgramException() {
    }

    public ProgramException(String string) {
        super(string);
    }

    public ProgramException(Throwable throwable) {
        super(throwable);
    }

    public ProgramException(String string, Throwable throwable) {
        super(string, throwable);
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }
}
