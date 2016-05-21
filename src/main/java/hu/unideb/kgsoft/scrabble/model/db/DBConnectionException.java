package hu.unideb.kgsoft.scrabble.model.db;

public class DBConnectionException extends Exception {
	
	private static final long serialVersionUID = -741313723514686447L;

	public DBConnectionException() {
		super();
	}
	
	public DBConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBConnectionException(Throwable cause) {
		super(cause);
	}
}
