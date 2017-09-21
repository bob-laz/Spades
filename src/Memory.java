import java.util.ArrayList;

/**
 * Memory is used by the Bot. every time a card is player, it is sent to memory.
 * The bot then uses what is in memory in certain situations to figure out which
 * card should be played next.
 * 
 * @author Bob Laskowski
 * @author Peter Klein
 * @version 1.3
 */
public class Memory {

	/*
	 * INSTANCE VARIABLES
	 */

	private final ArrayList<Card> memory; // array list of cards already played
											// this round

	/*
	 * CONSTRUCTORS
	 */

	/**
	 * Memory constructor creates an array list that can store cards.
	 */
	public Memory() {
		memory = new ArrayList<>();
	}

	/*
	 * PUBLIC METHODS
	 */

	/**
	 * Adds a card to the memory using arrayList's add method
	 * 
	 * @param card
	 *            The card to be added to memory.
	 */
	public void add(Card card) {
		memory.add(card);
	}

	/**
	 * Checks to see if a certain card is in the memory.
	 * 
	 * @param c
	 *            The card to check.
	 * @return True if it is in the memory, else false.
	 */
	public boolean contains(Card c) {
		for (Card aMemory : memory) {
			if (aMemory.getNumber() == c.getNumber() && aMemory.getSuit() == c.getSuit()) {
				return true;
			}
		}
		return false;
	}

}
