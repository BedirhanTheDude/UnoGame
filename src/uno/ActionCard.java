package uno;

/**
 * Class representing an action card in Uno.
 * <p>
 * Action cards are special Uno cards that perform specific actions
 * when played, such as skipping a player's turn or forcing the next
 * player to draw cards.
 * </p>
 *
 * @author Bedirhan Sakaoğlu
 */
public class ActionCard extends Card {
	
	/**
     * Constructs a new action card with the specified color and action.
     *
     * @param color  the color of the action card.
     * @param action the action associated with the action card.
     */
	public ActionCard(EColor color, Action action){
		super(color, action);
		this.score = 20;
	}

	/**
     * Returns the string representation of the action card.
     *
     * @return a string containing information about the action card, including its color and action.
     */
	@Override
	public String toString() {
		return String.format("ActionCard(%s, %s)", getColorEnum().toString(), getAction().toString());
	}
	
}
