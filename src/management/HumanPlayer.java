package management;

import uno.Card;

/**
 * Represents a human player in a game session.
 * <p>
 * This class extends the abstract Player class and represents a human player.
 * It inherits basic player functionality such as managing the player's deck,
 * drawing and playing cards, joining a game session, and comparing players based on their IDs.
 * Actually has no functionality different from {@link Player}
 * </p>
 *
 * @author Bedirhan Sakaoğlu
 */
public class HumanPlayer extends Player {

	/**
     * Constructs a human player object.
     *
     * @param gameSession the game session to which the human player joins.
     */
	public HumanPlayer(GameSession gameSession) {
		super(gameSession);
	}

}
