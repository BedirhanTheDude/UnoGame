package exception;

/**
 * Custom exception class indicating that the provided input is invalid.
 */
public class InvalidInputException extends Exception {

	private static final long serialVersionUID = 8004458251385205886L;

	/**
     * Constructs a new InvalidInputException with a message describing the invalid input.
     * @param msg A message describing the invalid input.
     */
	public InvalidInputException(String msg) {
		super("The input provided is invalid: ".concat(msg));
	}
	
}
