package grupoalan.backendgalan.exceptions;

public class InvalidAccountTypeException extends RuntimeException{
    public InvalidAccountTypeException() {
        super();
    }

    public InvalidAccountTypeException(String message) {
        super(message);
    }

    public InvalidAccountTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAccountTypeException(Throwable cause) {
        super(cause);
    }
}
