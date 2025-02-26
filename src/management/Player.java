package management;

import java.util.ArrayList;

import uno.Card;

/**
 * Abstract class representing a player in a game session.
 * <p>
 * This class provides basic functionality for managing a player's deck, joining a game session,
 * drawing and playing cards, and comparing players based on their IDs.
 * </p>
 *
 * @author Bedirhan Sakaoğlu
 */
public abstract class Player implements Comparable<Player>{
	private static int numberOfPlayerObjects;
	
	protected int playerID;
	protected ArrayList<Card> deck;
	protected GameSession gameSession;
	
	/**
     * Constructs a player object and initializes the deck.
     *
     * @param gameSession the game session in which the player participates.
     */
	public Player(GameSession gameSession) {
		deck = new ArrayList<Card>();
		playerID = numberOfPlayerObjects++;
		joinGameSession(gameSession);
	}
		
	/**
     * Gets the player's deck.
     *
     * @return the player's deck.
     */
	public ArrayList<Card> getDeck() {
		return this.deck;
	}
	
	/**
     * Draws a card and adds it to the player's deck.
     *
     * @param cardDrawn the card drawn from the draw pile.
     */
	public void drawCard(Card cardDrawn) {
		if (gameSession == null) return;
		deck.add(cardDrawn);
		gameSession.getDrawPile().remove(cardDrawn);
	}
	
	/**
     * Plays a card from the player's deck.
     *
     * @param cardPlayed the card to be played.
     */
	public void playCard(Card cardPlayed) {
		if (gameSession == null) return;
		deck.remove(cardPlayed);
		gameSession.getDiscardPile().add(cardPlayed);
		gameSession.updateTopDeckFields();
	}
	
	/**
     * Joins a game session.
     *
     * @param gameSession the game session to join.
     */
	public void joinGameSession(GameSession gameSession) {
		this.gameSession = gameSession;
	}
	
	/**
     * Gets the number of player objects created.
     *
     * @return the number of player objects created.
     */
	public int getNumberOfPlayerObjects() {
		return numberOfPlayerObjects;
	}

	/**
     * Gets the player's ID.
     *
     * @return the player's ID.
     */
	public int getPlayerID() {
		return playerID;
	}

	/**
     * Gets the game session in which the player participates.
     *
     * @return the game session.
     */
	public GameSession getGameSession() {
		return gameSession;
	}
	
	/**
     * Gets the game session in which the player participates.
     *
     * @return the game session.
     */
	@Override
	public int compareTo(Player player) {
		if (this.playerID == player.getPlayerID()) return 0;
		return (this.playerID < player.getPlayerID()) ? -1 : 1;
	}
	
	/**
     * Compares this player with another player based on their IDs.
     *
     * @param player the player to compare with.
     * @return 0 if the players have the same ID, a negative value if this player's ID is less than the other player's ID,
     *         or a positive value if this player's ID is greater than the other player's ID.
     */
	@Override
	public String toString() {
		return "PlayerID: " + getPlayerID() + "\n";
	}
}
