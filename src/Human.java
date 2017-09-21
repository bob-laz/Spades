/**
 * Human is a player, it has a name and a hand and plays the game of spades
 * based on user clicks through the GUI.
 * 
 * @author Bob Laskowski
 * @author Peter Klein
 * @version 1.3
 */
public class Human extends Player {

	/*
	 * CONSTRUCTORS
	 */

	/**
	 * Human constructor calls the Player constructor with no parameters.
	 */
	public Human() {
		super();
	}

	/**
	 * Human constructor calls the Player constructor and sends it a name.
	 *
	 * @param name Name of player
	 */
	public Human(String name) {
		super(name);
	}

}
