package uno;

import java.util.Random;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling Uno decks.
 * <p>
 * This class provides methods for initializing, shuffling, and sorting Uno decks,
 * as well as generating string representations of decks.
 * </p>
 *
 * @author Bedirhan Sakaoğlu
 */
public class Decks {

	private final static int NUMBERCARDSPERCOLOR = 19;
	private final static int NUMBEROFWILDCARDS = 8;
	
	/**
     * Initializes the draw pile for an Uno game.
     *
     * @return the initialized draw pile as a list of cards.
     */
	public static List<Card> initializeDrawPile() {
		List<Card> deck = new ArrayList<Card>();
		for (EColor color : EColor.values()) {
			if (color.equals(EColor.NONE)) continue;
			for (Action action : Action.values()) {
				if (action.equals(Action.WILD) || action.equals(Action.WILDFOUR) || action.equals(Action.NUMBER)) continue;
				deck.add(new ActionCard(color, action)); deck.add(new ActionCard(color, action));
			}
			deck.add(new NumberCard(color, ENumber.ZERO));
			for (int i = 0; i < NUMBERCARDSPERCOLOR - 1; i++) {
				deck.add(new NumberCard(color, ENumber.values()[i % 9 + 1])); // adds every number twice except zero
			}
		}
		for (int i = 0; i < NUMBEROFWILDCARDS; i++) {
			deck.add(new WildCard((i < NUMBEROFWILDCARDS/2) ? Action.WILD : Action.WILDFOUR));
		}
		shuffleDeck(deck);
		return deck;
	}
	
	/**
     * Shuffles a deck randomly.
     *
     * @param deck the deck to shuffle.
     */
	private static void shuffleDeck(List<Card> deck) {
		Random random = new Random();
		random.setSeed(random.nextInt()); // set a random seed
		Collections.shuffle(deck, random);
	}
	
	/**
     * Sorts a deck in Uno card order.
     *
     * @param deck the deck to sort.
     * @return the sorted deck.
     */
	public static ArrayList<Card> sortedDeck(List<Card> deck) {
		// Sorting order -> WildDraw4 -> Wild -> Red -> Green -> Blue -> Yellow
		ArrayList<Card> arrayListDeck = ((ArrayList<Card>)deck);
		ArrayList<Card> sortedDeck = new ArrayList<Card>();
		// Wild
		addCardTypeToDeck(arrayListDeck, sortedDeck, Action.WILDFOUR);
		addCardTypeToDeck(arrayListDeck, sortedDeck, Action.WILD);
		// Red
		addCardColorToDeck(arrayListDeck, sortedDeck, EColor.RED);
		// Green
		addCardColorToDeck(arrayListDeck, sortedDeck, EColor.GREEN);
		// Blue
		addCardColorToDeck(arrayListDeck, sortedDeck, EColor.BLUE);
		// Yellow
		addCardColorToDeck(arrayListDeck, sortedDeck, EColor.YELLOW);
		
		return sortedDeck;
	}
	
	/**
     * Adds cards of a specific color to a deck in Uno card order. 
     * Has no real use outside this class, and is purely used to sort decks in a custom fashion.
     *
     * @param deckFrom the source deck to extract cards from.
     * @param deckTo   the destination deck to add sorted cards to.
     * @param color    the color of cards to add.
     */
	private static void addCardColorToDeck(ArrayList<Card> deckFrom, ArrayList<Card> deckTo, EColor color) {
		// Sorting inside color -> Draw2 -> Reverse -> Skip -> NumericOrder
		addCardTypeToDeck(deckFrom, deckTo, Action.DRAWTWO, color); // Draw2
		addCardTypeToDeck(deckFrom, deckTo, Action.REVERSE, color); // Reverse
		addCardTypeToDeck(deckFrom, deckTo, Action.SKIP, color); // Skip
		addCardTypeToDeck(deckFrom, deckTo, color); // NumericOrder
	}
	
	/**
     * Adds cards of a specific action to a deck.
     * Has no real use outside this class, and is purely used to sort decks in a custom fashion.
     *
     * @param deckFrom the source deck to extract cards from.
     * @param deckTo   the destination deck to add cards to.
     * @param action   the action of cards to add.
     */
	private static void addCardTypeToDeck(ArrayList<Card> deckFrom, ArrayList<Card> deckTo, Action action) {
		for (Card card : deckFrom) {
			if (card.getAction().equals(action)) {
				deckTo.add(card);
			}
		}
	}
	
	/**
     * Adds cards of a specific action and color to a deck.
     * Has no real use outside this class, and is purely used to sort decks in a custom fashion.
     *
     * @param deckFrom the source deck to extract cards from.
     * @param deckTo   the destination deck to add cards to.
     * @param action   the action of cards to add.
     * @param color    the color of cards to add.
     */
	private static void addCardTypeToDeck(ArrayList<Card> deckFrom, ArrayList<Card> deckTo, EColor color) {
		ArrayList<NumberCard> unsortedMonoColorDeck = new ArrayList<NumberCard>();
		for (Card card : deckFrom) {
			if (card.getColorEnum().equals(color) && card.getClass().getSimpleName().equals("NumberCard")) {
				unsortedMonoColorDeck.add(((NumberCard)card));
			}
		}
		
		unsortedMonoColorDeck.sort((o1, o2) -> (o1.getNumberInt() < o2.getNumberInt()) ? -1 : 
			  	o1.getNumberInt() > o2.getNumberInt() ? 1 : 0);
		
		for (Card card : unsortedMonoColorDeck) {
			deckTo.add(card);
		}
	}
	
	/**
     * Adds cards of a specific action and color to a deck.
     * Has no real use outside this class, and is purely used to sort decks in a custom fashion.
     *
     * @param deckFrom the source deck to extract cards from.
     * @param deckTo   the destination deck to add cards to.
     * @param action   the action of cards to add.
     * @param color    the color of cards to add.
     */
	private static void addCardTypeToDeck(ArrayList<Card> deckFrom, ArrayList<Card> deckTo, Action action, EColor color) {
		for (Card card : deckFrom) {
			if (card.getAction().equals(action) && card.getColorEnum().equals(color)) {
				deckTo.add(card);
			}
		}
	}
}
