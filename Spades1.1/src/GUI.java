
/**
 * This class provides the GUI for the main window of our game. It also controls the flow of the game and 
 * keeps track of the score from hand to hand. In the JavaDoc below, a round is defined to be each of the
 * four players playing one card and determining the winner from those four cards. A hand is defined to be
 * 13 rounds, until each player runs out of cards.
 * 
 * @author Bob Laskowski
 * @author Peter Klein
 * @version 1.3
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	/*
	 * INSTANCE VARIABLES
	 */

	/**
	 * Menu bar across the top of the screen in this window, contains the JMenus
	 * and JMenuItems
	 */
	private JMenuBar menuBar;

	/**
	 * Menu options in the JMenu for changing options and starting new game or
	 * exiting
	 */
	private JMenu file, settings, changeColor;

	/**
	 * Specific options contained in the JMenuBars
	 */
	private JMenuItem exit, newGame, grey, blue, green, red;

	/**
	 * All of the JPanels used to hold components and create the desired layout
	 * of the game
	 */
	private JPanel north, east, south, west, center, cardDisplaySouth, cardDisplayEast, cardDisplayWest,
			cardDisplayNorth, centerNorth, centerEast, centerSouth, centerWest, centerCenter, centerPanel1,
			centerPanel2, centerPanel3, eCardRow1, eCardRow2, eCardRow3, wCardRow1, wCardRow2, wCardRow3;

	/**
	 * These arrays of JLabels, one for each player, hold the name, tricks
	 * taken, tricks bid and face down card images for each player
	 */
	private JLabel[] playerS, playerN, playerE, playerW;

	/**
	 * These JLabels are created on each turn and hold the image of the card
	 * each player plays
	 */
	private JLabel l1, l2, l3, l4;

	/**
	 * The roundWinner JLabel is displayed at the end of each hand to show who
	 * played the highest card. The endOfGameWinner is displayed at the end of
	 * each game to show which team won the hand.
	 */
	private JLabel roundWinner, endOfGameWinner;

	/**
	 * The JTable is displayed at the end of each hand with both teams scores.
	 * The scored are carried over from hand to hand and the JTable is updated
	 * appropriately.
	 */
	private JTable scoring;

	/**
	 * Play button starts the game, nextTurn is displayed after each round and
	 * starts the next hand when clicked, nextHand is displayed at the end of
	 * the hand with the score to go to the next hand and nextPlayerReady is
	 * used when there is more than one human to give people time to switch
	 * spots so the other person doesn't see any cards but their own
	 */
	private JButton play, nextTurnButton, nextHand, nextPlayerReady;

	/**
	 * This instantiates the private class MyModel and is used to create the
	 * JTable with the proper formatting and values.
	 */
	private MyModel model;

	/**
	 * This insets object sets the margins for the card buttons so that the
	 * button is not larger than the card.
	 */
	private Insets margin;

	/**
	 * The GridBagConstraints declared here are used with various GridBagLayouts
	 * to properly format displays. They are declared globally because they are
	 * changed from different places throughout the code.
	 */
	private GridBagConstraints constraints, cons1, cons2, cons3, cons4;

	/**
	 * The font object is set in the constructor and depends on the screen
	 * resolution chosen, larger if higher resolution and smaller if lower
	 * resolution. This font is used to set the font for all text throughout the
	 * game to make it easy to change all the font without changing it for every
	 * object.
	 */
	private Font font;

	/**
	 * These ImageIcons are used to display the face down hand images of cards
	 * within JLabels. They are updated as cards are played and their size
	 * depends on the screen resolution chosen.
	 */
	private ImageIcon horizontalHandIcon, verticalHandIcon;

	/**
	 * These Image objects are used to manipulate the size of the images
	 * displayed as the size depends on the screen resolution chosen.
	 */
	private Image horizontalHandImage, sizedHorizontalHandImage, verticalHandImage, sizedVerticalHandImage;

	/**
	 * The color is used to set the initial background color with the
	 * setBackgroundColor method. Declared in the constructor and green by
	 * default.
	 */
	private Color bgColor;

	/**
	 * The ArrayLists of CardButton objects are used when the respective players
	 * are Humans. They hold the CardButtons for all the card in the players
	 * hands.
	 */
	private ArrayList<CardButton> myHand0, myHand1, myHand2, myHand3;

	/**
	 * The playedCards ArrayList stores cards played for each round in the order
	 * of South player in 0, West player in 1, North player in 2 and East player
	 * in 3. MiddlePile stores the same cards but in the order that they are
	 * played, since this order changes every round. We use two different orders
	 * because for some methods it is easier to work with if the cards are in
	 * the order that they are played, such as for Bot's playCard method, but
	 * for determining the winner of the round, it is important to know who
	 * played which card.
	 */
	private ArrayList<Card> playedCards, middlePile;

	/**
	 * Holds the Card value returned by the determineWinner method called after
	 * each round and used in conjunction with the playedCards array list to
	 * determine which player played the winning card.
	 */
	private Card winner;

	/**
	 * Creates a driver object which helps control some of the game play flow.
	 */
	private Driver driver;

	/**
	 * The ints declared here keep track of various score features and help with
	 * some control of the game play flow.
	 */
	private int southTricksTaken, eastTricksTaken, northTricksTaken, westTricksTaken, southTricksBid, eastTricksBid,
			westTricksBid, northTricksBid, tricksBidNS, tricksBidEW, ptsScoredNS, ptsScoredEW, scoreNS, scoreEW,
			sandbagsNS, sandbagsEW, totalBagsNS, totalBagsEW, turnNumber, nextPlayer, imageNum, numHumans, screenResNum;

	/**
	 * Used by the playableCard method. Is true once spades have been played
	 * during a round so the game knows whether or not to allow a player to lead
	 * with spades.
	 */
	private boolean spadesPlayed;

	/*
	 * CONSTRUCTORS
	 */

	/**
	 * This constructor creates the GUI object for the first round of a game. It
	 * sets the font size and initial image size according the the screenRes int
	 * passed in. It creates a new driver object, call the method to initialize
	 * all the variables that do not change whether it is a new game or the next
	 * hand. It sets the look and feel of the GUI to windows, which will throw
	 * an exception on a Mac but this will be caught by the try/catch block.
	 * This constructor sets the score and number of sand bags for both teams to
	 * 0 and then calls the methods to initialize all the parts of the game
	 * board. Lastly, it sets the background color to the default color of green
	 * with the setBackgroundColor method.
	 * 
	 * @param screenRes
	 *            1 if lower resolution is selected, 0 if higher resolution is
	 *            selected
	 */
	public GUI(int screenRes) {
		// Set the layout of the JFrame to Border Layout
		setLayout(new BorderLayout());

		// Initialize all start of hand variables
		initializeVariables(screenRes);

		// Set the look and feel to Windows, catch exception thrown if this look
		// and feel is not supported
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

		// Initialize JTable
		initializeJTable();

		// Initialize North panel
		initializeNorthPanel();

		// Initialize East panel
		initializeEastPanel();

		// Initialize West panel
		initializeWestPanel();

		// Initialize South panel
		initializeSouthPanel();

		// Initialize Center panel
		initializeCenterPanel();

		// Initialize menu bar
		initializeMenuBar();

		// Set background to green
		setBackgroundColor(bgColor);

		// Set new game score and bags
		scoreNS = 0;
		scoreEW = 0;
		totalBagsNS = 0;
		totalBagsEW = 0;
	}

	/**
	 * This constructor creates the GUI for subsequent rounds of the game. It
	 * performs all the same functions as the first constructor except that it
	 * carries the score and number of sand bags over from hand to hand.
	 * 
	 * @param screenRes
	 *            1 if lower resolution is selected, 0 if higher resolution is
	 *            selected
	 * @param totalScoreNS
	 *            score of North and South team from previous round
	 * @param totalScoreEW
	 *            score of East and West team from previous round
	 * @param bagsNS
	 *            number of sand bags for North and South team from previous
	 *            round
	 * @param bagsEW
	 *            number of sand bags for East and West team from previous round
	 */
	public GUI(int screenRes, int totalScoreNS, int totalScoreEW, int bagsNS, int bagsEW) {
		// Set the layout of the JFrame to Border Layout
		setLayout(new BorderLayout());

		// Initialize all start of hand variables
		initializeVariables(screenRes);

		// Set the look and feel to Windows, catch exception thrown if this look
		// and feel is not supported
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

		// Initialize JTable
		initializeJTable();

		// Initialize North panel
		initializeNorthPanel();

		// Initialize East panel
		initializeEastPanel();

		// Initialize West panel
		initializeWestPanel();

		// Initialize South panel
		initializeSouthPanel();

		// Initialize Center panel
		initializeCenterPanel();

		// Initialize menu bar
		initializeMenuBar();

		// Set background to green
		setBackgroundColor(bgColor);

		// Set the score and sand bags as passed in from previous hand, only
		// different part of the constructor
		scoreNS = totalScoreNS;
		scoreEW = totalScoreEW;
		totalBagsNS = bagsNS;
		totalBagsEW = bagsEW;
	}

	/*
	 * PRIVATE INITIALIZATION METHODS USED IN CONSTRUCTORS
	 */

	/**
	 * Initialize all variables that are the same in both constructors. Made
	 * into a separate method to increase readability of code and make changes
	 * easier.
	 * 
	 * @param screenRes
	 *            1 if lower resolution is selected, 0 if higher resolution is
	 *            selected
	 */
	private void initializeVariables(int screenRes) {
		// Set the screen resolution
		screenResNum = screenRes;

		// Create a new driver object, passing in the screen resolution selected
		driver = new Driver(screenRes);

		// Set font dependent on screen resolution selected
		if (screenResNum == 1)
			font = new Font("Sans Serif", Font.PLAIN, 20);
		else
			font = new Font("Sans Serif", Font.BOLD, 20);

		// Set initial face down card image sizes based on screen resolution
		horizontalHandIcon = new ImageIcon("src/PNG-cards-1.3/CardBack13.png");
		horizontalHandImage = horizontalHandIcon.getImage();
		verticalHandIcon = new ImageIcon("src/PNG-cards-1.3/CardBackSide13.png");
		verticalHandImage = verticalHandIcon.getImage();

		if (screenResNum == 1) {
			sizedHorizontalHandImage = horizontalHandImage.getScaledInstance(350, 100, Image.SCALE_FAST);
			sizedVerticalHandImage = verticalHandImage.getScaledInstance(100, 350, Image.SCALE_FAST);
		} else {
			sizedHorizontalHandImage = horizontalHandImage.getScaledInstance(700, 200, Image.SCALE_FAST);
			sizedVerticalHandImage = verticalHandImage.getScaledInstance(200, 700, Image.SCALE_FAST);
		}

		// Initialized to 13 because all players start with 13 cards,
		// decremented each round
		imageNum = 13;

		// All players start having taken 0 tricks
		southTricksTaken = 0;
		eastTricksTaken = 0;
		northTricksTaken = 0;
		westTricksTaken = 0;

		// Initialize bids to 0, set to number of spades in hand after play
		// button clicked
		southTricksBid = 0;
		westTricksBid = 0;
		northTricksBid = 0;
		eastTricksBid = 0;

		// Keeps track of what turn the game is on, 0-3, resets each round
		turnNumber = 0;

		// Used to check which player starts the next round, updated on nextTurn
		// click
		nextPlayer = 0;

		// Variables used to keep track of score and bags for the teams for the
		// round
		tricksBidNS = 0;
		tricksBidEW = 0;
		ptsScoredNS = 0;
		ptsScoredEW = 0;
		sandbagsNS = 0;
		sandbagsEW = 0;

		// Number of human players in the game, used to determine if
		// nextPlayerReady button should be displayed
		numHumans = 0;

		// Initialized the ArrayList of CardButtons for human players hands,
		// increments numHumans
		if (driver.players[0] instanceof Human) {
			myHand0 = new ArrayList<CardButton>();
			numHumans++;
		}
		if (driver.players[1] instanceof Human) {
			myHand1 = new ArrayList<CardButton>();
			numHumans++;
		}
		if (driver.players[2] instanceof Human) {
			myHand2 = new ArrayList<CardButton>();
			numHumans++;
		}
		if (driver.players[3] instanceof Human) {
			myHand3 = new ArrayList<CardButton>();
			numHumans++;
		}

		// Holds the card of the winner of the round
		winner = null;

		// Default background color, green
		bgColor = new Color(0, 200, 0);

		// GridBagConstraints object used to set layout
		constraints = new GridBagConstraints();

		// Sets the margins for the CardButton objects so the button is the same
		// size as the card
		margin = new Insets(0, 0, 0, 0);

		// Initializes playedCards arrayList of cards, cards added in same order
		// each round
		playedCards = new ArrayList<Card>(4);
		playedCards.add(null);
		playedCards.add(null);
		playedCards.add(null);
		playedCards.add(null);

		// Initializes middlePile arrayList of cards, cards added in the order
		// played
		middlePile = new ArrayList<Card>();

		// True when spades are "broken" and players can then lead with spades
		spadesPlayed = false;
	}

	/**
	 * Initializes the format for the JTable used to display the score at the
	 * end of each hand
	 */
	private void initializeJTable() {
		model = new MyModel();
		scoring = new JTable(model);
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		DefaultTableCellRenderer header = new DefaultTableCellRenderer();
		header.setHorizontalAlignment(SwingConstants.CENTER);
		center.setHorizontalAlignment(SwingConstants.CENTER);
		if (screenResNum == 1) {
			center.setFont(new Font("Sans Serif", Font.PLAIN, 15));
			scoring.getTableHeader().setFont(new Font("Sans Serif", Font.BOLD, 15));
			scoring.getColumnModel().getColumn(0).setMinWidth(110);
			scoring.getColumnModel().getColumn(1).setMinWidth(120);
			scoring.getColumnModel().getColumn(2).setMinWidth(110);
		} else {
			center.setFont(font);
			scoring.getTableHeader().setFont(font);
			scoring.getColumnModel().getColumn(0).setPreferredWidth(170);
			scoring.getColumnModel().getColumn(1).setPreferredWidth(170);
			scoring.getColumnModel().getColumn(2).setPreferredWidth(170);
		}
		for (int x = 0; x < scoring.getColumnCount(); x++) {
			scoring.getColumnModel().getColumn(x).setCellRenderer(center);
		}
		scoring.setRowHeight(30);
		scoring.getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * Initializes the North panel and the playerN array of JLabels used to
	 * display all information for North player, regardless of whether human or
	 * bot
	 */
	private void initializeNorthPanel() {
		// North panel
		north = new JPanel();
		add(north, BorderLayout.NORTH);
		north.setLayout(new GridBagLayout());
		cons1 = new GridBagConstraints();

		playerN = new JLabel[4];
		cardDisplayNorth = new JPanel();
		cons1.fill = GridBagConstraints.HORIZONTAL;
		cons1.gridx = 0;
		cons1.gridy = 0;
		cons1.gridheight = 4;
		north.add(cardDisplayNorth, cons1);

		// Position 0 holds the face down card image
		playerN[0] = new JLabel(new ImageIcon(sizedHorizontalHandImage));
		cardDisplayNorth.add(playerN[0]);

		// Position 1 holds the player's name or "Bot"
		playerN[1] = new JLabel(driver.playerAry[2]);
		cons1.fill = GridBagConstraints.HORIZONTAL;
		cons1.gridx = 1;
		cons1.gridy = 0;
		cons1.gridheight = 1;
		north.add(playerN[1], cons1);

		// Position 2 holds the number of tricks bid, based on number of spades
		// in hand, displayed when
		// play button clicked
		playerN[2] = new JLabel("Tricks Bid: ");
		cons1.fill = GridBagConstraints.HORIZONTAL;
		cons1.gridx = 1;
		cons1.gridy = 1;
		north.add(playerN[2], cons1);

		// Position 3 holds the number of tricks taken, updated when the player
		// wins tricks
		playerN[3] = new JLabel("Tricks Taken: ");
		cons1.fill = GridBagConstraints.HORIZONTAL;
		cons1.gridx = 1;
		cons1.gridy = 2;
		north.add(playerN[3], cons1);

		// Set font for all JLabels in array
		for (int i = 0; i < playerN.length; i++) {
			playerN[i].setFont(font);
		}
	}

	/**
	 * Initializes the East panel and the playerE array of JLabels used to
	 * display all information for East player, regardless of whether human or
	 * bot
	 */
	private void initializeEastPanel() {
		// East Panel
		east = new JPanel();
		add(east, BorderLayout.EAST);
		east.setLayout(new GridBagLayout());
		cons2 = new GridBagConstraints();

		playerE = new JLabel[4];
		cardDisplayEast = new JPanel();
		cons2.fill = GridBagConstraints.HORIZONTAL;
		cons2.gridx = 0;
		cons2.gridy = 0;
		east.add(cardDisplayEast, cons2);

		// Position 0 holds the face down card image
		playerE[0] = new JLabel(new ImageIcon(sizedVerticalHandImage));
		cardDisplayEast.add(playerE[0]);

		// Position 1 holds the player's name or "Bot"
		playerE[1] = new JLabel(driver.playerAry[3]);
		cons2.fill = GridBagConstraints.HORIZONTAL;
		cons2.gridx = 0;
		cons2.gridy = 1;
		east.add(playerE[1], cons2);

		// Position 2 holds the number of tricks bid, based on number of spades
		// in hand, displayed when
		// play button clicked
		playerE[2] = new JLabel("Tricks Bid: ");
		cons2.fill = GridBagConstraints.HORIZONTAL;
		cons2.gridx = 0;
		cons2.gridy = 2;
		east.add(playerE[2], cons2);

		// Position 3 holds the number of tricks taken, updated when the player
		// wins tricks
		playerE[3] = new JLabel("Tricks Taken: ");
		cons2.fill = GridBagConstraints.HORIZONTAL;
		cons2.gridx = 0;
		cons2.gridy = 3;
		east.add(playerE[3], cons2);

		// Used to properly display cards if player is human since card must be
		// displayed in three rows
		// for east and west players due to space constraints
		eCardRow1 = new JPanel();
		eCardRow2 = new JPanel();
		eCardRow3 = new JPanel();

		// Set font for all JLabels in array
		for (int i = 0; i < playerE.length; i++) {
			playerE[i].setFont(font);
		}
	}

	/**
	 * Initializes the West panel and the playerW array of JLabels used to
	 * display all information for West player, regardless of whether human or
	 * bot
	 */
	private void initializeWestPanel() {
		// West Panel
		west = new JPanel();
		add(west, BorderLayout.WEST);
		west.setLayout(new GridBagLayout());
		cons3 = new GridBagConstraints();

		playerW = new JLabel[4];
		cardDisplayWest = new JPanel();
		cons3.fill = GridBagConstraints.HORIZONTAL;
		cons3.gridx = 0;
		cons3.gridy = 3;
		west.add(cardDisplayWest, cons3);

		// Position 0 holds the face down card image
		playerW[0] = new JLabel(new ImageIcon(sizedVerticalHandImage));
		cardDisplayWest.add(playerW[0]);

		// Position 1 holds the player's name or "Bot"
		playerW[1] = new JLabel(driver.playerAry[1]);
		cons3.fill = GridBagConstraints.HORIZONTAL;
		cons3.gridx = 0;
		cons3.gridy = 0;
		west.add(playerW[1], cons3);

		// Position 2 holds the number of tricks bid, based on number of spades
		// in hand, displayed when
		// play button clicked
		playerW[2] = new JLabel("Tricks Bid: ");
		cons3.fill = GridBagConstraints.HORIZONTAL;
		cons3.gridx = 0;
		cons3.gridy = 1;
		west.add(playerW[2], cons3);

		// Position 3 holds the number of tricks taken, updated when the player
		// wins tricks
		playerW[3] = new JLabel("Tricks Taken: ");
		cons3.fill = GridBagConstraints.HORIZONTAL;
		cons3.gridx = 0;
		cons3.gridy = 2;
		west.add(playerW[3], cons3);

		// Used to properly display cards if player is human since card must be
		// displayed in three rows for east and west players due to space
		// constraints
		wCardRow1 = new JPanel();
		wCardRow2 = new JPanel();
		wCardRow3 = new JPanel();

		// Set font for all JLabels in array
		for (int i = 0; i < playerW.length; i++) {
			playerW[i].setFont(font);
		}
	}

	/**
	 * Initializes the South panel and the playerS array of JLabels used to
	 * display all information for South player, regardless of whether human or
	 * bot
	 */
	private void initializeSouthPanel() {
		// South panel
		south = new JPanel();
		add(south, BorderLayout.SOUTH);
		south.setLayout(new GridBagLayout());
		cons4 = new GridBagConstraints();

		playerS = new JLabel[4];
		cardDisplaySouth = new JPanel();
		cons4.fill = GridBagConstraints.HORIZONTAL;
		cons4.gridx = 1;
		cons4.gridy = 0;
		cons4.gridheight = 4;
		south.add(cardDisplaySouth, cons4);

		// Position 0 holds the face down card image
		playerS[0] = new JLabel(new ImageIcon(sizedHorizontalHandImage));
		cardDisplaySouth.add(playerS[0]);

		// Position 1 holds the player's name or "Bot"
		playerS[1] = new JLabel(driver.playerAry[0]);
		cons4.fill = GridBagConstraints.HORIZONTAL;
		cons4.gridx = 0;
		cons4.gridy = 0;
		cons4.gridheight = 1;
		south.add(playerS[1], cons4);

		// Position 2 holds the number of tricks bid, based on number of spades
		// in hand, displayed when play button clicked
		playerS[2] = new JLabel("Tricks Bid: ");
		cons4.fill = GridBagConstraints.HORIZONTAL;
		cons4.gridx = 0;
		cons4.gridy = 1;
		south.add(playerS[2], cons4);

		// Position 3 holds the number of tricks taken, updated when the player
		// wins tricks
		playerS[3] = new JLabel("Tricks Taken: ");
		cons4.fill = GridBagConstraints.HORIZONTAL;
		cons4.gridx = 0;
		cons4.gridy = 2;
		south.add(playerS[3], cons4);

		// Set font for all JLabels in array
		for (int i = 0; i < playerS.length; i++) {
			playerS[i].setFont(font);
		}
	}

	/**
	 * Initializes the Center panel used to display played cards, round winner,
	 * next turn button, score table, hand winner and next hand button
	 */
	private void initializeCenterPanel() {
		// JPanels used to construct layout
		center = new JPanel();
		centerNorth = new JPanel();
		centerEast = new JPanel();
		centerSouth = new JPanel();
		centerWest = new JPanel();
		centerCenter = new JPanel();
		centerPanel1 = new JPanel();
		centerPanel2 = new JPanel();
		centerPanel3 = new JPanel();

		// Set the layout for all JPanels
		center.setLayout(new BorderLayout());
		centerNorth.setLayout(new BorderLayout());
		centerEast.setLayout(new BorderLayout());
		centerSouth.setLayout(new BorderLayout());
		centerWest.setLayout(new BorderLayout());
		centerCenter.setLayout(new GridLayout(1, 3));
		centerPanel1.setLayout(new GridBagLayout());
		centerPanel2.setLayout(new GridBagLayout());
		centerPanel3.setLayout(new GridBagLayout());

		// Add JPanels to display
		add(center, BorderLayout.CENTER);
		center.add(centerNorth, BorderLayout.NORTH);
		center.add(centerEast, BorderLayout.EAST);
		center.add(centerSouth, BorderLayout.SOUTH);
		center.add(centerWest, BorderLayout.WEST);
		center.add(centerCenter, BorderLayout.CENTER);
		centerCenter.add(centerPanel1);
		centerCenter.add(centerPanel2);
		centerCenter.add(centerPanel3);

		// PLAY BUTTON
		play = new JButton("Play!");
		// Set font
		play.setFont(font);
		// Set size of play button based on screen resolution
		if (screenResNum == 1)
			play.setPreferredSize(new Dimension(100, 50));
		else
			play.setPreferredSize(new Dimension(200, 100));
		// Add Play action listener
		play.addActionListener(new Play());
		// Add button to display
		centerPanel1.add(play);

		// ROUND WINNER LABEL
		roundWinner = new JLabel("");
		// Set font size based on screen resolution
		if (screenResNum == 1)
			roundWinner.setFont(new Font("Sans Serif", Font.BOLD, 30));
		else
			roundWinner.setFont(new Font("Sans Serif", Font.BOLD, 40));
		// Add to display
		centerPanel2.add(roundWinner);

		// NEXT TURN BUTTON
		nextTurnButton = new JButton("Next Turn");
		// Set font
		nextTurnButton.setFont(font);
		// Set size based on screen resolution
		if (screenResNum == 1)
			nextTurnButton.setPreferredSize(new Dimension(130, 50));
		else
			nextTurnButton.setPreferredSize(new Dimension(200, 100));
		// Add NextTurn action listener
		nextTurnButton.addActionListener(new NextTurn());
		// nextTurnButton is not visible at start of game
		nextTurnButton.setVisible(false);
		// Add to display
		centerPanel3.add(nextTurnButton);

		// NEXT PLAYER READY BUTTON
		nextPlayerReady = new JButton("Next Player");
		// Set font
		nextPlayerReady.setFont(font);
		// Set size based on screen resolution
		if (screenResNum == 1)
			nextPlayerReady.setPreferredSize(new Dimension(130, 50));
		else
			nextPlayerReady.setPreferredSize(new Dimension(200, 100));
		// Add action listener
		nextPlayerReady.addActionListener(new NextHumanPlayer());
		// nextPlayerReady is not visible at start of game
		nextPlayerReady.setVisible(false);
		// Add button to display
		centerPanel2.add(nextPlayerReady);
	}

	/**
	 * Initialized the menu bar across the top of the screen with the new game,
	 * exit and change background color options
	 */
	private void initializeMenuBar() {
		// Add menu bar to display window
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// File menu
		file = new JMenu("File");
		file.setFont(font);
		file.setFont(font);
		menuBar.add(file);
		newGame = new JMenuItem("New Game");
		newGame.setFont(font);
		file.add(newGame);
		exit = new JMenuItem("Exit");
		exit.setFont(font);
		file.add(exit);

		// Settings menu
		settings = new JMenu("Settings");
		settings.setFont(font);
		menuBar.add(settings);
		changeColor = new JMenu("Change Background Color...");
		changeColor.setFont(font);
		settings.add(changeColor);
		grey = new JMenuItem("Grey");
		grey.setFont(font);
		blue = new JMenuItem("Blue");
		blue.setFont(font);
		green = new JMenuItem("Green");
		green.setFont(font);
		red = new JMenuItem("Red");
		red.setFont(font);
		changeColor.add(blue);
		changeColor.add(grey);
		changeColor.add(green);
		changeColor.add(red);

		// Add action listeners to all menu options
		exit.addActionListener(new ExitEvent());
		newGame.addActionListener(new NewGameEvent());
		blue.addActionListener(new BlueEvent());
		grey.addActionListener(new GreyEvent());
		green.addActionListener(new GreenEvent());
		red.addActionListener(new RedEvent());
	}

	/*
	 * PRIVATE METHODS
	 */

	/**
	 * Used for human players to determine which buttons to enable based on the
	 * rules of the game. This is done so that humans cannot play illegal cards.
	 * It is called when it is a human's turn to play and either enables or
	 * disables buttons based on whether the cards can be played or not.
	 * 
	 * @param myHand
	 *            The players hand to be evaluated
	 * @param pile
	 *            The middlePile arrayList that contains the cards already
	 *            played in the order they were played
	 */
	private void playableCards(ArrayList<CardButton> myHand, ArrayList<Card> pile) {
		// If you are the first person to play that turn, enable all cards if
		// spadesPlayed is true
		// or all cards except spades if spadesPlayed is false
		if (pile == null || pile.size() == 0) {
			for (int i = 0; i < myHand.size(); i++) {
				if (myHand.get(i).getCard().getSuit() == Suit.SPADE && spadesPlayed == false)
					myHand.get(i).setEnabled(false);
				else
					myHand.get(i).setEnabled(true);
			}
			return;
		}

		// The first card played, determines the lead suit
		Card card1 = pile.get(0);
		// Counts the number of cards disabled, if all cards in hand disabled,
		// enable all cards
		int count = 0;

		// you can only play cards from the first suit
		for (int i = 0; i < myHand.size(); i++) {
			if (myHand.get(i).getCard().getSuit() != card1.getSuit()) {
				myHand.get(i).setEnabled(false);
				count++;
			}
		}

		// if you don't have that first card suit, you can play anything
		if (count == myHand.size()) {
			for (int i = 0; i < myHand.size(); i++) {
				myHand.get(i).setEnabled(true);
			}
		}

	}

	/**
	 * Calculates the number of points a team scored for a round based on their
	 * tricks bid and tricks taken. Called once for North and South and once for
	 * East and West
	 * 
	 * @param p1TricksBid
	 *            First player on the team's tricks bid
	 * @param p2TricksBid
	 *            Second player on the team's tricks bid
	 * @param p1TricksTaken
	 *            First player on the team's tricks taken
	 * @param p2TricksTaken
	 *            Second player on the team's tricks taken
	 * @return The points for that team for the round
	 */
	private int pointsScored(int p1TricksBid, int p2TricksBid, int p1TricksTaken, int p2TricksTaken) {
		int score = 0;
		if ((p1TricksTaken + p2TricksTaken) < (p1TricksBid + p2TricksBid))
			score = -(10 * (p1TricksBid + p2TricksBid));
		else if ((p1TricksTaken + p2TricksTaken) == (p1TricksBid + p2TricksBid))
			score = 10 * (p1TricksBid + p2TricksBid);
		else {
			score = (10 * (p1TricksBid + p2TricksBid))
					+ ((p1TricksTaken + p2TricksTaken) - (p1TricksBid + p2TricksBid));
		}

		return score;
	}

	/**
	 * Calculated the number of sand bags for a team for a round based on their
	 * tricks bid and tricks taken. Called once for North and South and once for
	 * East and West
	 * 
	 * @param p1TricksBid
	 *            First player on the team's tricks bid
	 * @param p2TricksBid
	 *            Second player on the team's tricks bid
	 * @param p1TricksTaken
	 *            First player on the team's tricks taken
	 * @param p2TricksTaken
	 *            Second player on the team's tricks taken
	 * @return The total sand bags for the team for that round, returns 0 if
	 *         none
	 */
	private int sandBags(int p1TricksBid, int p2TricksBid, int p1TricksTaken, int p2TricksTaken) {
		int bags = 0;
		// If more tricks are taken than bid, number of over tricks = number of
		// sand bags
		if ((p1TricksTaken + p2TricksTaken) > (p1TricksBid + p2TricksBid))
			bags = (p1TricksTaken + p2TricksTaken) - (p1TricksBid + p2TricksBid);
		return bags;
	}

	/**
	 * Changes the background color for all JPanels on the display
	 * 
	 * @param bgColor
	 *            the new Color object for the background
	 */
	private void setBackgroundColor(Color bgColor) {
		north.setBackground(bgColor);
		south.setBackground(bgColor);
		west.setBackground(bgColor);
		east.setBackground(bgColor);
		eCardRow1.setBackground(bgColor);
		eCardRow2.setBackground(bgColor);
		eCardRow3.setBackground(bgColor);
		wCardRow1.setBackground(bgColor);
		wCardRow2.setBackground(bgColor);
		wCardRow3.setBackground(bgColor);
		cardDisplaySouth.setBackground(bgColor);
		cardDisplayEast.setBackground(bgColor);
		cardDisplayWest.setBackground(bgColor);
		cardDisplayNorth.setBackground(bgColor);
		centerNorth.setBackground(bgColor);
		centerEast.setBackground(bgColor);
		centerSouth.setBackground(bgColor);
		centerWest.setBackground(bgColor);
		centerCenter.setBackground(bgColor);
		centerPanel1.setBackground(bgColor);
		centerPanel2.setBackground(bgColor);
		centerPanel3.setBackground(bgColor);
	}

	/**
	 * Used to update the images for the face down cards based on the number of
	 * cards left in the player's hand. Adjusts the size based on screen
	 * resolution chosen.
	 * 
	 */
	private void updateCardImages() {
		if (screenResNum == 1) {
			horizontalHandIcon = new ImageIcon("src/PNG-cards-1.3/CardBack" + --imageNum + ".png");
			horizontalHandImage = horizontalHandIcon.getImage();
			sizedHorizontalHandImage = horizontalHandImage.getScaledInstance((50 + imageNum * 25), 100,
					Image.SCALE_FAST);

			verticalHandIcon = new ImageIcon("src/PNG-cards-1.3/CardBackSide" + imageNum + ".png");
			verticalHandImage = verticalHandIcon.getImage();
			sizedVerticalHandImage = verticalHandImage.getScaledInstance(100, (50 + imageNum * 25), Image.SCALE_FAST);
		} else {
			horizontalHandIcon = new ImageIcon("src/PNG-cards-1.3/CardBack" + --imageNum + ".png");
			horizontalHandImage = horizontalHandIcon.getImage();
			sizedHorizontalHandImage = horizontalHandImage.getScaledInstance((100 + imageNum * 50), 200,
					Image.SCALE_FAST);

			verticalHandIcon = new ImageIcon("src/PNG-cards-1.3/CardBackSide" + imageNum + ".png");
			verticalHandImage = verticalHandIcon.getImage();
			sizedVerticalHandImage = verticalHandImage.getScaledInstance(200, (100 + imageNum * 50), Image.SCALE_FAST);
		}

		playerE[0].setIcon(new ImageIcon(sizedVerticalHandImage));
		playerS[0].setIcon(new ImageIcon(sizedHorizontalHandImage));
		playerW[0].setIcon(new ImageIcon(sizedVerticalHandImage));
		playerN[0].setIcon(new ImageIcon(sizedHorizontalHandImage));
	}

	/*
	 * ACTION LISTENERS
	 */

	/**
	 * Defines what happens when a Play action is created. Used to start the
	 * game.
	 */
	private class Play implements ActionListener {

		/**
		 * Tells the driver to deal the hands for the game from the deck. Hides
		 * the play button. Initialize all human players' hands but set visible
		 * to false. Generate a random number to determine which player will
		 * start the game. If human, display their hand and wait for click, else
		 * if bot create a CardButton with PlayCard action listener and click it
		 */
		public void actionPerformed(ActionEvent e) {
			// Deals hands of 13 cards to each player
			driver.dealHands();
			// Hide the play button
			play.setVisible(false);
			// Action listener to be added to all human players' CardButtons
			PlayCard p = new PlayCard();

			// Sets the tricks each player bids to the number of spades in their
			// hand
			southTricksBid = driver.players[0].getHand().getNumSpades();
			westTricksBid = driver.players[1].getHand().getNumSpades();
			northTricksBid = driver.players[2].getHand().getNumSpades();
			eastTricksBid = driver.players[3].getHand().getNumSpades();
			playerS[2].setText("Tricks Bid: " + southTricksBid);
			playerW[2].setText("Tricks Bid: " + westTricksBid);
			playerN[2].setText("Tricks Bid: " + northTricksBid);
			playerE[2].setText("Tricks Bid: " + eastTricksBid);

			// Calculates each team's number of tricks bid for use in scoring
			tricksBidNS = northTricksBid + southTricksBid;
			tricksBidEW = eastTricksBid + westTricksBid;

			// Check to see if each player is Human, if they are initializes how
			// their hand will be
			// displayed but does not display yet (setVisible = false). East and
			// West players' hands will
			// be displayed in three horizontal rows while North and South
			// players' hands will be
			// displayed horizontally in one row. Also adds PlayCard action
			// listener to each button
			if (driver.players[1] instanceof Human) {
				cardDisplayWest.setLayout(new GridBagLayout());
				GridBagConstraints cons = new GridBagConstraints();
				cons.gridx = 0;
				cons.gridy = 0;
				cardDisplayWest.add(wCardRow1, cons);
				cons.gridx = 0;
				cons.gridy = 1;
				cardDisplayWest.add(wCardRow2, cons);
				cons.gridx = 0;
				cons.gridy = 2;
				cardDisplayWest.add(wCardRow3, cons);
				for (int i = 0; i < 13; i++) {
					CardButton button = new CardButton(driver.players[1].getHand().getCardAtPosition(i).getImage(),
							driver.players[1].getHand().getCardAtPosition(i));
					button.setMargin(margin);
					button.addActionListener(p);
					myHand1.add(i, button);
					myHand1.get(i).setVisible(false);
					if (i < 4) {
						wCardRow1.add(myHand1.get(i));
					} else if (i < 8) {
						wCardRow2.add(myHand1.get(i));
					} else
						wCardRow3.add(myHand1.get(i));
				}
			}
			if (driver.players[2] instanceof Human) {
				for (int i = 0; i < 13; i++) {
					CardButton button = new CardButton(driver.players[2].getHand().getCardAtPosition(i).getImage(),
							driver.players[2].getHand().getCardAtPosition(i));
					button.setMargin(margin);
					button.addActionListener(p);
					myHand2.add(i, button);
					cardDisplayNorth.add(myHand2.get(i));
					myHand2.get(i).setVisible(false);
				}
			}
			if (driver.players[3] instanceof Human) {
				cardDisplayEast.setLayout(new GridBagLayout());
				GridBagConstraints cons = new GridBagConstraints();
				cons.gridx = 0;
				cons.gridy = 0;
				cardDisplayEast.add(eCardRow1, cons);
				cons.gridx = 0;
				cons.gridy = 1;
				cardDisplayEast.add(eCardRow2, cons);
				cons.gridx = 0;
				cons.gridy = 2;
				cardDisplayEast.add(eCardRow3, cons);
				for (int i = 0; i < 13; i++) {
					CardButton button = new CardButton(driver.players[3].getHand().getCardAtPosition(i).getImage(),
							driver.players[3].getHand().getCardAtPosition(i));
					button.setMargin(margin);
					button.addActionListener(p);
					myHand3.add(i, button);
					myHand3.get(i).setVisible(false);
					if (i < 4) {
						eCardRow1.add(myHand3.get(i));
					} else if (i < 8) {
						eCardRow2.add(myHand3.get(i));
					} else
						eCardRow3.add(myHand3.get(i));
				}
			}
			if (driver.players[0] instanceof Human) {
				for (int i = 0; i < 13; i++) {
					CardButton button = new CardButton(driver.players[0].getHand().getCardAtPosition(i).getImage(),
							driver.players[0].getHand().getCardAtPosition(i));
					button.setMargin(margin);
					button.addActionListener(p);
					myHand0.add(i, button);
					cardDisplaySouth.add(myHand0.get(i));
					myHand0.get(i).setVisible(false);
				}
			}

			// Generate a random number 0-3 to determine who will start the
			// hand. If that player is human,
			// display their hand, else if bot, create a CardButton with
			// PlayCard action listener and
			// click
			int randomNum = (int) (Math.random() * 4);
			if (randomNum == 0) {
				driver.players[0].setTurnToPlay(true);
				if (driver.players[0] instanceof Human) {
					playerS[0].setVisible(false);
					for (int i = 0; i < 13; i++) {
						myHand0.get(i).setVisible(true);
					}
					playableCards(myHand0, middlePile);
				} else {
					CardButton b = new CardButton();
					b.addActionListener(p);
					b.doClick();
				}
			} else if (randomNum == 1) {
				driver.players[1].setTurnToPlay(true);
				if (driver.players[1] instanceof Human) {
					playerW[0].setVisible(false);
					for (int i = 0; i < 13; i++) {
						myHand1.get(i).setVisible(true);
					}
					playableCards(myHand1, middlePile);
				} else {
					CardButton b = new CardButton();
					b.addActionListener(p);
					b.doClick();
				}
			} else if (randomNum == 2) {
				driver.players[2].setTurnToPlay(true);
				if (driver.players[2] instanceof Human) {
					playerN[0].setVisible(false);
					for (int i = 0; i < 13; i++) {
						myHand2.get(i).setVisible(true);
					}
					playableCards(myHand2, middlePile);
				} else {
					CardButton b = new CardButton();
					b.addActionListener(p);
					b.doClick();
				}
			} else if (randomNum == 3) {
				driver.players[3].setTurnToPlay(true);
				if (driver.players[3] instanceof Human) {
					playerE[0].setVisible(false);
					for (int i = 0; i < 13; i++) {
						myHand3.get(i).setVisible(true);
					}
					playableCards(myHand3, middlePile);
				} else {
					CardButton b = new CardButton();
					b.addActionListener(p);
					b.doClick();
				}
			}
		}
	}

	/**
	 * Defines what happens when a PlayCard action is created. Used to control a
	 * single round and determine the winner of the round.
	 * 
	 */
	private class PlayCard implements ActionListener {

		/**
		 * When a PlayCard click is received, gets the source of the click and
		 * takes the appropriate action based on which player's turn it was.
		 * Checks to see if that player was a bot or human and responds
		 * appropriately. Displays the card the player chose in the center
		 * panel. Checks to see if next player is a human or bot, plays a card
		 * for the bot or displays hand and waits for next click if human. After
		 * the round, determines the winner of the round, displays the winner
		 * and makes the nextTurn button visible and waits for click.
		 */
		public void actionPerformed(ActionEvent e) {
			// Set b to the CardButton that was clicked to trigger the event
			CardButton b = (CardButton) e.getSource();

			// Keep going through the loop while turnNumber is 0-3
			while (turnNumber < 4) {
				// Every player has a boolean turnToPlay property that is true
				// when it is their turn to
				// play. Players[0] is always the South player.
				if (driver.players[0].getTurnToPlay()) {
					// If players[0] is human, respond appropriately to card
					// click
					if (driver.players[0] instanceof Human) {
						Card c = b.getCard();
						if (c.getSuit() == Suit.SPADE)
							spadesPlayed = true;
						playedCards.set(0, c);
						middlePile.add(c);
						l1 = new JLabel(b.getIcon());
						cardDisplaySouth.remove(b);
						myHand0.remove(b);
						for (int i = 0; i < myHand0.size(); i++) {
							myHand0.get(i).setVisible(false);
						}
						centerSouth.add(l1, BorderLayout.CENTER);
						driver.players[0].getHand().remove(c);
						playerS[0].setVisible(true);
						// If players[0] is a bot, call their playCard method
						// and display card
					} else if (driver.players[0] instanceof Bot) {
						System.out.println("South player: " + driver.players[0].getHand().toString());
						Card c = ((Bot) driver.players[0]).playCard(middlePile);
						c.setScreenRes(screenResNum);
						if (c.getSuit() == Suit.SPADE)
							spadesPlayed = true;
						playedCards.set(0, c);
						middlePile.add(c);
						l1 = new JLabel(c.getImage());
						centerSouth.add(l1, BorderLayout.CENTER);
					}

					// Get ready for next player's turn
					turnNumber++;
					driver.players[0].setTurnToPlay(false);
					driver.players[1].setTurnToPlay(true);
					// If the next player is a human, check to see if there is
					// more than one human player
					// If more than one human, display nextPlayerReady button,
					// if only one human, display
					// Their hand
					if (driver.players[1] instanceof Human && turnNumber < 4) {
						if (numHumans == 1) {
							playerW[0].setVisible(false);
							for (int i = 0; i < myHand1.size(); i++) {
								myHand1.get(i).setVisible(true);
								myHand1.get(i).setEnabled(true);
							}
							playableCards(myHand1, middlePile);
							return;
						} else {
							nextPlayerReady.setVisible(true);
							return;
						}
					}
				}
				// players[1] is always west player. Actions are same as for
				// previous player
				if (driver.players[1].getTurnToPlay() && turnNumber < 4) {

					if (driver.players[1] instanceof Human) {
						Card c = b.getCard();
						if (c.getSuit() == Suit.SPADE)
							spadesPlayed = true;
						playedCards.set(1, c);
						middlePile.add(c);
						l2 = new JLabel(b.getIcon());
						for (int i = 0; i < wCardRow1.getComponentCount(); i++) {
							if (((CardButton) wCardRow1.getComponent(i)).getCard().equals(c))
								wCardRow1.remove(b);
						}
						for (int i = 0; i < wCardRow2.getComponentCount(); i++) {
							if (((CardButton) wCardRow2.getComponent(i)).getCard().equals(c))
								wCardRow2.remove(b);
						}
						for (int i = 0; i < wCardRow3.getComponentCount(); i++) {
							if (((CardButton) wCardRow3.getComponent(i)).getCard().equals(c))
								wCardRow3.remove(b);
						}
						myHand1.remove(b);
						for (int i = 0; i < myHand1.size(); i++) {
							myHand1.get(i).setVisible(false);
						}
						centerWest.add(l2, BorderLayout.CENTER);
						driver.players[1].getHand().remove(c);
						playerW[0].setVisible(true);
					} else if (driver.players[1] instanceof Bot) {
						System.out.println("West: " + driver.players[1].getHand().toString());
						Card c = ((Bot) (driver.players[1])).playCard(middlePile);
						c.setScreenRes(screenResNum);
						if (c.getSuit() == Suit.SPADE)
							spadesPlayed = true;
						playedCards.set(1, c);
						middlePile.add(c);
						l2 = new JLabel(c.getImage());
						centerWest.add(l2, BorderLayout.CENTER);
					}
					turnNumber++;
					driver.players[1].setTurnToPlay(false);
					driver.players[2].setTurnToPlay(true);
					if (driver.players[2] instanceof Human && turnNumber < 4) {
						if (numHumans == 1) {
							playerN[0].setVisible(false);
							for (int i = 0; i < myHand2.size(); i++) {
								myHand2.get(i).setVisible(true);
								myHand2.get(i).setEnabled(true);
							}
							playableCards(myHand2, middlePile);

							return;
						} else {
							nextPlayerReady.setVisible(true);
							return;
						}
					}
				}
				// players[2] is always north player. Same actions as first
				// player
				if (driver.players[2].getTurnToPlay() && turnNumber < 4) {
					if (driver.players[2] instanceof Human) {
						Card c = b.getCard();
						if (c.getSuit() == Suit.SPADE)
							spadesPlayed = true;
						playedCards.set(2, c);
						middlePile.add(c);
						l3 = new JLabel(b.getIcon());
						cardDisplayNorth.remove(b);
						myHand2.remove(b);
						for (int i = 0; i < myHand2.size(); i++) {
							myHand2.get(i).setVisible(false);
						}
						centerNorth.add(l3, BorderLayout.CENTER);
						driver.players[2].getHand().remove(c);
						playerN[0].setVisible(true);
					} else if (driver.players[2] instanceof Bot) {
						System.out.println("North: " + driver.players[2].toString());
						Card c = ((Bot) (driver.players[2])).playCard(middlePile);
						c.setScreenRes(screenResNum);
						if (c.getSuit() == Suit.SPADE)
							spadesPlayed = true;
						playedCards.set(2, c);
						middlePile.add(c);
						l3 = new JLabel(c.getImage());
						centerNorth.add(l3, BorderLayout.CENTER);
					}
					turnNumber++;
					driver.players[2].setTurnToPlay(false);
					driver.players[3].setTurnToPlay(true);
					if (driver.players[3] instanceof Human && turnNumber < 4) {
						if (numHumans == 1) {
							playerE[0].setVisible(false);
							for (int i = 0; i < myHand3.size(); i++) {
								myHand3.get(i).setVisible(true);
								myHand3.get(i).setEnabled(true);
							}
							playableCards(myHand3, middlePile);
							return;
						} else {
							nextPlayerReady.setVisible(true);
							return;
						}
					}
				}
				// players[3] is always east player. Actions the same as first
				// player
				if (driver.players[3].getTurnToPlay() && turnNumber < 4) {
					if (driver.players[3] instanceof Human) {
						Card c = b.getCard();
						if (c.getSuit() == Suit.SPADE)
							spadesPlayed = true;
						playedCards.set(3, c);
						middlePile.add(c);
						l4 = new JLabel(b.getIcon());
						for (int i = 0; i < eCardRow1.getComponentCount(); i++) {
							if (((CardButton) eCardRow1.getComponent(i)).getCard().equals(c))
								eCardRow1.remove(b);
						}
						for (int i = 0; i < eCardRow2.getComponentCount(); i++) {
							if (((CardButton) eCardRow2.getComponent(i)).getCard().equals(c))
								eCardRow2.remove(b);
						}
						for (int i = 0; i < eCardRow3.getComponentCount(); i++) {
							if (((CardButton) eCardRow3.getComponent(i)).getCard().equals(c))
								eCardRow3.remove(b);
						}

						myHand3.remove(b);
						for (int i = 0; i < myHand3.size(); i++) {
							myHand3.get(i).setVisible(false);
						}
						centerEast.add(l4, BorderLayout.CENTER);
						driver.players[3].getHand().remove(c);
						playerE[0].setVisible(true);
					} else if (driver.players[3] instanceof Bot) {
						System.out.println("East: " + driver.players[3].getHand().toString());
						Card c = ((Bot) (driver.players[3])).playCard(middlePile);
						c.setScreenRes(screenResNum);
						if (c.getSuit() == Suit.SPADE)
							spadesPlayed = true;
						playedCards.set(3, c);
						middlePile.add(c);
						l4 = new JLabel(c.getImage());
						centerEast.add(l4, BorderLayout.CENTER);
					}
					turnNumber++;
					driver.players[3].setTurnToPlay(false);
					driver.players[0].setTurnToPlay(true);
					if (driver.players[0] instanceof Human && turnNumber < 4) {
						if (numHumans == 1) {
							playerS[0].setVisible(false);
							for (int i = 0; i < myHand0.size(); i++) {
								myHand0.get(i).setVisible(true);
								myHand0.get(i).setEnabled(true);
							}
							playableCards(myHand0, middlePile);
							return;
						} else {
							nextPlayerReady.setVisible(true);
							return;
						}
					}
				}
			} // End of while loop

			// evaluate winner of hand
			winner = driver.determineWinner(middlePile);

			// Determine which player played the winning card
			if (winner.equals(playedCards.get(0)))

			{
				// If a human won, display their name as winner
				if (driver.players[0] instanceof Human) {
					roundWinner.setText(driver.playerAry[0] + " wins!");
					// If bot won, display direction of bot as winner
				} else {
					roundWinner.setText("South Bot wins!");
				}
				playerS[3].setText("Tricks Taken: " + ++southTricksTaken);
				nextPlayer = 0;
				driver.players[0].setTurnToPlay(true);
				driver.players[1].setTurnToPlay(false);
				driver.players[2].setTurnToPlay(false);
				driver.players[3].setTurnToPlay(false);
			} else if (winner.equals(playedCards.get(1)))

			{
				if (driver.players[1] instanceof Human) {
					roundWinner.setText(driver.playerAry[1] + " wins!");
				} else {
					roundWinner.setText("West Bot wins!");
				}
				playerW[3].setText("Tricks Taken: " + ++westTricksTaken);
				nextPlayer = 1;
				driver.players[1].setTurnToPlay(true);
				driver.players[0].setTurnToPlay(false);
				driver.players[2].setTurnToPlay(false);
				driver.players[3].setTurnToPlay(false);
			} else if (winner.equals(playedCards.get(2)))

			{
				if (driver.players[2] instanceof Human) {
					roundWinner.setText(driver.playerAry[2] + " wins!");
				} else {
					roundWinner.setText("North Bot wins!");
				}
				playerN[3].setText("Tricks Taken: " + ++northTricksTaken);
				nextPlayer = 2;
				driver.players[2].setTurnToPlay(true);
				driver.players[1].setTurnToPlay(false);
				driver.players[0].setTurnToPlay(false);
				driver.players[3].setTurnToPlay(false);
			} else if (winner.equals(playedCards.get(3)))

			{
				if (driver.players[3] instanceof Human) {
					roundWinner.setText(driver.playerAry[3] + " wins!");
				} else {
					roundWinner.setText("East Bot wins!");
				}
				playerE[3].setText("TricksTaken: " + ++eastTricksTaken);
				nextPlayer = 3;
				driver.players[3].setTurnToPlay(true);
				driver.players[1].setTurnToPlay(false);
				driver.players[2].setTurnToPlay(false);
				driver.players[0].setTurnToPlay(false);
			}

			// update card images
			updateCardImages();

			// Display nextTurnButton and wait for click
			nextTurnButton.setVisible(true);

		}
	}

	/**
	 * Defines what happens when a NextTurn action is created. Used to set up
	 * game for the next round.
	 *
	 */
	private class NextTurn implements ActionListener {

		/**
		 * Hides the nextTurnButton, clears the center panel of cards, checks to
		 * see if the hand is over, displays winner and score if it is,
		 * otherwise resets all round variables and starts next round.
		 */
		public void actionPerformed(ActionEvent e) {
			// Hide nextTurn button
			nextTurnButton.setVisible(false);

			// Reset round variables
			middlePile.clear();
			roundWinner.setText("");
			centerSouth.remove(l1);
			centerWest.remove(l2);
			centerNorth.remove(l3);
			centerEast.remove(l4);
			turnNumber = 0;
			playedCards.set(0, null);
			playedCards.set(1, null);
			playedCards.set(2, null);
			playedCards.set(3, null);

			// Check to see if hand is over
			if (driver.players[0].getHand().size() == 0) {
				// Check to see who has the most tricks
				// Display the winner
				// Display score in JTable
				// Add button for next hand
				model.setValueAt(((Integer) tricksBidNS).toString(), 0, 1);
				model.setValueAt(((Integer) (tricksBidEW)).toString(), 0, 2);
				model.setValueAt(((Integer) (southTricksTaken + northTricksTaken)).toString(), 1, 1);
				model.setValueAt(((Integer) (eastTricksTaken + westTricksTaken)).toString(), 1, 2);
				sandbagsNS = sandBags(northTricksBid, southTricksBid, northTricksTaken, southTricksTaken);
				sandbagsEW = sandBags(eastTricksBid, westTricksBid, westTricksTaken, eastTricksTaken);
				model.setValueAt(((Integer) sandbagsNS).toString(), 2, 1);
				model.setValueAt(((Integer) sandbagsEW).toString(), 2, 2);
				totalBagsNS += sandbagsNS;
				totalBagsEW += sandbagsEW;

				if (totalBagsNS >= 10) {
					scoreNS -= 100;
					totalBagsNS = 0;
				}
				if (totalBagsEW >= 10) {
					scoreEW -= 100;
					totalBagsEW = 0;
				}
				model.setValueAt(((Integer) totalBagsNS).toString(), 3, 1);
				model.setValueAt(((Integer) totalBagsEW).toString(), 3, 2);
				ptsScoredNS = pointsScored(northTricksBid, southTricksBid, northTricksTaken, southTricksTaken);
				ptsScoredEW = pointsScored(eastTricksBid, westTricksBid, westTricksTaken, eastTricksTaken);
				model.setValueAt(((Integer) ptsScoredNS).toString(), 4, 1);
				model.setValueAt(((Integer) ptsScoredEW).toString(), 4, 2);
				scoreNS += ptsScoredNS;
				scoreEW += ptsScoredEW;
				model.setValueAt(((Integer) scoreNS).toString(), 5, 1);
				model.setValueAt(((Integer) scoreEW).toString(), 5, 2);

				constraints.gridx = 0;
				constraints.gridy = 0;
				centerPanel2.add(scoring.getTableHeader(), constraints);
				constraints.gridx = 0;
				constraints.gridy = 1;
				centerPanel2.add(scoring, constraints);

				if (scoreNS >= 500) {
					endOfGameWinner = new JLabel("Congratuations N & S! You win!");
					endOfGameWinner.setFont(new Font("Sans Serif", Font.BOLD, 30));
					constraints.gridx = 0;
					constraints.gridy = 3;
					centerPanel2.add(endOfGameWinner, constraints);
				} else if (scoreEW >= 500) {
					endOfGameWinner = new JLabel("Congratuations E & W! You win!");
					endOfGameWinner.setFont(new Font("Sans Serif", Font.BOLD, 30));
					constraints.gridx = 0;
					constraints.gridy = 3;
					centerPanel2.add(endOfGameWinner, constraints);
				} else {
					nextHand = new JButton("Next Hand");
					NextHand next = new NextHand();
					if (screenResNum == 1)
						nextHand.setPreferredSize(new Dimension(140, 50));
					else
						nextHand.setPreferredSize(new Dimension(200, 100));
					nextHand.addActionListener(next);
					nextHand.setFont(font);
					constraints.gridx = 0;
					constraints.gridy = 3;
					centerPanel2.add(nextHand, constraints);
				}
				return;
			}

			// Check which player should start next round, display hand if
			// human, otherwise click
			// a PlayCard button. nextPlayer == 0 corresponds to south, 1 to
			// west, 2 to north and
			// 3 to east as always
			if (nextPlayer == 0) {
				if (driver.players[0] instanceof Human) {
					playerS[0].setVisible(false);
					for (int i = 0; i < myHand0.size(); i++) {
						myHand0.get(i).setVisible(true);
					}
					playableCards(myHand0, middlePile);
					return;
				}
			} else if (nextPlayer == 1) {
				if (driver.players[1] instanceof Human) {
					playerW[0].setVisible(false);
					for (int i = 0; i < myHand1.size(); i++) {
						myHand1.get(i).setVisible(true);
					}
					playableCards(myHand1, middlePile);
					return;
				}
			} else if (nextPlayer == 2) {
				if (driver.players[2] instanceof Human) {
					playerN[0].setVisible(false);
					for (int i = 0; i < myHand2.size(); i++) {
						myHand2.get(i).setVisible(true);
					}
					playableCards(myHand2, middlePile);
					return;
				}
			} else if (nextPlayer == 3) {
				if (driver.players[3] instanceof Human) {
					playerE[0].setVisible(false);
					for (int i = 0; i < myHand3.size(); i++) {
						myHand3.get(i).setVisible(true);
					}
					playableCards(myHand3, middlePile);
					return;
				}
			}

			CardButton b = new CardButton();
			PlayCard p = new PlayCard();
			b.addActionListener(p);
			b.doClick();
		}
	}

	/**
	 * Defines what happens when a NextHand action is created. Used to move game
	 * to next hand.
	 */
	private class NextHand implements ActionListener {
		/**
		 * Disposes of current GUI window, creates a new GUI passing in screen
		 * resolution, score and sand bags from this hand
		 */
		public void actionPerformed(ActionEvent e) {
			dispose();
			GUI gui = new GUI(screenResNum, scoreNS, scoreEW, totalBagsNS, totalBagsEW);
			gui.display();
		}
	}

	/**
	 * Defines what happens when a NextHumanPlayer action is created. Used when
	 * there is more than one human player in a game so players cannot see each
	 * other's cards.
	 *
	 */
	private class NextHumanPlayer implements ActionListener {

		/**
		 * When this button is clicked it displays the next player's hand
		 * instead of displaying the next player's hand right away. This gives
		 * two human players time to switch seats or look away so they do not
		 * see each other's hands.
		 * 
		 */
		public void actionPerformed(ActionEvent e) {
			nextPlayerReady.setVisible(false);
			if (driver.players[0].getTurnToPlay()) {
				playerS[0].setVisible(false);
				for (int i = 0; i < myHand0.size(); i++) {
					myHand0.get(i).setVisible(true);
					myHand0.get(i).setEnabled(true);
				}
				playableCards(myHand0, middlePile);
			} else if (driver.players[1].getTurnToPlay()) {
				playerW[0].setVisible(false);
				for (int i = 0; i < myHand1.size(); i++) {
					myHand1.get(i).setVisible(true);
					myHand1.get(i).setEnabled(true);
				}
				playableCards(myHand1, middlePile);
			} else if (driver.players[2].getTurnToPlay()) {
				playerN[0].setVisible(false);
				for (int i = 0; i < myHand2.size(); i++) {
					myHand2.get(i).setVisible(true);
					myHand2.get(i).setEnabled(true);
				}
				playableCards(myHand2, middlePile);
			} else if (driver.players[3].getTurnToPlay()) {
				playerE[0].setVisible(false);
				for (int i = 0; i < myHand3.size(); i++) {
					myHand3.get(i).setVisible(true);
					myHand3.get(i).setEnabled(true);
				}
				playableCards(myHand3, middlePile);
			}
		}

	}

	/**
	 * Defines what happens when an ExitEvent action is created. Used to exit
	 * the game.
	 *
	 */
	private class ExitEvent implements ActionListener {
		/**
		 * Exit the game on click.
		 */
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	/**
	 * Defines what happens when a BlueEvent action is created. Used to change
	 * the background color to blue.
	 *
	 */
	private class BlueEvent implements ActionListener {
		/**
		 * Calls setBackgroundColor method to change background color to blue
		 */
		public void actionPerformed(ActionEvent e) {
			setBackgroundColor(new Color(0, 0, 255));
		}
	}

	/**
	 * Defines what happens when a GreyEvent action is created. Used to change
	 * the background color to grey.
	 */
	private class GreyEvent implements ActionListener {
		/**
		 * Calls setBackgroundColor method to change background color to grey
		 */
		public void actionPerformed(ActionEvent e) {
			setBackgroundColor(Color.GRAY);
		}
	}

	/**
	 * Defines what happens when a GreenEvent action is created. Used to change
	 * the background color to green.
	 */
	private class GreenEvent implements ActionListener {
		/**
		 * Calls setBackgroundColor method to change background color to green
		 */
		public void actionPerformed(ActionEvent e) {
			setBackgroundColor(new Color(0, 200, 0));
		}
	}

	/**
	 * Defines what happens when a RedEvent action is created. Used to change
	 * the background color to red.
	 */
	private class RedEvent implements ActionListener {
		/**
		 * Calls setBackgroundColor method to change background color to red
		 */
		public void actionPerformed(ActionEvent e) {
			setBackgroundColor(new Color(200, 0, 0));
		}
	}

	/**
	 * Defines what happens when a NewGameEvent action is created. Used to start
	 * a new game.
	 *
	 */
	private class NewGameEvent implements ActionListener {
		/**
		 * Disposes of current GUI window and opens a new one, not carrying the
		 * score or sand bags over. Does carry over the screen resolution.
		 */
		public void actionPerformed(ActionEvent e) {
			dispose();
			GUI gui = new GUI(screenResNum);
			gui.display();
		}
	}

	/*
	 * OTHER PRIVATE CLASSES
	 */

	/**
	 * Defines a new AbstractTableModel to be used with the JTable that displays
	 * the score.
	 *
	 */
	private class MyModel extends AbstractTableModel {
		// Names for column headers and row headers. Empty strings where scores
		// will go
		String[] colNames = { "", "North & South", "East & West" };
		String[][] data = { { "Tricks Bid", "", "" }, { "Tricks Taken", "", "" }, { "Sand Bags", "", "" },
				{ "Total Bags", "", "" }, { "Points Scored", "", "" }, { "Total Points", "", "" } };

		/**
		 * Returns the header of the column
		 * 
		 * @param col
		 *            the column number
		 */
		public String getColumnName(int col) {
			return colNames[col];
		}

		/**
		 * Returns the total number of columns
		 */
		public int getColumnCount() {
			return colNames.length;
		}

		/**
		 * returns the total number of rows
		 */
		public int getRowCount() {
			return data.length;
		}

		/**
		 * Returns the data value at a specified row and column
		 */
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		/**
		 * Updates the value at a specified row and column and updates the table
		 */
		public void setValueAt(Object aValue, int row, int col) {
			data[row][col] = (String) aValue;
			fireTableCellUpdated(row, col);
		}
	}

	/*
	 * PUBLIC METHODS
	 */

	/**
	 * Displays the GUI with default properties
	 */
	public void display() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = new Dimension(1366, 768);
		setMinimumSize(d);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		setTitle("Spades");
	}

}
