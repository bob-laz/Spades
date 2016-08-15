import java.util.ArrayList;

/**
 * The bot has a hand and four unique play card methods based on the number of
 * cards in the current middle pile. It uses strategy and intelligence based on
 * memory of previous cards played and current cards in its hand in order to
 * determine the smartest card to play in that situation.
 * 
 * @author Bob Laskowski
 * @author Peter Klein
 * @version 1.3
 */
public class Bot extends Player {

	/*
	 * CONSTRUCTORS
	 */

	/**
	 * Default constructor, calls Player's constructor
	 */
	public Bot() {
		super();
	}

	/**
	 * Calls Player's constructor and passes in a name
	 * 
	 * @param name
	 *            Name for the bot
	 */
	public Bot(String name) {
		super(name);
	}
	
	/*
	 * PUBLIC METHODS
	 */

	/**
	 * Play card takes in the cards already in the middle pile, and plays a
	 * smart, legal card, based on the other cards player.
	 * 
	 * @param list
	 *            the middle pile (cards already played)
	 * @return The played bot card
	 */
	public Card playCard(ArrayList<Card> list) {
		// call the correct play card based
		if (list.size() == 0)
			return playCard0();
		else if (list.size() == 1)
			return playCard1(list.get(0));
		else if (list.size() == 2)
			return playCard2(list.get(0), list.get(1));
		else if (list.size() == 3)
			return playCard3(list.get(0), list.get(1), list.get(2));

		return null;
	}

	/**
	 * Bot plays a card when the middle pile is empty
	 * 
	 * @return Legal card from bot's hand.
	 */
	public Card playCard0() {
		// if you have an ace, play it
		if (getHand().contains(new Card(Suit.CLUB, 1))) {
			getHand().remove(new Card(Suit.CLUB, 1));
			return new Card(Suit.CLUB, 1);
		}
		if (getHand().contains(new Card(Suit.HEART, 1))) {
			getHand().remove(new Card(Suit.HEART, 1));
			return new Card(Suit.HEART, 1);
		}
		if (getHand().contains(new Card(Suit.DIAMOND, 1))) {
			getHand().remove(new Card(Suit.DIAMOND, 1));
			return new Card(Suit.DIAMOND, 1);
		}

		// if an ace has been played and you have the king of that suit, play
		// that.
		if (Driver.memory.contains(new Card(Suit.CLUB, 1)) && getHand().contains(new Card(Suit.CLUB, 13))) {
			getHand().remove(new Card(Suit.CLUB, 13));
			return new Card(Suit.CLUB, 13);
		}
		if (Driver.memory.contains(new Card(Suit.HEART, 1)) && getHand().contains(new Card(Suit.HEART, 13))) {
			getHand().remove(new Card(Suit.HEART, 13));
			return new Card(Suit.HEART, 13);
		}
		if (Driver.memory.contains(new Card(Suit.DIAMOND, 1)) && getHand().contains(new Card(Suit.DIAMOND, 13))) {
			getHand().remove(new Card(Suit.DIAMOND, 13));
			return new Card(Suit.DIAMOND, 13);
		}

		// if an ace and king have been played and you have the queen of that
		// suit, play that.
		if (Driver.memory.contains(new Card(Suit.DIAMOND, 1)) && Driver.memory.contains(new Card(Suit.DIAMOND, 13))
				&& getHand().contains(new Card(Suit.DIAMOND, 12))) {
			getHand().remove(new Card(Suit.DIAMOND, 12));
			return new Card(Suit.DIAMOND, 12);
		}
		if (Driver.memory.contains(new Card(Suit.HEART, 1)) && Driver.memory.contains(new Card(Suit.HEART, 13))
				&& getHand().contains(new Card(Suit.HEART, 12))) {
			getHand().remove(new Card(Suit.HEART, 12));
			return new Card(Suit.HEART, 12);
		}
		if (Driver.memory.contains(new Card(Suit.CLUB, 1)) && Driver.memory.contains(new Card(Suit.CLUB, 13))
				&& getHand().contains(new Card(Suit.CLUB, 12))) {
			getHand().remove(new Card(Suit.CLUB, 12));
			return new Card(Suit.CLUB, 12);
		}

		// play a low, throw away card
		for (int i = 2; i < 13; i++) {
			if (getHand().contains(new Card(Suit.DIAMOND, i))) {
				getHand().remove(new Card(Suit.DIAMOND, i));
				return new Card(Suit.DIAMOND, i);
			}
			if (getHand().contains(new Card(Suit.HEART, i))) {
				getHand().remove(new Card(Suit.HEART, i));
				return new Card(Suit.HEART, i);
			}
			if (getHand().contains(new Card(Suit.CLUB, i))) {
				getHand().remove(new Card(Suit.CLUB, i));
				return new Card(Suit.CLUB, i);
			}
		}

		// last case, play a low spade
		for (int i = 2; i < 13; i++) {
			if (getHand().contains(new Card(Suit.SPADE, i))) {
				getHand().remove(new Card(Suit.SPADE, i));
				return new Card(Suit.SPADE, i);
			}
			if (getHand().contains(new Card(Suit.SPADE, 1))) {
				getHand().remove(new Card(Suit.SPADE, 1));
				return new Card(Suit.SPADE, 1);
			}
		}

		return getHand().remove(0);
	}

