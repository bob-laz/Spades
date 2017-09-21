import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * This class defines the Welcome GUI that is displayed when the program is
 * first launched. It allows the user to select the number of Humans or Bots and
 * enter names for the Human players, as well as choose high or low screen
 * resolution. There is also a button they can click to display the rules of
 * spades as adapted for our version of the game.
 * 
 * @author Bob Laskowski
 * @author Peter Klein
 * @version 1.3
 *
 */
public class WelcomeGUI extends JFrame {

	/*
	 * INSTANCE VARIABLES
	 */

	/**
	 * wholePage holds all items on the page, panel1 holds the logo image,
	 * panel2 holds the four player rows and panel 3 holds the how to play and
	 * play buttons
	 */
	private JPanel wholePage, panel1, panel2, panel3;

	/**
	 * Declare all buttons to be used
	 */
	private JButton p1button;
	private JButton p2button;
	private JButton p3button;
	private JButton p4button;

	/**
	 * Declare the text fields to be used to enter player names if Human
	 */
	private JTextField p1text, p2text, p3text, p4text;

	/**
	 * Declare the radio buttons to be used to select the screen resolution
	 */
	private JRadioButton screenRes1, screenRes2;

	/**
	 * Holds the names of the players entered in the JTextFields. Holds the
	 * actual text of the name if they are human, holds "Bot" if bot. Static so
	 * that it can be accessed without declaring a new WelcomeGUI object from
	 * driver so that driver knows whether to make human or bot players
	 */
	private static String[] players;

	/**
	 * The font to be used for the window
	 */
	private Font font;

	/**
	 * The Grid Bag Constraints to be used to set proper layout with Grid Bag
	 * Layouts
	 */
	private GridBagConstraints c1;
	private GridBagConstraints c3;

	/**
	 * The button group to group the two radio buttons together so that they
	 * cannot both be selected at once
	 * 
	 */
	private ButtonGroup group;

	/**
	 * Insets used to set the margins for the buttons
	 */
	private Insets i;

	/**
	 * The color for the background
	 */
	private Color green;

	/*
	 * CONSTRUCTORS
	 */

	/**
	 * This constructor defines the WelcomeGUI window. Defines all variables and
	 * components used and sets the proper style.
	 * 
	 */
	public WelcomeGUI() {

		// Initialize all variables to be used
		initializeVariables();

		// Set the look and feel to Windows. Catch any exceptions thrown if this
		// look and feel is
		// not supported, but do not print error messages
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
			// e.printStackTrace();
		}

		// Initialize all items in player 1 row. This is always South player
		initializePlayer1();

		// Initialize all items in player 2 row. This is always West player
		initializePlayer2();

		// Initialize all items in player 3 row. This is always North player
		initializePlayer3();

		// Initialize all items in player 4 row. This is always East player
		initializePlayer4();

		// Initialize all items for screen resolution row
		initializeScreenResolution();

		// Play and How to Play buttons
		initializeButtons();

