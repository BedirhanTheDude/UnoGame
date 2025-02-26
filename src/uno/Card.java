package uno;

import java.util.ArrayList;

/**
 * Abstract class representing a generic Uno card.
 * <p>
 * This class serves as the base for all types of Uno cards.
 * It provides common attributes and methods that are shared
 * among different types of Uno cards.
 * </p>
 *
 * @author Bedirhan Sakaoğlu
 */
public abstract class Card {
	
	protected EColor color;
	private Action action;
	protected int score = 0;
	
	/**
     * Constructs a new Uno card with the specified color and action. Isn't really supposed to initialize a card object since {@link Card} is abstract.
     *
     * @param color  the color of the card.
     * @param action the action of the card.
     */
	public Card(EColor color, Action action) {
		this.color = color; this.action = action;
	}
	
	/**
     * Gets the color of the card.
     *
     * @return the color of the card as an EColor enum value.
     */
	public EColor getColorEnum() {
		return color;
	}
	
	/**
     * Gets the action of the card.
     *
     * @return the action of the card as an Action enum value.
     */
	public Action getAction() {
		return action;
	}
	
	/**
     * Gets the score associated with the card.
     *
     * @return the score of the card.
     */
	public int getScore() {
		return score;
	}
	
	/**
     * Generates a string representation of the card.
     *
     * @return a string containing information about the card, including its color, action, and score.
     */
	@Override
	public String toString() {
		return String.format("Color: %s%nAction: %s%nScore: %d%n-----", color.getString(), action.getString(), score);
	}
}