	/**
	 * Bot plays a card when there is one in the middle pile.
	 * 
	 * @param card1
	 *            The first card in the middle pile, the card led by the first
	 *            player
	 * @return Legal card from bot's hand.
	 */
	public Card playCard1(Card card1) {
		// check to see if you have an ace of that suit
		if (getHand().contains(new Card(card1.getSuit(), 1))) {
			getHand().remove(new Card(card1.getSuit(), 1));
			return new Card(card1.getSuit(), 1);
		}

		// play the highest card thats bigger than the one played
		for (int i = 13; i >= 1; i--) {
			if (getHand().contains(new Card(card1.getSuit(), i))
					&& Card.compareTo2(new Card(card1.getSuit(), i), card1) == 1) {
				getHand().remove(new Card(card1.getSuit(), i));
				return new Card(card1.getSuit(), i);
			}
		}

		// play a low legal card if you cannot beat the first
		for (int i = 2; i < 13; i++) {
			if (getHand().contains(new Card(card1.getSuit(), i))) {
				getHand().remove(new Card(card1.getSuit(), i));
				return new Card(card1.getSuit(), i);
			}
		}

		// play a low spade
		for (int i = 2; i < 13; i++) {
			if (getHand().contains(new Card(Suit.SPADE, i))) {
				getHand().remove(new Card(Suit.SPADE, i));
				return new Card(Suit.SPADE, i);
			}
		}

		return getHand().remove(0);
	}

