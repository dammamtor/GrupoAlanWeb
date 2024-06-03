package grupoalan.backendgalan.exceptions;

public class UserAlreadyEnabledException extends RuntimeException{
    public UserAlreadyEnabledException() {
        super();
    }

    public UserAlreadyEnabledException(String message) {
        super(message);
    }

    public UserAlreadyEnabledException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyEnabledException(Throwable cause) {
        super(cause);
    }
}
