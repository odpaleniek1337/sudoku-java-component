package exceptions;

public class FileException extends ProgramException {
    public FileException() {
    }

    public FileException(String string) {
        super(string);
    }

    public FileException(Throwable throwable) {
        super(throwable);
    }

    public FileException(String string, Throwable throwable) {
        super(string, throwable);
    }
}