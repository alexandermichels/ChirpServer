public class UserAppException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UserAppException(String message) {
		super(message);
	}
	
	public UserAppException(String message, Throwable cause) {
		super(message,cause);
	}

}
