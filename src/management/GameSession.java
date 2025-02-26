package management;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uno.Action;
import uno.ActionCard;
import uno.Card;
import uno.EColor;
import uno.Decks;
import uno.ENumber;
import uno.NumberCard;
import uno.WildCard;

/**
 * Represents a game session of Uno.
 * <p>
 * This class manages the game session of Uno, including player management, card piles,
 * game state, and actions such as initializing the game, playing cards, and handling
 * special card effects.
 * </p>
 *
 * @author Bedirhan Sakaoğlu
 */
public class GameSession {

	public static final int MAXNUMBEROFPLAYERS = 10;
	
	private List<Player> playerList;
	private List<Player> playerListGameOrder;
	
	private List<Card> drawPile;
	private List<Card> discardPile;

	private EColor currentColor;
	private ENumber currentCardNumber;
	
	private String gameName;
	private int playerIndexInGameOrder;
	private boolean gameStarted = false;
	private boolean topDeckIsWild = false;
	private EColor wildColor;
	private Card cardTopDeck;
	
	private Player humanPlayer;

	/**
     * Constructs a game session with the given name.
     *
     * @param gameName the name of the game session.
     */
	public GameSession(String gameName) {
		this.gameName = gameName;
		playerList = new ArrayList<Player>();
		playerListGameOrder = new ArrayList<Player>();
	}

	/**
     * Updates the fields related to the top card on the discard pile.
     */
	public void updateTopDeckFields() {
		if (discardPile.size() == 0) return;
		cardTopDeck = discardPile.get(discardPile.size() - 1);
		currentColor = cardTopDeck.getColorEnum();
		currentCardNumber = (cardTopDeck instanceof NumberCard) ? ((NumberCard)cardTopDeck).getNumber() : null;
	}
	
	/**
     * Initializes the game session with the specified number of players.
     *
     * @param playerCount the number of players to initialize the game with.
     */
	@SuppressWarnings("unchecked")
	public void initializeGame(int playerCount) {
		drawPile = Decks.initializeDrawPile();
		discardPile = new ArrayList<Card>();
		cardTopDeck = drawPile.get(drawPile.size() - 1);
		
		int trial = 1;
		while (cardTopDeck instanceof ActionCard || cardTopDeck instanceof WildCard) {
			drawPile.remove(cardTopDeck);
			drawPile.add(0, cardTopDeck);
			cardTopDeck = drawPile.get(drawPile.size() - 1);
			if (trial++ > drawPile.size()) return;
		}
		System.out.println(cardTopDeck);
		discardPile.add(cardTopDeck);
		drawPile.remove(cardTopDeck);
		
		humanPlayer = new HumanPlayer(this);
		playerList.add(humanPlayer);
		for (int i = 0; i < playerCount - 1; i++) {
			playerList.add(new AiPlayer(this));
		}
		playerListGameOrder = (List<Player>) ((ArrayList<Player>)playerList).clone();
		
		for (Player player : playerList) {
			for (int i = 0; i < 7; i++) {
				Card cardAdded = drawPile.get(drawPile.size() - 1);
				drawPile.remove(cardAdded);
				player.drawCard(cardAdded);
			}
		}
	}
	
	/**
     * Reshuffles the discard pile when the draw pile runs out of cards.
     */
	@SuppressWarnings("unchecked")
	public void reshuffleDiscardPile() {
		if (drawPile.size() >= 4) return;
		
		discardPile.remove(cardTopDeck);
		for (Card card : drawPile) {
			discardPile.add(card);
		} drawPile.clear();
		Collections.shuffle(discardPile);
		drawPile = (ArrayList<Card>) ((ArrayList<Card>)discardPile).clone();
		discardPile.clear();
		discardPile.add(cardTopDeck);
		drawPile.remove(cardTopDeck);
	}
	
	/**
     * Updates the player list with the given list of players and sorts them by player ID.
     *
     * @param playerList the list of players to update.
     */
	public void updatePlayerList(List<Player> playerList) {
		this.playerList = playerList;
		List<Player> listClone = List.copyOf(playerList);
		Collections.sort(listClone);
		this.playerListGameOrder = listClone;
	}
	
	/**
     * Adds a player to the game session.
     *
     * @param player the player to add.
     */
	public void addPlayer(Player player) {
		playerList.add(player);
	}
	
	/**
     * Removes a player from the game session.
     *
     * @param player the player to remove.
     */
	public void removePlayer(Player player) {
		playerList.remove(player);
	}
	
	// TODO: Implement each action and wild card methods
	
	/**
     * Reverses the order of players in the game order list.
     */
	public void reverse() {
		Collections.reverse(playerListGameOrder);
	}
	
	/**
     * Advances to the next player in the game order list.
     */
	public void skip() {
		playerIndexInGameOrder++;
	}
	
	 /**
     * Performs the draw card action based on the provided card's action type.
     *
     * @param card the card triggering the draw card action.
     */
	public void drawCardAction(Card card) {
		if ((card.getAction() != Action.DRAWTWO && card.getAction() != Action.WILDFOUR) || drawPile.size() < 4 || !gameStarted) return;
		int cardsToDraw = (card.getAction() == Action.DRAWTWO) ? 2 : 4;
		
		Player nextPlayer = playerListGameOrder.get(playerIndexInGameOrder + 1);
		for (int i = 0; i < cardsToDraw; i++) {
			Card cardDrawn = drawPile.get(drawPile.size() - 1); drawPile.remove(cardDrawn);
			nextPlayer.drawCard(cardDrawn);
		}
	}
	
