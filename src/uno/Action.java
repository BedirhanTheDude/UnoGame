package uno;

/**
 * Enum representing actions associated with Uno cards.
 * <p>
 * Each action corresponds to a specific behavior or effect when the
 * corresponding Uno card is played during the game.
 * </p>
 * <p>
 * All the actions available:
 * <ul>
 *     <li>NUMBER: Represents a regular number card.</li>
 *     <li>DRAWTWO: Forces the next player to draw two cards.</li>
 *     <li>REVERSE: Reverses the direction of play.</li>
 *     <li>SKIP: Skips the next player's turn.</li>
 *     <li>WILD: Allows the player to choose the next color.</li>
 *     <li>WILDFOUR: Forces the next player to draw four cards and allows the player to choose the next color.</li>
 * </ul>
 * </p>
 *
 * @author Bedirhan Sakaoğlu
 */
public enum Action {
	NUMBER("Number"), DRAWTWO("DrawTwo"), REVERSE("Reverse"), SKIP("Skip"), WILD("Wild"), WILDFOUR("WildDrawFour");
	
	private String _string;
	
	/**
     * Constructs an action enum with the specified string representation.
     *
     * @param value the string representation of the action.
     */
	private Action(String value) {
		_string = value;
	}
	
	/**
     * Gets the string representation of the action.
     *
     * @return the string representation of the action.
     */
	public String getString() {
		return _string;
	}
}
