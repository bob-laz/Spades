import javax.swing.ImageIcon;
import java.awt.Image;


/**	The card class creates a card object that contains a 
 * suit, a number, and a image that corresponds to that
 * suit and number. It is used in hand, deck and memory.
 * 
 *@author Bob Laskowski
 *@author Peter Klein
 *@version 1.3
 */
@SuppressWarnings("rawtypes")
public class Card implements Comparable {

	/*
	 * INSTANCE VARIABLES
	 */

	private Suit mySuit; // the suit of the card.
	private int number; // the number of the card.
	private ImageIcon image; // the Image of the card based on the suit and number.

	/*
	 * CONSTRUCTORS
	 */

	/**
	 * Card constructor that make a new card based on suit, number and screen
	 * resolution.
	 * 
	 * @param theSuit
	 *            the suit of the card, of the enumerated type Suit, defined in
	 *            the Suit class, either Spade, Club, Diamond or Heart
	 * @param theNum
	 *            the number of the card, 1-13
	 * @param screenRes
	 *            the screen resolution of the game, either 0 or 1
	 */
	public Card(Suit theSuit, int theNum, int screenRes) {
		mySuit = theSuit;
		number = theNum;
		setImage(screenRes);
	}

	/**
	 * Card constructor that makes a new card based on the suit and number, used
	 * for defining a card for comparisons that will not actually be displayed
	 * 
	 * @param theSuit
	 *            the suit of the card.
	 * @param theNum
	 *            the number of the card.
	 */
	public Card(Suit theSuit, int theNum) {
		mySuit = theSuit;
		number = theNum;
	}

	/*
	 * PUBLIC METHODS
	 */

	/**
	 * Returns this cards suit.
	 * 
	 * @return the suit of the card.
	 */
	public Suit getSuit() {
		return mySuit;
	}

	/**
	 * Returns this cards number.
	 * 
	 * @return the number of the card.
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Compares two cards to see if their suit and number are equal. Do not need
	 * to be the same card object
	 * 
	 * @param o
	 *            A card object to compare with this card.
	 */
	public boolean equals(Object o) {
		if (this.getSuit().equals(((Card) o).getSuit())) {
			if (this.getNumber() == ((Card) o).getNumber())
				return true;
		}
		return false;
	}

	/**
	 * Returns the cards name based on its suit and number.
	 * 
	 * @return the name of the card "____ of ____", for example "Ace of Hearts"
	 */
	public String getName() {
		String cardName = "";

		switch (this.getNumber()) {
		case 1:
			cardName += "Ace of ";
			break;
		case 2:
			cardName += "2 of ";
			break;
		case 3:
			cardName += "3 of ";
			break;
		case 4:
			cardName += "4 of ";
			break;
		case 5:
			cardName += "5 of ";
			break;
		case 6:
			cardName += "6 of ";
			break;
		case 7:
			cardName += "7 of ";
			break;
		case 8:
			cardName += "8 of ";
			break;
		case 9:
			cardName += "9 of ";
			break;
		case 10:
			cardName += "10 of ";
			break;
		case 11:
			cardName += "Jack of ";
			break;
		case 12:
			cardName += "Queen of ";
			break;
		case 13:
			cardName += "King of ";
			break;
		default:
			break;
		}

		switch (this.getSuit()) {
		case SPADE:
			cardName += "Spades";
			break;
		case CLUB:
			cardName += "Clubs";
			break;
		case HEART:
			cardName += "Hearts";
			break;
		case DIAMOND:
			cardName += "Diamonds";
			break;
		default:
			break;
		}

		return cardName;
	}

	/**
	 * Gets the image of the card
	 * 
	 * @return An ImageIcon holding the card image
	 */
	public ImageIcon getImage() {
		return image;
	}

	/**
	 * Prints the name of the card using the getName method
	 */
	public String toString() {
		return getName();
	}

	/**
	 * Allows the screenResolution of the card to be changed, so the size of the
	 * card's image is changed appropriately
	 * 
	 * @param screenRes
	 *            0 if high resolution, 1 if low resolution
	 */
	public void setScreenRes(int screenRes) {
		setImage(screenRes);
	}

	/**
	 * compare to compares two card objects, it first checks the suit. Spade
	 * being the highest, diamond second, club third and heart last.
	 * 
	 * @param secondCard
	 *            the second card to compare to the first.
	 * @return if "this" is smaller, -1 is returned, else 1 is returned.
	 */

