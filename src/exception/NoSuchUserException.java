package exception;

/**
 * Custom exception class indicating that no user with the specified username exists.
 */
public class NoSuchUserException extends Exception {
	
	private static final long serialVersionUID = 7798895937263760400L;

	/**
     * Constructs a new NoSuchUserException with a message indicating the non-existent username.
     * @param username The username that does not exist.
     */
	public NoSuchUserException(String username) {
		super("There exists no user with the name: ".concat(username));
	}
}
