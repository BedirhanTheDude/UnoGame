package uno;

/**
 * Enumeration for the values of the numbers of {@link NumberCard}
 * 
 * <ul>
 *     <li>ZERO: Zero</li>
 *     <li>ONE: One</li>
 *     <li>TWO: Two</li>
 *     <li>THREE: Three</li>
 *     <li>FOUR: Four</li>
 *     <li>FIVE: Five</li>
 *     <li>SIX: Six</li>
 *     <li>SEVEN: Seven</li>
 *     <li>EIGHT: Eight</li>
 *     <li>NINE: Nine</li>
 * </ul>
 * 
 * @author Bedirhan Sakaoğlu
 */
public enum ENumber {
	ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9);
	private final int number;
	
	/**
	 * Constructor for {@link ENumber}
	 * <p>
	 * Assigns corresponding integer values to enum constants
	 * </p>
	 * 
	 * @param number corresponding number to an enum constant
	 */
	private ENumber(int number) {
		this.number = number;
	}
	
	/**
	 * 
	 * @return integer values corresponding to a enum constant
	 */
	public int getInt() {
		return number;
	}
}