	/**
     * Changes the current color of the game session.
     *
     * @param color the new color to set.
     */
	public void changeColor(EColor color) {
		this.currentColor = color;
	}
	
	/**
     * Checks if a given card is playable on the top card of the discard pile.
     *
     * @param card the card to check for playability.
     * @return true if the card is playable, otherwise false.
     */
	public boolean cardIsPlayable(Card card) {
		if (card instanceof WildCard) return true;
		
		if (cardTopDeck instanceof NumberCard) {
			if (card instanceof NumberCard) return ((NumberCard)card).getNumberInt() == ((NumberCard)cardTopDeck).getNumberInt() 
					|| card.getColorEnum().equals(cardTopDeck.getColorEnum());
			else {
				return card.getColorEnum().equals(cardTopDeck.getColorEnum()) || card.getColorEnum().equals(wildColor);
			}
		}
		else {
			return cardTopDeck.getColorEnum().equals(card.getColorEnum()) || card.getColorEnum().equals(wildColor)
					|| card.getAction().equals(cardTopDeck.getAction());
		}
	}

	/**
     * Gets the list of players in the game session.
     *
     * @return the list of players.
     */
	public List<Player> getPlayerList() {
		return playerList;
	}

	/**
     * Sets the list of players in the game session.
     *
     * @param playerList the list of players to set.
     */
	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}

	/**
     * Gets the list of players in the current game order.
     *
     * @return the list of players in game order.
     */
	public List<Player> getPlayerListGameOrder() {
		return playerListGameOrder;
	}

	/**
     * Sets the list of players in the current game order.
     *
     * @param playerListGameOrder the list of players in game order to set.
     */
	public void setPlayerListGameOrder(List<Player> playerListGameOrder) {
		this.playerListGameOrder = playerListGameOrder;
	}

	/**
     * Gets the draw pile of cards.
     *
     * @return the draw pile.
     */
	public List<Card> getDrawPile() {
		return drawPile;
	}

	/**
     * Sets the draw pile of cards.
     *
     * @param drawPile the draw pile to set.
     */
	public void setDrawPile(List<Card> drawPile) {
		this.drawPile = drawPile;
	}

	/**
     * Gets the discard pile of cards.
     *
     * @return the discard pile.
     */
	public List<Card> getDiscardPile() {
		return discardPile;
	}

	/**
     * Sets the discard pile of cards.
     *
     * @param discardPile the discard pile to set.
     */
	public void setDiscardPile(List<Card> discardPile) {
		this.discardPile = discardPile;
	}

	/**
     * Gets the current color of the game session.
     *
     * @return the current color.
     */
	public EColor getCurrentColor() {
		return currentColor;
	}

	/**
     * Sets the current color of the game session.
     *
     * @param currentColor the current color to set.
     */
	public void setCurrentColor(EColor currentColor) {
		this.currentColor = currentColor;
	}

	/**
     * Gets the current card number of the game session.
     *
     * @return the current card number.
     */
	public ENumber getCurrentCardNumber() {
		return currentCardNumber;
	}

	/**
     * Sets the current card number of the game session.
     *
     * @param currentCardNumber the current card number to set.
     */
	public void setCurrentCardNumber(ENumber currentCardNumber) {
		this.currentCardNumber = currentCardNumber;
	}

	/**
     * Gets the index of the current player in the game order.
     *
     * @return the index of the current player.
     */
	public int getPlayerIndexInGameOrder() {
		return playerIndexInGameOrder;
	}

	/**
     * Sets the index of the current player in the game order.
     *
     * @param playerIndexInGameOrder the index of the current player to set.
     */
	public void setPlayerIndexInGameOrder(int playerIndexInGameOrder) {
		this.playerIndexInGameOrder = playerIndexInGameOrder;
	}
	
	/**
     * Gets the top card on the discard pile.
     *
     * @return the top card on the discard pile.
     */
	public Card getCardTopDeck() {
		return cardTopDeck;
	}
	
	/**
     * Sets the card on top of the deck.
     *
     * @param cardTopDeck card to be set to the top deck.
     */
	public void setCardTopDeck(Card cardTopDeck) {
		this.cardTopDeck = cardTopDeck;
	}

	/**
     * Gets the human player in the game session.
     *
     * @return the human player.
     */
	public Player getHumanPlayer() {
		return humanPlayer;
	}
	
	/**
     * Gets the wild color set during the game session.
     *
     * @return the wild color.
     */
	public EColor getWildColor() {
		return wildColor;
	}

	/**
     * Sets the wild color during the game session.
     *
     * @param wildColor the wild color to set.
     */
	public void setWildColor(EColor wildColor) {
		this.wildColor = wildColor;
	}
	
	/**
     * Gets the name of the game session.
     *
     * @return the name of the game session.
     */
	public String getGameName() {
		return gameName;
	}
}
