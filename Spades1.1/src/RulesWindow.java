import java.awt.Color;
import java.awt.Dimension;
import javax.swing.text.Style;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * This class defines the Rules Window GUI that is displayed upon clicking the
 * "How to Play" button on the Welcome GUI. It displays the rules for our
 * version of Spades as adapter from www.spades-cardgame.com. It displays the
 * rules with a StyledDocument in a JTextPane with a JScrollPane with styling to
 * differentiate between the headers and the rules.
 * 
 * @author Bob Laskowski
 * @author Peter Klein
 * @version 1.3
 *
 */
@SuppressWarnings("serial")
public class RulesWindow extends JFrame {

	/*
	 * INSTANCE VARIABLES
	 */

	/**
	 * Used to style the document
	 */
	private StyleContext sc;

	/**
	 * Used to display the text with proper formatting
	 */
	private DefaultStyledDocument doc;

	/**
	 * Holds the StyledDocument for proper display
	 */
	private JTextPane pane;

	/**
	 * Used to style the different parts of the document
	 */
	private Style defaultStyle, dataStyle, header;

	/**
	 * Used to make the text pane scrollable so all text can be seen
	 */
	private JScrollPane scroll;

	/**
	 * Used to set background color to match other parts of the game
	 */
	private Color green;

	/**
	 * Contains the rules for the game to be displayed
	 */
	private final String[] data = { "Spades\n\n",
			"These are the rules for the card game spades. We adapted them from " + "www.spades-cardgame.com.\n\n",
			"The Teams\n\n",
			"The four players are in fixed partnerships, with partners sitting opposite each other. "
					+ "Deal and play are clockwise. North and South are always partners. East and West are always partners.\n\n",
			"Rank of Cards\n\n",
			"A standard pack of 52 cards is used. The cards, in each suit, rank from highest to "
					+ "lowest: A, K, Q, J, 10, 9, 8, 7, 6, 5, 4, 3, 2.\n\n",
			"The Start of Game\n\n",
			"The game generates a random number 0-3 to choose the player to start the hand. "
					+ "A new random number is generated at the start of each hand. Play moves to the starting player's left.\n\n",
			"The Bidding\n\n",
			"All four players bid a number of tricks they think they will take. In our version of "
					+ "spades, each player, bot or human, automatically bids the number of spades in their hand. "
					+ "The teams add together the bids of the two partners and that is the total number of tricks "
					+ "the team must win in order to get a positive score. No one can pass on bidding. Bid cannot be changed once made.\n\n"
					+ "Example: West bids 3; North bids 1; East bids 4; South bids 4. The objective of North and South is to win at least 5 ticks "
					+ "(4+1), East and West try to win at least 7 (4+3).\n\n",
			"The Play of the Hand\n\n",
			"The player randomly chosen at the start of the game leads with any card except a "
					+ "spade for the first trick. Each player, in turn, clockwise, must follow suit if able; if unable "
					+ "to follow suit, the player may play any card. A trick containing a spade is won by the highest "
					+ "spade played; if no spade is played, the trick is won by the highest card of the suit led. The winner "
					+ "of each trick leads to the next. Spades may not be led until either some player has played a spade "
					+ "(on the lead of another suit, of course), or the leader has nothing but spades left in hand.\n\n",
			"Scoring\n\n",
			"A side that takes at least as many tricks as its bid calls for receives a score equal to 10 times its bid. "
					+ "Additional tricks (overtricks) are worth an extra one point each. Sandbagging rule: Overtricks are colloquially "
					+ "known as bags. A side which (over several deals) accumulates ten or more bags has 100 points deducted from its score. "
					+ "After 10 bags are accumulated, the counting of bags starts over - that is if they reached twenty overtricks "
					+ "they would lose another 100 points and so on. Example: Suppose a team whose score is 337 bids 5 tricks and they have "
					+ "7 bags carried over from the previous rounds. If they win 7 tricks they score 52, taking their score to 389 (and their "
					+ "bags to 9). If they win 8 tricks they score 53, but lose 100 because they now have 10 bags, and their score becomes "
					+ "290 (337 + 53 - 100). If they win 9 tricks they score 54 and lose 100, bringing their score to 291. If a side "
					+ "does not make its bid, they lose 10 points for each trick they bid. The side which reaches 500 points first wins the game. "
					+ "If both sides reach 500 points in a single deal, the side with the higher score wins.\n\n" };

	/*
	 * CONSTRUCTORS
	 */

	/**
	 * This constructor initializes all data for the display and sets the proper
	 * styling.
	 */
	public RulesWindow() {

		// Set the look and feel to Windows. Catch any exceptions thrown if this
		// look and feel is
		// not supported, but do not print error messages
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
		} catch (InstantiationException e) {
			// e.printStackTrace();
		} catch (IllegalAccessException e) {
			// e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// e.printStackTrace();
		}

		// Define all variables to be used
		sc = new StyleContext();
		doc = new DefaultStyledDocument(sc);
		pane = new JTextPane(doc);
		green = new Color(0, 200, 0);
		pane.setBackground(green);
		scroll = new JScrollPane(pane);

		// Define styling to be used
		defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
		dataStyle = sc.addStyle("dataStyle", defaultStyle);
		header = sc.addStyle("header", defaultStyle);
		StyleConstants.setFontFamily(dataStyle, "Sans Serif");
		StyleConstants.setFontSize(dataStyle, 25);
		StyleConstants.setAlignment(dataStyle, StyleConstants.ALIGN_CENTER);
		StyleConstants.setBackground(header, green);
		StyleConstants.setFontFamily(header, "Sans Serif");
		StyleConstants.setFontSize(header, 30);
		StyleConstants.setAlignment(header, StyleConstants.ALIGN_CENTER);
		StyleConstants.setForeground(header, Color.RED);
		StyleConstants.setBackground(dataStyle, green);

		// Make the JTextPane non-editable
		pane.setEditable(false);

		// Add the scroll pane
		add(scroll);

		// Add all the data to the document. Use header style if an even number,
		// use
		// data style if odd number. The data string array is divided up so that
		// the
		// even positions contain the headers and the odd positions contain the
		// rules
		for (int i = 0; i < data.length; i++) {
			if (i % 2 == 0) {
				try {
					doc.insertString(doc.getLength(), data[i], header);
				} catch (BadLocationException e) {
					// e.printStackTrace();
				}
			} else {
				try {
					doc.insertString(doc.getLength(), data[i], dataStyle);
				} catch (BadLocationException e) {
					// e.printStackTrace();
				}
			}
		}

		// Make the document start at the top of the page instead of scrolled
		// all the way to
		// the bottom
		pane.setCaretPosition(0);
	}

	/*
	 * PUBLIC METHODS
	 */

	/**
	 * This method defines the default values for the JFrame and how it will be
	 * displayed. Used to display the rule window. Set to dispose on close so
	 * that it does not exit the entire program when the X is clicked.
	 * 
	 */
	public void display() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension d = new Dimension(1000, 1000);
		setMinimumSize(d);
		setVisible(true);
		setTitle("How to Play");
	}

}