		// Set background color
		setBackgroundColor(green);
	}

	/*
	 * PRIVATE INITIALIZATION METHODS FOR USE IN CONSTRUCTOR
	 */

	/**
	 * Initializes all variables used in constructor. Defined in a separate
	 * method to increase readability of the code and make changes easier.
	 * 
	 */
	private void initializeVariables() {
		c1 = new GridBagConstraints();
		GridBagConstraints c2 = new GridBagConstraints();
		c3 = new GridBagConstraints();
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		wholePage = new JPanel();
		wholePage.setLayout(new GridBagLayout());
		add(wholePage);

		panel1.setLayout(new GridBagLayout());
		c2.gridx = 0;
		c2.gridy = 0;
		wholePage.add(panel1, c2);

		panel2.setLayout(new GridBagLayout());
		c2.gridx = 0;
		c2.gridy = 1;
		wholePage.add(panel2, c2);
		panel3.setLayout(new GridBagLayout());
		c1.gridx = 1;
		c1.gridy = 5;
		panel2.add(panel3, c1);

		players = new String[4];

		group = new ButtonGroup();

		i = new Insets(10, 20, 10, 20);
		font = new Font("Sans Serif", Font.BOLD, 30);

		Image img = null;
		try {
			img = ImageIO.read(getClass().getResourceAsStream("PNG-cards-1.3/Logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
	  The Image to hold the logo image for size manipulation
	 */
		assert img != null;
		Image scaledImage = img.getScaledInstance(617, 274, Image.SCALE_FAST);
		JLabel logo = new JLabel(new ImageIcon(scaledImage));
		green = new Color(0, 200, 0);
		panel1.add(logo);
	}

	/**
	 * Initialize all components used for the Player 1 row. Declared in separate
	 * method to increase readability and make changes easier.
	 * 
	 */
	private void initializePlayer1() {
		/*
	  Declare all JLabels to be used to display text and images
	 */
		JLabel player1 = new JLabel("Player 1");
		player1.setFont(font);
		c1.gridx = 0;
		c1.gridy = 0;
		c1.insets = i;
		panel2.add(player1, c1);

		p1button = new JButton("Human");
		p1button.addActionListener(new HumanBotButton1());
		p1button.setFont(font);
		c1.gridx = 1;
		c1.gridy = 0;
		c1.insets = i;
		panel2.add(p1button, c1);

		p1text = new JTextField("Enter Name", 10);
		p1text.setHorizontalAlignment(JTextField.CENTER);
		p1text.setFont(font);
		c1.gridx = 2;
		c1.gridy = 0;
		c1.insets = i;
		panel2.add(p1text, c1);
	}

	/**
	 * Initialize all components used for the Player 2 row. Declared in separate
	 * method to increase readability and make changes easier.
	 * 
	 */
	private void initializePlayer2() {
		JLabel player2 = new JLabel("Player 2");
		player2.setFont(font);
		c1.gridx = 0;
		c1.gridy = 1;
		c1.insets = i;
		panel2.add(player2, c1);

		p2button = new JButton("   Bot   ");
		p2button.addActionListener(new HumanBotButton2());
		p2button.setFont(font);
		c1.gridx = 1;
		c1.gridy = 1;
		c1.insets = i;
		panel2.add(p2button, c1);

		p2text = new JTextField("Bot", 10);
		p2text.setHorizontalAlignment(JTextField.CENTER);
		p2text.setFont(font);
		p2text.setEditable(false);
		c1.gridx = 2;
		c1.gridy = 1;
		c1.insets = i;
		panel2.add(p2text, c1);
	}

	/**
	 * Initialize all components used for the Player 3 row. Declared in separate
	 * method to increase readability and make changes easier.
	 * 
	 */
	private void initializePlayer3() {
		JLabel player3 = new JLabel("Player 3");
		player3.setFont(font);
		c1.gridx = 0;
		c1.gridy = 2;
		c1.insets = i;
		panel2.add(player3, c1);

		p3button = new JButton("   Bot   ");
		p3button.addActionListener(new HumanBotButton3());
		p3button.setFont(font);
		c1.gridx = 1;
		c1.gridy = 2;
		c1.insets = i;
		panel2.add(p3button, c1);

		p3text = new JTextField("Bot", 10);
		p3text.setHorizontalAlignment(JTextField.CENTER);
		p3text.setFont(font);
		p3text.setEditable(false);
		c1.gridx = 2;
		c1.gridy = 2;
		c1.insets = i;
		panel2.add(p3text, c1);
	}

	/**
	 * Initialize all components used for the Player 4 row. Declared in separate
	 * method to increase readability and make changes easier.
	 * 
	 */
	private void initializePlayer4() {
		JLabel player4 = new JLabel("Player 4");
		c1.gridx = 0;
		c1.gridy = 3;
		c1.insets = i;
		player4.setFont(font);
		panel2.add(player4, c1);
		p4button = new JButton("   Bot   ");

		p4button.addActionListener(new HumanBotButton4());
		p4button.setFont(font);
		c1.gridx = 1;
		c1.gridy = 3;
		c1.insets = i;
		panel2.add(p4button, c1);

		p4text = new JTextField("Bot", 10);
		p4text.setFont(font);
		p4text.setHorizontalAlignment(JTextField.CENTER);
		p4text.setEditable(false);
		c1.gridx = 2;
		c1.gridy = 3;
		c1.insets = i;
		panel2.add(p4text, c1);
	}

	/**
	 * Initialize all components used for the screen resolution row. Declared in
	 * separate method to increase readability and make changes easier.
	 * 
	 */
	private void initializeScreenResolution() {
		JLabel screenRes = new JLabel("Screen Resolution:");
		screenRes.setFont(font);
		screenRes1 = new JRadioButton("High Resolution");
		screenRes1.setSelected(true);
		screenRes1.setFont(font);
		screenRes1.setActionCommand("High Resolution");
		screenRes2 = new JRadioButton("Low Resolution");
		screenRes2.setFont(font);
		c1.gridx = 0;
		c1.gridy = 4;
		c1.insets = i;
		panel2.add(screenRes, c1);
		c1.gridx = 1;
		c1.gridy = 4;
		c1.insets = i;
		panel2.add(screenRes1, c1);
		c1.gridx = 2;
		c1.gridy = 4;
		c1.insets = i;
		panel2.add(screenRes2, c1);
		screenRes2.setActionCommand("Low Resolution");
		group.add(screenRes1);
		group.add(screenRes2);
	}

	/**
	 * Initialize all components used for the buttons row. Declared in separate
	 * method to increase readability and make changes easier.
	 * 
	 */
	private void initializeButtons() {
		// How to play button
		JButton rules = new JButton("How to Play");
		rules.setFont(font);
		rules.addActionListener(new HowToPlay());
		c3.gridx = 0;
		c3.gridy = 0;
		c3.insets = i;
		panel3.add(rules, c3);

		// play button
		JButton play = new JButton("  Play  ");
		play.addActionListener(new Play());
		play.setFont(font);
		c3.gridx = 1;
		c3.gridy = 0;
		c3.insets = i;
		panel3.add(play, c3);
	}

	/*
	 * PRIVATE METHODS
	 */

	/**
	 * Used in the constructor to set the background color. Declared in separate
	 * method to increase readability of the code and make changes easier.
	 * 
	 * @param c
	 *            The color the background should be set to
	 */
	private void setBackgroundColor(Color c) {
		wholePage.setBackground(c);
		screenRes1.setBackground(c);
		screenRes2.setBackground(c);
		panel1.setBackground(c);
		panel2.setBackground(c);
		panel3.setBackground(c);

	}

	/*
	 * ACTION LISTENERS
	 */

	/**
	 * This class defines the action performed when the human/bot button is
	 * clicked for player 1
	 *
	 */
	private class HumanBotButton1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if (b.getText().equals("Human")) {
				b.setText("   Bot   ");
				p1text.setEditable(false);
				p1text.setText("Bot");
			} else {
				b.setText("Human");
				p1text.setEditable(true);
				p1text.setText("Enter Name");
			}
		}
	}

	/**
	 * This class defines the action performed when the human/bot button is
	 * clicked for player 2
	 *
	 */
	private class HumanBotButton2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if (b.getText().equals("Human")) {
				b.setText("   Bot   ");
				p2text.setEditable(false);
				p2text.setText("Bot");
			} else {
				b.setText("Human");
				p2text.setEditable(true);
				p2text.setText("Enter Name");
			}
		}
	}

	/**
	 * This class defines the action performed when the human/bot button is
	 * clicked for player 3
	 *
	 */
	private class HumanBotButton3 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if (b.getText().equals("Human")) {
				b.setText("   Bot   ");
				p3text.setEditable(false);
				p3text.setText("Bot");
			} else {
				b.setText("Human");
				p3text.setEditable(true);
				p3text.setText("Enter Name");
			}
		}
	}

	/**
	 * This class defines the action performed when the human/bot button is
	 * clicked for player 4
	 *
	 */
	private class HumanBotButton4 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if (b.getText().equals("Human")) {
				b.setText("   Bot   ");
				p4text.setEditable(false);
				p4text.setText("Bot");
			} else {
				b.setText("Human");
				p4text.setEditable(true);
				p4text.setText("Enter Name");
			}
		}
	}

	/**
	 * This class defines the action performed when the how to play button is
	 * clicked. Displays the Rules Window GUI
	 *
	 */
	private class HowToPlay implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			RulesWindow rules = new RulesWindow();
			rules.display();
		}
	}

	/**
	 * This class defines the action performed when the play button is clicked.
	 * Displays the main game GUI. Disposes of the Welcome GUI window. Adds the
	 * player selections to the players array, depending on whether bot or human
	 * is selected. Adds "Bot" if bot, otherwise adds the name entered in the
	 * JTextField for human
	 *
	 */
	private class Play implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (p1button.getText().equals("Bot"))
				players[0] = "Bot";
			else
				players[0] = p1text.getText();
			if (p2button.getText().equals("Bot"))
				players[1] = "Bot";
			else
				players[1] = p2text.getText();
			if (p3button.getText().equals("Bot"))
				players[2] = "Bot";
			else
				players[2] = p3text.getText();
			if (p4button.getText().equals("Bot"))
				players[3] = "Bot";
			else
				players[3] = p4text.getText();

			dispose();
			GUI gui = new GUI(getScreenRes());
			gui.display();
		}
	}

	/*
	 * PUBLIC METHODS
	 */

	/**
	 * Displays the Welcome GUI with default window options
	 */
	public void display() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Dimension d = new Dimension(1366, 768);
		setMinimumSize(d);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		setTitle("Welcome GUI");
	}

	/**
	 * Returns the player array that is initialized when "Play" is clicked.
	 * Static so that driver can access it without declaring a new WelcomeGUI
	 * object.
	 * 
	 * @return The array holds the player names if human, holds "Bot" if bot
	 */
	public static String[] getPlayers() {
		return players;
	}

	/**
	 * Returns the screen resolution selection. Returns 1 if low resolution
	 * selected, returns 0 if high resolution selected
	 * 
	 * @return An int, 1 if low resolution and 0 if high resolution
	 */
	public int getScreenRes() {
		if (screenRes1.isSelected())
			return 0;
		else
			return 1;
	}

}