	/**
	 * Bot plays a card when there are two in the middle pile
	 * 
	 * @param card1
	 *            The first card in the middle pile, the card led by the first
	 *            player
	 * @param card2
	 *            The second card in the middle pile, the card played second in
	 *            the round
	 * @return Legal card from bot's hand
	 */
	public Card playCard2(Card card1, Card card2) {
		Card highCard = null;

		// if the second card is a spade, but you have cards of the first suit,
		// you cannot win this hand, so play a low card
		if (card1.getSuit() != card2.getSuit() && card2.getSuit().equals(Suit.SPADE)) {
			for (int i = 2; i <= 14; i++) {
				if (getHand().contains(new Card(card1.getSuit(), i))) {
					getHand().remove(new Card(card1.getSuit(), i));
					return new Card(card1.getSuit(), i);
				}
			}

			if (getHand().contains(new Card(card1.getSuit(), 1))) {
				getHand().remove(new Card(card1.getSuit(), 1));
				return new Card(card1.getSuit(), 1);
			}
		}

		// find the highest Card
		if (card1.getSuit() == Suit.SPADE && card2.getSuit() != Suit.SPADE) {
			highCard = new Card(card1.getSuit(), card1.getNumber());
		}
		if (card2.getSuit() == Suit.SPADE && card1.getSuit() != Suit.SPADE) {
			highCard = new Card(card2.getSuit(), card2.getNumber());
		}

		if (card1.getSuit() == card2.getSuit()) {
			if (card1.getNumber() > card2.getNumber())
				highCard = new Card(card1.getSuit(), card1.getNumber());
			else
				highCard = new Card(card2.getSuit(), card2.getNumber());
		}

		if (card1.getSuit() != card2.getSuit() && card2.getSuit() != Suit.SPADE) {
			highCard = new Card(card1.getSuit(), card1.getNumber());
		}

		// check if the suits are the same, and the first card is higher
		// try to beat the higher card.
		if (card1.getSuit() == card2.getSuit()) {
			if (getHand().contains(new Card(highCard.getSuit(), 1))) {
				getHand().remove(new Card(highCard.getSuit(), 1));
				return new Card(highCard.getSuit(), 1);
			}

			if (Driver.memory.contains(new Card(highCard.getSuit(), 1))) {
				if (getHand().contains(new Card(highCard.getSuit(), 13))) {
					getHand().remove(new Card(highCard.getSuit(), 13));
					return new Card(highCard.getSuit(), 13);
				}
				if (Driver.memory.contains(new Card(highCard.getSuit(), 13))) {
					if (getHand().contains(new Card(highCard.getSuit(), 12))) {
						getHand().remove(new Card(highCard.getSuit(), 12));
						return new Card(highCard.getSuit(), 12);
					}
					if (Driver.memory.contains(new Card(highCard.getSuit(), 12))) {
						if (getHand().contains(new Card(highCard.getSuit(), 11))) {
							getHand().remove(new Card(highCard.getSuit(), 11));
							return new Card(highCard.getSuit(), 11);
						}
						if (Driver.memory.contains(new Card(highCard.getSuit(), 11))) {
							if (getHand().contains(new Card(highCard.getSuit(), 10))) {
								getHand().remove(new Card(highCard.getSuit(), 10));
								return new Card(highCard.getSuit(), 10);
							}
						}
					}
				}
			}
		}

		// if the first card is the higher one and its not an ace, try to beat
		// it.
		if ((highCard.getSuit() == card1.getSuit() && highCard.getNumber() == card1.getNumber())
				&& highCard.getNumber() != 1) {
			for (int i = highCard.getNumber(); i <= 14; i++) {
				if (getHand().contains(new Card(card1.getSuit(), i))) {
					getHand().remove(new Card(card1.getSuit(), i));
					return new Card(card1.getSuit(), i);
				}
			}

			if (getHand().contains(new Card(card1.getSuit(), 1))) {
				getHand().remove(new Card(card1.getSuit(), 1));
				return new Card(card1.getSuit(), 1);
			}
		}

		// play a low card of the first suit.
		for (int i = 2; i <= 14; i++) {
			if (getHand().contains(new Card(card1.getSuit(), i))) {
				getHand().remove(new Card(card1.getSuit(), i));
				return new Card(card1.getSuit(), i);
			}
		}

		if (getHand().contains(new Card(card1.getSuit(), 1))) {
			getHand().remove(new Card(card1.getSuit(), 1));
			return new Card(card1.getSuit(), 1);
		}

		// if the second card is a spade thats higher, try to beat it, if not,
		// play a low card
		if (card2.getSuit() == Suit.SPADE && card1.getSuit() != Suit.SPADE) {
			for (int i = card2.getNumber(); i < 14; i++) {
				if (getHand().contains(new Card(Suit.SPADE, i))) {
					getHand().remove(new Card(Suit.SPADE, i));
					return new Card(Suit.SPADE, i);
				}
			}
			if (getHand().contains(new Card(Suit.SPADE, 1))) {
				getHand().remove(new Card(Suit.SPADE, 1));
				return new Card(Suit.SPADE, 1);
			}
		}

		// play a low spade
		for (int i = 2; i <= 14; i++) {
			if (getHand().contains(new Card(Suit.SPADE, i))) {
				getHand().remove(new Card(Suit.SPADE, i));
				return new Card(Suit.SPADE, i);
			}
		}

		if (getHand().contains(new Card(Suit.SPADE, 1))) {
			getHand().remove(new Card(Suit.SPADE, 1));
			return new Card(Suit.SPADE, 1);
		}

		// try to play the lowest club, heart or diamond
		for (int i = 2; i <= 13; i++) {
			if (getHand().contains(new Card(Suit.CLUB, i))) {
				getHand().remove(new Card(Suit.CLUB, i));
				return new Card(Suit.CLUB, i);
			}
			if (getHand().contains(new Card(Suit.HEART, i))) {
				getHand().remove(new Card(Suit.HEART, i));
				return new Card(Suit.HEART, i);
			}
		}
		if (getHand().contains(new Card(Suit.CLUB, 1))) {
			getHand().remove(new Card(Suit.CLUB, 1));
			return new Card(Suit.CLUB, 1);
		}
		if (getHand().contains(new Card(Suit.HEART, 1))) {
			getHand().remove(new Card(Suit.HEART, 1));
			return new Card(Suit.HEART, 1);
		}
		if (getHand().contains(new Card(Suit.DIAMOND, 1))) {
			getHand().remove(new Card(Suit.DIAMOND, 1));
			return new Card(Suit.DIAMOND, 1);
		}

		// this is only used in dire situations, it makes the bot play a legal
		// card of the first cards suit, and if that does not work, play a spade.
		for (int i = 0; i < getHand().size(); i++) {
			if (getHand().getCardAtPosition(i).getSuit() == card1.getSuit()) {
				Card rCard = getHand().getCardAtPosition(i);
				getHand().remove(getHand().getCardAtPosition(i));
				return rCard;
			}
		}
		for (int i = 13; i > 1; i--) {
			if (getHand().contains(new Card(Suit.SPADE, 1))) {
				getHand().remove(new Card(Suit.SPADE, 1));
				return new Card(Suit.SPADE, 1);
			}
			if (getHand().contains(new Card(Suit.SPADE, i))) {
				getHand().remove(new Card(Suit.SPADE, i));
				return new Card(Suit.SPADE, i);
			}
		}
		return getHand().remove(0);
	}

