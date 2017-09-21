/**
 * Player is extended by both bot and human. It has a hand and tricks.
 * 
 * @author Bob Laskowski
 * @author Peter Klein
 * @version 1.3
 */
public class Player {

	/*
	 * INSTANCE VARIABLES
	 */

	private Hand myHand; // the hand of cards a player has.
	private boolean turnToPlay; // true if it is this players turn to play
	private String name; // the name of the player.

	/*
	 * CONSTRUCTORS
	 */

	/**
	 * Player constructor creates a new player with default name, hand, tricks
	 * taken, and tricks bid.
	 */
	public Player() {
		name = "Player";
		myHand = null;
	}

	/**
	 * player constructor creates a new player with a specified name, but
	 * default hand, tricks taken, and tricks bid.
	 * 
	 * @param n
	 *            The name of the player.
	 */
	public Player(String n) {
		name = n;
		myHand = null;
	}

	/*
	 * PUBLIC METHODS
	 */

	/**
	 * Sets the player's name
	 * 
	 * @param name
	 *            The name of the player.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of the player
	 * 
	 * @return the name of the player.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the hand of the player
	 * 
	 * @param hand
	 *            The hand to be assigned to this player.
	 */
	public void setHand(Hand hand) {
		myHand = hand;
	}

	/**
	 * Gets the hand of cards for the player
	 * 
	 * @return The hand object of this player.
	 */
	public Hand getHand() {
		return myHand;
	}

	/**
	 * Checks to see if it is this player's turn to play.
	 * 
	 * @return Return true if their turn to play, false otherwise
	 */
	boolean getTurnToPlay() {
		return turnToPlay;
	}

	/**
	 * Sets the player's turn to play
	 * 
	 * @param turn
	 *            True if their turn to play, false otherwise
	 */
	void setTurnToPlay(boolean turn) {
		turnToPlay = turn;
	}

}
