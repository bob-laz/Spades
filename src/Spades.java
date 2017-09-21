/*
 * 
 * 
 * 			SSSSSSSS	PPPPPPPP	 AAAAAA 	DDDDDDDD	EEEEEEEE	SSSSSSSS
 * 			S			P	   P	A	   A	D		D	E			S
 * 			SSSSSSSS	PPPPPPPP	AAAAAAAA	D		D	EEEEE		SSSSSSSS
 *				   S	P			A	   A	D		D	E				   S
 *			SSSSSSSS	P			A	   A	DDDDDDDD	EEEEEEEE	SSSSSSSS
 */

/**
 *		This is the game executor, it calls the welcome GUI, which starts the whole game.
 *
 *		@author Bob Laskowski
 *		@author Peter Klein
 *		@version 1.3
 *
 *
 */
public class Spades {
	
	/**
	 * Starts the game.
	 * 
	 * @param args The command line arguments
	 */
	public static void main(String[] args)
	{
		WelcomeGUI welGUI = new WelcomeGUI();
		welGUI.display();
	}
}
