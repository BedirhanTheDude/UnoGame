package management;

import java.awt.Color;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import uno.Action;
import uno.Card;
import uno.EColor;
import uno.WildCard;

/**
 * Represents an AI player in the game.
 * <p>
 * An AI player makes decisions based on the game rules and current state to play cards or draw from the deck.
 * </p>
 * <p>
 * AI players have random names assigned to them from a predefined list.
 * </p>
 * <p>
 * This class extends the {@link Player} class and implements AI-specific gameplay logic.
 * </p>
 * 
 * @author Bedirhan Sakaoğlu
 */
public class AiPlayer extends Player {
	
	private GameLogListener logListener = new GameLogListener();
	private static final String[] botNames = {"Connor", "Markus", "Kara", "Hank", "Mat", "John", "Evelyn", "Emily", "Mike"};
	private static int botID = 0;
	
	private String botName;
	
	/**
	 * Constructs a new AI player with a randomly assigned name.
	 *
	 * @param gameSession the game session this player belongs to.
	 */
	public AiPlayer(GameSession gameSession) {
		super(gameSession);
		botName = botNames[botID++];
	}

	/**
	 * Get a list of playable cards from the AI player's deck.
	 *
	 * @return a list of playable cards.
	 */
	private List<Card> getPlayableCards() {
		List<Card> playableCards = new ArrayList<Card>();
 		for (Card card : getDeck()) {
			if (gameSession.cardIsPlayable(card)) {
				playableCards.add(card);
			}
		}
		return playableCards;
	}
	
	/**
	 * Plays a random card from the AI player's deck.
	 *
	 * @return an integer representing the outcome of the play action:
	 *         0 - Drawn a card from the deck.
	 *         1 - Played a regular card.
	 *         2 - Played a wild card with a chosen color.
	 *         3 - No available actions (draw pile is empty).
	 *         4 - Played a skip card.
	 */
	public int playRandom() {
		List<Card> cards = getPlayableCards();
		
		if (cards.size() == 0) {
			List<Card> drawPile = gameSession.getDrawPile();
			if (drawPile.size() == 0) return 3;
			Card cardDrawn = drawPile.get(drawPile.size() - 1);
			drawCard(cardDrawn);
			System.out.println(botName + " drew a card.\n");
			String logEntry = String.format("%s drew a card: %s", botName, cardDrawn);
			logListener.updateGameLogEvent(logListener.new GameLogEvent(this, logEntry, gameSession.getGameName()));
			return 0;
		}
		else {
			SecureRandom random = new SecureRandom();
			random.setSeed(random.nextLong());
			int randomInt = random.nextInt(0, cards.size());
			Card cardPlayed = cards.get(randomInt);
			if (cardPlayed instanceof WildCard) {
				WildCard wild = (WildCard) cardPlayed;
				EColor[] colors = {EColor.RED, EColor.GREEN, EColor.BLUE, EColor.YELLOW};
				
				EColor wildColor = colors[random.nextInt(0, 4)];
				wild.setColorEnum(wildColor);
				playCard(wild);
				System.out.println(botName + " played " + wild);
				String logEntry = String.format("%s played card: %s with color: ", botName, wild, wildColor.toString());
				logListener.updateGameLogEvent(logListener.new GameLogEvent(this, logEntry, gameSession.getGameName()));
				return 2;
			} else if (cardPlayed.getAction() == Action.REVERSE) {
				playCard(cardPlayed);
				System.out.println(botName + " played " + cardPlayed);
				String logEntry = String.format("%s played card: %s", botName, cardPlayed);
				logListener.updateGameLogEvent(logListener.new GameLogEvent(this, logEntry, gameSession.getGameName()));
				return 0;
			} else if (cardPlayed.getAction() == Action.SKIP) {
				playCard(cardPlayed);
				System.out.println(botName + " played " + cardPlayed);
				String logEntry = String.format("%s played card: %s", botName, cardPlayed);
				logListener.updateGameLogEvent(logListener.new GameLogEvent(this, logEntry, gameSession.getGameName()));
				return 4;
			}
			else {
				playCard(cardPlayed);
				System.out.println(botName + " played " + cardPlayed);
				String logEntry = String.format("%s played card: %s", botName, cardPlayed);
				logListener.updateGameLogEvent(logListener.new GameLogEvent(this, logEntry, gameSession.getGameName()));
				return 1;
			}
		}
	}
	
	/**
	 * Forces the AI player to draw a specified number of cards from the deck.
	 *
	 * @param num the number of cards to draw.
	 * @return an integer representing the outcome of the draw action:
	 *         1 - Cards drawn successfully.
	 *         3 - Insufficient cards in the draw pile.
	 */
	public int forcedDrawCard(int num) {
		List<Card> drawPile = gameSession.getDrawPile();
		if (drawPile.size() < num) return 3;
		for (int i = 0; i < num; i++) {
			Card cardDrawn = drawPile.get(drawPile.size() - 1);
			drawCard(cardDrawn);
			System.out.println(botName + " drew a card.\n");
			String logEntry = String.format("%s drew a card: %s", botName, cardDrawn);
			logListener.updateGameLogEvent(logListener.new GameLogEvent(this, logEntry, gameSession.getGameName()));
		}
		return 1;
	}

	/**
	 * Gets the ID of the AI player.
	 *
	 * @return the bot ID.
	 */
	public static int getBotID() {
		return botID;
	}

	/**
	 * Sets the ID of the AI player.
	 *
	 * @param botID the bot ID to set.
	 */
	public static void setBotID(int botID) {
		AiPlayer.botID = botID;
	}

	/**
	 * Gets the name of the AI player.
	 *
	 * @return the bot name.
	 */
	public String getBotName() {
		return botName;
	}

	/**
	 * Sets the name of the AI player.
	 *
	 * @param botName the bot name to set.
	 */
	public void setBotName(String botName) {
		this.botName = botName;
	}

	/**
	 * Gets the array of predefined bot names.
	 *
	 * @return the array of bot names.
	 */
	public static String[] getBotnames() {
		return botNames;
	}
}
