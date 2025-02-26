package uno;

/**
 *
 * A WildCard is a special type of card in Uno that can be played on any color
 * and allows the player to choose the next color to continue the game.
 * <p>
 * This class extends the {@link Card} class.
 * </p>
 *
 * @author Bedirhan Sakaoğlu
 */
public class WildCard extends Card {
	
	private EColor supposedColorEnum;
	private boolean supposedColorSet = false;
	
	/**
     * Constructs a WildCard with the specified wild card action.
     *
     * @param action the action of the WildCard.
     */
	public WildCard(Action action){
		super(EColor.NONE, action);
		this.score = 50;
	}

	/**
     * Returns a string representation of the WildCard.
     *
     * @return a string containing the type of the WildCard.
     */
	@Override
	public String toString() {
		return "WildCard(" + getAction().toString() + ")";
	}
	
	/**
     * Sets the supposed color of the WildCard.
     *
     * @param color the color to set.
     */
	public void setColorEnum(EColor color) {
		supposedColorEnum = color;
		supposedColorSet = true;
	}
	
	/**
     * Gets the color of the WildCard.
     * <p>
     * If the supposed color is set, returns the supposed color,
     * otherwise returns the default color of the WildCard.
     * </p>
     *
     * @return the color of the WildCard.
     */	
	@Override
	public EColor getColorEnum() {
		return (supposedColorSet) ? supposedColorEnum : color;
	}
}
