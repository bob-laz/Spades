import java.util.ArrayList;

/**
 * Driver helps run the game, but the primary running of the game takes place in
 * GUI. Driver controls the memory object for the bots, which stores all cards
 * that have been played. It also defines the Players, whether they are Human or
 * Bot based on the selections in the Welcome GUI. Additionally, it has a method
 * to determine the winner of each round that is called from GUI. A new driver
 * object is made in GUI at the start of each hand.
 * 
 * @author Bob Laskowski
 * @author Peter Klein
 * @version 1.3
 *
 */
public class Driver {

	/*
	 * INSTANCE VARIABLES
	 */

	public String[] playerAry; // number of players(bot or human).
	public Player[] players; // number of players in total.
	public Deck deck; // the deck to be used for the game.
	public static Memory memory; // the memory to be used for the game.

	/*
	 * CONSTRUCTORS
	 */

	/**
	 * Creates all the players, deck and memory and passes it into the game.
	 * 
	 * @param screenRes
	 *            the screen resolution.
	 */
	public Driver(int screenRes) {
		deck = new Deck(screenRes);
		memory = new Memory();
		playerAry = WelcomeGUI.getPlayers();
		players = new Player[4];
		for (int i = 0; i < playerAry.length; i++) {
			if (playerAry[i].equals("Bot"))
				players[i] = new Bot();
			else
				players[i] = new Human();
		}
		// players[0].turnToPlay = true;

	}

	/**
	 * Deals hand to every player from the deck
	 */
	public void dealHands() {
		for (int i = 0; i < players.length; i++) {
			players[i].setHand(deck.dealHand());
		}
	}

	/**
	 * Determine winner occurs at the end of each round, it takes in the four
	 * cards played and determines the winner of that round
	 * 
	 * @param c
	 *            the four cards played that round
	 * @return the card that won that round.
	 */
	public Card determineWinner(ArrayList<Card> c) {
		Card card0 = c.get(0);
		Card card1 = c.get(1);
		Card card2 = c.get(2);
		Card card3 = c.get(3);

		Card highCard = null;

		// find the highest card, if you only have spades and one other suit.
		if (Card.compareTo2(card0, card1) == 1) {
			highCard = new Card(card0.getSuit(), card0.getNumber());
		} else {
			if (card1.getSuit() == Suit.SPADE || card1.getSuit() == card0.getSuit()) {
				highCard = new Card(card1.getSuit(), card1.getNumber());
			}
		}

		if (Card.compareTo2(highCard, card2) == -1) {
			if (card2.getSuit() == Suit.SPADE || card2.getSuit() == card0.getSuit()) {
				highCard = new Card(card2.getSuit(), card2.getNumber());
			}
		}
		if (Card.compareTo2(highCard, card3) == -1) {
			if (card3.getSuit() == Suit.SPADE || card3.getSuit() == card0.getSuit()) {
				highCard = new Card(card3.getSuit(), card3.getNumber());
			}
		}

		// ad all cards to memory
		memory.add(c.get(0));
		memory.add(c.get(1));
		memory.add(c.get(2));
		memory.add(c.get(3));

		// return the card of the winner
		return highCard;
	}

}