	/**
	 * Bot plays a card when there are three in the middle pile
	 * 
	 * @param card1
	 *            The first card in the middle pile, the card led by the first
	 *            player
	 * @param card2
	 *            The second card in the middle pile, the card played second in
	 *            the round
	 * @param card3
	 *            The third card in the middle pile, the card played third in
	 *            the round
	 * @return Legal card from bot's hand
	 */
	public Card playCard3(Card card1, Card card2, Card card3) {

		Card highCard = null;

		// find the highest card, if you only have spades and one other suit.
		if (Card.compareTo2(card1, card2) == 1)
			highCard = new Card(card1.getSuit(), card1.getNumber());
		else {
			if (card2.getSuit() == Suit.SPADE || card2.getSuit() == card1.getSuit())
				highCard = new Card(card2.getSuit(), card2.getNumber());
		}

		if (Card.compareTo2(highCard, card3) == -1) {
			if (card3.getSuit() == Suit.SPADE || card3.getSuit() == card1.getSuit())
				highCard = new Card(card3.getSuit(), card3.getNumber());
		}

		// if the second card is the highest, that is your partner, so you
		// should play
		// a low card so they will win, and you will save your good cards.
		if (highCard.getSuit() == card2.getSuit() && highCard.getNumber() == card2.getNumber()) {
			for (int i = 2; i <= 13; i++) {
				if (getHand().contains(new Card(card1.getSuit(), i))) {
					getHand().remove(new Card(card1.getSuit(), i));
					return new Card(card1.getSuit(), i);
				}
			}
			if (getHand().contains(new Card(card1.getSuit(), 1))) {
				getHand().remove(new Card(card1.getSuit(), 1));
				return new Card(card1.getSuit(), 1);
			}
			for (int i = 2; i <= 13; i++) {
				if (getHand().contains(new Card(Suit.HEART, i))) {
					getHand().remove(new Card(Suit.HEART, i));
					return new Card(Suit.HEART, i);
				}
				if (getHand().contains(new Card(Suit.DIAMOND, i))) {
					getHand().remove(new Card(Suit.DIAMOND, i));
					return new Card(Suit.DIAMOND, i);
				}
				if (getHand().contains(new Card(Suit.CLUB, i))) {
					getHand().remove(new Card(Suit.CLUB, i));
					return new Card(Suit.CLUB, i);
				}
			}
			if (getHand().contains(new Card(Suit.HEART, 1))) {
				getHand().remove(new Card(Suit.HEART, 1));
				return new Card(Suit.HEART, 1);
			}
			if (getHand().contains(new Card(Suit.CLUB, 1))) {
				getHand().remove(new Card(Suit.CLUB, 1));
				return new Card(Suit.CLUB, 1);
			}
			if (getHand().contains(new Card(Suit.DIAMOND, 1))) {
				getHand().remove(new Card(Suit.DIAMOND, 1));
				return new Card(Suit.DIAMOND, 1);
			}
		}

		// if the high card is the first suit, try to beat it.
		if (highCard.getSuit() == card1.getSuit()) {
			for (int i = highCard.getNumber(); i <= 13; i++) {
				if (getHand().contains(new Card(highCard.getSuit(), i))) {
					getHand().remove(new Card(highCard.getSuit(), i));
					return new Card(highCard.getSuit(), i);
				}
			}
			if (getHand().contains(new Card(highCard.getSuit(), 1))) {
				getHand().remove(new Card(highCard.getSuit(), 1));
				return new Card(highCard.getSuit(), 1);
			}
		}

		// play a legal card of the first cards suit.
		for (int i = 2; i <= 13; i++) {
			if (getHand().contains(new Card(card1.getSuit(), i))) {
				getHand().remove(new Card(card1.getSuit(), i));
				return new Card(card1.getSuit(), i);
			}
		}
		if (getHand().contains(new Card(card1.getSuit(), 1))) {
			getHand().remove(new Card(card1.getSuit(), 1));
			return new Card(card1.getSuit(), 1);
		}

		// play a legal card , higher than the highest
		if (highCard.getNumber() != 1) {
			for (int i = highCard.getNumber() + 1; i < 14; i++) {
				if (getHand().contains(new Card(highCard.getSuit(), i))) {
					getHand().remove(new Card(highCard.getSuit(), i));
					return new Card(highCard.getSuit(), i);
				}
			}
			if (getHand().contains(new Card(highCard.getSuit(), 1))) {
				getHand().remove(new Card(highCard.getSuit(), 1));
				return new Card(highCard.getSuit(), 1);
			}
		}

		// play your lowest spade that beats the high card
		for (int i = 2; i < 14; i++) {
			if (getHand().contains(new Card(Suit.SPADE, i))) {
				getHand().remove(new Card(Suit.SPADE, i));
				return new Card(Suit.SPADE, i);
			}
		}
		if (getHand().contains(new Card(Suit.SPADE, 1))) {
			getHand().remove(new Card(Suit.SPADE, 1));
			return new Card(Suit.SPADE, 1);
		}

		// if all else fails, and you can't beat it, play your lowest card
		for (int i = 2; i < 14; i++) {
			if (getHand().contains(new Card(Suit.HEART, i))) {
				getHand().remove(new Card(Suit.HEART, i));
				return new Card(Suit.HEART, i);
			} else if (getHand().contains(new Card(Suit.CLUB, i))) {
				getHand().remove(new Card(Suit.CLUB, i));
				return new Card(Suit.CLUB, i);
			} else if (getHand().contains(new Card(Suit.DIAMOND, i))) {
				getHand().remove(new Card(Suit.DIAMOND, i));
				return new Card(Suit.DIAMOND, i);
			}
		}
		if (getHand().contains(new Card(Suit.HEART, 1))) {
			getHand().remove(new Card(Suit.HEART, 1));
			return new Card(Suit.HEART, 1);
		}
		if (getHand().contains(new Card(Suit.CLUB, 1))) {
			getHand().remove(new Card(Suit.CLUB, 1));
			return new Card(Suit.CLUB, 1);
		}
		if (getHand().contains(new Card(Suit.DIAMOND, 1))) {
			getHand().remove(new Card(Suit.DIAMOND, 1));
			return new Card(Suit.DIAMOND, 1);
		}

		return getHand().remove(0);

	}

}