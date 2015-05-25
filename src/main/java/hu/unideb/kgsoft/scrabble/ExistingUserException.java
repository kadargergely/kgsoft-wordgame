package hu.unideb.kgsoft.scrabble;

public class ExistingUserException extends RuntimeException {
    
    private static final long serialVersionUID = 4973496468100990589L;

    public ExistingUserException(String message, Throwable cause) {
        super(message, cause);        
    }

    public ExistingUserException(Throwable cause) {
        super(cause);        
    }
    
    public ExistingUserException() {
        super();
    }
}
