package uno;

/**
 * A NumberCard is a type of card in Uno that contains a number and a color.
 * <p>
 * This class extends the {@link Card} class.
 * </p>
 *
 * @author Bedirhan Sakaoğlu
 */
public class NumberCard extends Card {
	
	/** The number of the card. */
	private ENumber number;
	
	/**
     * Constructs a NumberCard with the given color and number.
     *
     * @param color  the color of the card.
     * @param number the number of the card.
     */
	public NumberCard(EColor color, ENumber number) {
		super(color, Action.NUMBER);
		this.number = number; this.score = number.getInt();
	}
	
	/**
     * Gets the integer value of the number of the card.
     *
     * @return the integer value of the card's number.
     */
	public int getNumberInt() {
		return number.getInt();
	}
	
	/**
     * Gets the enum value of the number of the card.
     *
     * @return the enum value of the card's number.
     */
	public ENumber getNumber() {
		return number;
	}

	/**
     * Returns a string representation of the NumberCard.
     *
     * @return a string containing the color and number of the card.
     */
	@Override
	public String toString() {
		return String.format("NumberCard(%s, %d)", getColorEnum().toString(), getNumberInt());
	}
	
}
