package uno;

import java.awt.Color;

/**
 * Enumeration of colors used in Uno cards.
 * <p>
 * Each color has a corresponding text representation and a corresponding {@link java.awt.Color} object.
 * </p>
 * 
 * All the colors available:
 * <ul>
 *     <li>RED: Color Red</li>
 *     <li>GREEN: Color Green</li>
 *     <li>BLUE: Color Blue</li>
 *     <li>YELLOW: Color Yellow</li>
 * </ul>
 *
 * @author Bedirhan Sakaoğlu
 */
public enum EColor {
	RED("Red", new Color(225, 10, 60)), GREEN("Green", new Color(20, 155, 20)), BLUE("Blue", new Color(40, 85, 240)), YELLOW("Yellow", new Color(233, 192, 26)), NONE("None", null);
	
	private final String text;
	private final Color color;
	
	/**
     * Constructor for {@link EColor}
     *
     * @param text  the text representation of the color.
     * @param color the corresponding java.awt.Color object of the color.
     */
	private EColor(String text, Color color) {
		this.text = text;
		this.color = color;
	}
	
	/**
     * Gets the string representation of the color.
     *
     * @return the string representation of the color.
     */
	public String getString() {
		return text;
	}
	
	/**
     * Gets the color object representing the EColor enum.
     *
     * @return the color object representing the EColor enum.
     */
	public Color getColor() {
		return color;
	}
}