	@Override
	public int compareTo(Object secondCard) {

		int suit1 = 0, suit2 = 0;

		// Set a suit order for the first
		switch (getSuit()) {
		case SPADE:
			suit1 = 1;
			break;
		case DIAMOND:
			suit1 = 2;
			break;
		case CLUB:
			suit1 = 3;
			break;
		case HEART:
			suit1 = 4;
			break;
		default:
			suit1 = 0;
		}

		// set a suit order for the second
		switch (((Card) secondCard).getSuit()) {
		case SPADE:
			suit2 = 1;
			break;
		case DIAMOND:
			suit2 = 2;
			break;
		case CLUB:
			suit2 = 3;
			break;
		case HEART:
			suit2 = 4;
			break;
		default:
			suit2 = 0;
		}

		if (getSuit() == ((Card) secondCard).getSuit()) {
			if (getNumber() < ((Card) secondCard).getNumber())
				return -1;
			else
				return 1;
		}
		if (suit1 < suit2)
			return -1;
		else
			return 1;

	}

	/**
	 * The compare to 2 method is different from that of the first compare to,
	 * because it sets all suits besides spade as the equal to each other, and
	 * instead of comparing it to "this", it takes in two Card parameters.
	 * 
	 * @param firstCard
	 *            the first card to compare
	 * @param secondCard
	 *            the second card to compare
	 * @return 1 if first card is bigger, -1 if second card is bigger.
	 */
	public static int compareTo2(Object firstCard, Object secondCard) {

		int suit1 = 0, suit2 = 0;

		// Set a suit order for the first
		switch (((Card) firstCard).getSuit()) {
		case SPADE:
			suit1 = 4;
			break;
		case DIAMOND:
			suit1 = 1;
			break;
		case CLUB:
			suit1 = 1;
			break;
		case HEART:
			suit1 = 1;
			break;
		default:
			suit1 = 0;
		}

		// set a suit order for the second
		switch (((Card) secondCard).getSuit()) {
		case SPADE:
			suit2 = 4;
			break;
		case DIAMOND:
			suit2 = 1;
			break;
		case CLUB:
			suit2 = 1;
			break;
		case HEART:
			suit2 = 1;
			break;
		default:
			suit2 = 0;
		}

		if (((Card) firstCard).getSuit() == ((Card) secondCard).getSuit()) {
			if (((Card) firstCard).getNumber() == 1)
				return 1;
			else if (((Card) secondCard).getNumber() == 1)
				return -1;

			if (((Card) firstCard).getNumber() < ((Card) secondCard).getNumber())
				return -1;
			else
				return 1;
		}
		if (suit1 < suit2)
			return -1;
		else
			return 1;

	}

	/*
	 * PRIVATE METHODS
	 */

	/**
	 * setImage looks at the variables screen Resolution, suit, and number.
	 * Based on those three variables, it assigns the correct card image and
	 * size of image to the card
	 * 
	 * @param screenRes
	 *            0 if high resolution, 1 if low resolution
	 */
	private void setImage(int screenRes) {
		String cardDef = "src/PNG-cards-1.3/";

		if (number == 1)
			cardDef += "ace_of_";
		else if (number == 2)
			cardDef += "2_of_";
		else if (number == 3)
			cardDef += "3_of_";
		else if (number == 4)
			cardDef += "4_of_";
		else if (number == 5)
			cardDef += "5_of_";
		else if (number == 6)
			cardDef += "6_of_";
		else if (number == 7)
			cardDef += "7_of_";
		else if (number == 8)
			cardDef += "8_of_";
		else if (number == 9)
			cardDef += "9_of_";
		else if (number == 10)
			cardDef += "10_of_";
		else if (number == 11)
			cardDef += "jack_of_";
		else if (number == 12)
			cardDef += "queen_of_";
		else if (number == 13)
			cardDef += "king_of_";

		if (mySuit == Suit.SPADE)
			cardDef += "spades.png";
		if (mySuit == Suit.CLUB)
			cardDef += "clubs.png";
		if (mySuit == Suit.DIAMOND)
			cardDef += "diamonds.png";
		if (mySuit == Suit.HEART)
			cardDef += "hearts.png";

		ImageIcon i = new ImageIcon(cardDef);
		Image img = i.getImage();
		Image scaledImage;
		if (screenRes == 1)
			scaledImage = img.getScaledInstance(50, 100, Image.SCALE_FAST);
		else
			scaledImage = img.getScaledInstance(100, 200, Image.SCALE_FAST);

		image = new ImageIcon(scaledImage);
	}

}
