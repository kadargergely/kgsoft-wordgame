package hu.unideb.kgsoft.scrabble;

public class NoSavedGameException extends RuntimeException {    
    
    private static final long serialVersionUID = 510758124854850237L;

    public NoSavedGameException(String message, Throwable cause) {
        super(message, cause);        
    }

    public NoSavedGameException(Throwable cause) {
        super(cause);        
    }
    
    public NoSavedGameException() {
        super();
    }
}
