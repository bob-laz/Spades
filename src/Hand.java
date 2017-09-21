import java.util.ArrayList;
import java.util.Collections;

/**
 * Hand is a list of 13 card objects, used by any human or bot, it contains
 * methods to check the status and manipulate the cards in the hand.
 * 
 * @author Bob Laskowski
 * @author Peter Klein
 * @version 1.3
 */
public class Hand {

	/*
	 * INSTANCE VARIABLES
	 */

	private final ArrayList<Card> hand; // arrayList of 13 cards.

	/*
	 * CONSTRUCTORS
	 */

	/**
	 * creates a new array list that can store Card objects.
	 */
	public Hand() {
		hand = new ArrayList<>();
	}

	/*
	 * PUBLIC METHODS
	 */

	/**
	 * Adds a card object to the hand and sorts the hand as the card is added
	 * 
	 * @param card
	 *            Card to be added to the hand.
	 */
	public void add(Card card) {
		int i = hand.size();
		if (i < 13)
			hand.add(i, card);
		Collections.sort(hand);
	}

	/**
	 * Removes a specific card object from the hand using arrayList's remove
	 * method
	 * 
	 * @param card
	 *            Card to be removed from the hand.
	 * @return true if Card is successfully removed, false otherwise
	 */
	public boolean remove(Card card) {
		return hand.remove(card);
	}

	/**
	 * Removes a card object from the hand, based on position using arrayList's
	 * methods
	 * 
	 * @param position
	 *            The position to be removed from the array list
	 * @return The Card that is removed
	 */
	public Card remove(int position) {
		Card returnCard = hand.get(position);
		hand.remove(returnCard);
		return returnCard;
	}

	/**
	 * Returns a card at a specific position in the hand using arrayList's get
	 * method
	 * 
	 * @param position
	 *            The position to be returned from the hand
	 * @return The card at the given position
	 */
	Card getCardAtPosition(int position) {
		return hand.get(position);
	}

	/**
	 * The method getNumSpades is necessary in the game Spades because the
	 * number of spades in your hand right away is the number of bids or "wins"
	 * you must try to get in that round.
	 * 
	 * @return The number of spades in the hand
	 */
	int getNumSpades() {
		int numSpades = 0;
		for (Card aHand : hand) {
			if (aHand.getSuit().equals(Suit.SPADE))
				numSpades++;
		}
		return numSpades;
	}

	/**
	 * Prints out every card in the hand and the size of the hand
	 */
	public String toString() {
		return hand.toString() + " Length: " + hand.size();
	}

	/**
	 * checks to see if a certain card is in the hand.
	 * 
	 * @param c
	 *            The card to check.
	 * @return True if it is in the hand, else false.
	 */
	public boolean contains(Card c) {
		for (Card aHand : hand) {
			if (aHand.getNumber() == c.getNumber() && aHand.getSuit() == c.getSuit()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the number of cards in the hand
	 * 
	 * @return an integer 0-13 the is the number of cards in the hand
	 */
	public int size() {
		return hand.size();
	}
}
