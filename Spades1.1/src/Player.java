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
	private int tricksBid; // the number of tricks bid this round based on the
							// number of spade
	private int tricksTaken; // the number of tricks taken of turns won.
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
		tricksBid = 0;
		tricksTaken = 0;
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
		tricksBid = 0;
		tricksTaken = 0;
	}

	/*
	 * PUBLIC METHODS
	 */

	/**
	 * Gets the player's number of tricks bid
	 * 
	 * @return the number of tricks bid.
	 */
	public int getTricksBid() {
		return tricksBid;
	}

	/**
	 * Sets the number of tricks bid, based on number of spades.
	 */
	public void setTricksBid() {
		this.tricksBid = myHand.getNumSpades();
	}

	/**
	 * Gets the player's number of tricks won
	 * 
	 * @return the number of tricks taken.
	 */
	public int getTricksTaken() {
		return tricksTaken;
	}

	/**
	 * Sets the number of tricks taken by this player.
	 * 
	 * @param tricksTaken
	 *            The number of tricks taken.
	 */
	public void setTricksTaken(int tricksTaken) {
		this.tricksTaken = tricksTaken;
	}

	/**
	 * Adds one to the number of tricks taken.
	 */
	public void incrementTricksTaken() {
		this.tricksTaken++;
	}

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
	public boolean getTurnToPlay() {
		return turnToPlay;
	}

	/**
	 * Sets the player's turn to play
	 * 
	 * @param turn
	 *            True if their turn to play, false otherwise
	 */
	public void setTurnToPlay(boolean turn) {
		turnToPlay = turn;
	}

}
