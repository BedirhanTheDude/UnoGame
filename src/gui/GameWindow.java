package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import gui.GameWindow.GamePanel.PlayerLabel;
import management.AiPlayer;
import management.GameLogListener;
import management.GameSession;
import management.Player;
import uno.Action;
import uno.Card;
import uno.Decks;
import uno.EColor;
import uno.NumberCard;
import uno.WildCard;

/**
 * The main window of the application.
 * <p>
 * This class is the main window of the application, containing various panels and components
 * for managing the game. It includes a menu panel, log panel, and game panel, and provides methods for initializing
 * these components and managing game events.
 * </p>
 * 
 * @author Bedirhan Sakaoğlu
 */
public class GameWindow {
	
	private StartGameListener startGameListener;
	private GameLogListener logListener = new GameLogListener();
	private MenuPanel menuPanel;
	private LogPanel logPanel;
	private JPanel gamePanel;
	private Border defaultBorder = BorderFactory.createLineBorder(Color.orange, 3);
	private Color defaultPurple = new Color(80, 60, 125);
	private Color darkerPurple = new Color(50, 40, 80);
	
	private static ImageIcon unoLogo = new ImageIcon("Assets\\logo.png");
	
	private GameWindow findThis() {
		return this;
	}
	
	/**
	 * Panel for displaying logs.
	 * <p>
	 * This inner class represents a panel for displaying logs of the game events.
	 * It includes functionality for updating and displaying log files.
	 * </p>
	 */
	private class LogPanel extends JPanel {
		
		private JTextArea logTextArea = new JTextArea();
		
		private JPanel logPanel = new JPanel();
		
		/**
		 * Button for displaying log files.
		 */
		private class LogButton extends JButton {
			
			File logFile;
			
			/**
			 * Constructs a new LogButton with the specified text, dimension, and log file.
			 * 
			 * @param text    the text displayed on the button.
			 * @param dimension    the dimension of the button.
			 * @param logFile    the log file associated with the button.
			 */
			public LogButton(String text, Dimension dimension, File logFile) {
				super(text); this.logFile = logFile;
				setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
				setFocusable(false);
				setBackground(Color.DARK_GRAY);
				setForeground(Color.WHITE);
				setBorder(defaultBorder);
				setPreferredSize(dimension);
				
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						logTextArea.setText(logString());
					}
				});
			}
			
			/**
			 * Takes the text content of the log file.
			 * 
			 * @return the content of the log file as a string.
			 */
			private String logString() {
				String text = "";
				try {
					Scanner input = new Scanner(logFile);
					while (input.hasNextLine()) {
						text = text.concat(input.nextLine() + "\n");
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				return text;
			}
		}
		
		/**
		 * Initializes a new LogPanel.
		 */
		public LogPanel() {
			super();
			setBackground(defaultPurple);
			setLayout(new BorderLayout());
			
			JPanel panelEast = new JPanel();
			panelEast.setBackground(defaultPurple);
			JPanel panelWest = new JPanel();
			panelWest.setBackground(defaultPurple);
			JPanel panelSouth = new JPanel();
			panelSouth.setBackground(defaultPurple);
            logPanel = new JPanel();
			logPanel.setBackground(defaultPurple);
			JPanel panelCenter = new JPanel();
			panelCenter.setBackground(defaultPurple);
			
			logPanel.setLayout(new FlowLayout());
			updateLogButtons();
			
			panelCenter.setLayout(new FlowLayout());
			logTextArea.setEditable(true);
			logTextArea.setBackground(darkerPurple);
			logTextArea.setForeground(Color.white);
			logTextArea.setBorder(defaultBorder);
			logTextArea.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
			logTextArea.setLineWrap(true);
			logTextArea.setWrapStyleWord(true);
			JScrollPane scrollPane = new JScrollPane(logTextArea);
			scrollPane.setPreferredSize(new Dimension(900, 600));
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

			panelCenter.add(scrollPane);
			
			add(panelEast, BorderLayout.EAST);
			add(panelWest, BorderLayout.WEST);
			add(panelSouth, BorderLayout.SOUTH);
			add(logPanel, BorderLayout.NORTH);
			add(panelCenter, BorderLayout.CENTER);
			
		}
		
		/**
		 * Finds all log files in the "GameLogs" folder in "Data".
		 * 
		 * @return a list of log files found in the folder.
		 */
		private ArrayList<File> findAllLogTexts() {
			
			File dataFolder = new File("Data\\GameLogs");
			File[] filesInDataFolder = dataFolder.listFiles();
			
			ArrayList<File> logFiles = new ArrayList<File>();
			for (File file : filesInDataFolder) {
				if (file.getName().endsWith(".txt") && file.isFile()) logFiles.add(file);
			}
			
			return logFiles;
		}
		
		/**
		 * Updates the log buttons based on the log files found. Use to redraw buttons after new log entry.
		 */
		private void updateLogButtons() {
			ArrayList<File> logFiles = findAllLogTexts();
			logPanel.removeAll();
			for (File file : logFiles) {
				String logName = file.getName();
				LogButton button = new LogButton(logName.substring(0, logName.length() - 4), new Dimension(150, 80), file);
				this.logPanel.add(button);
			}
			logPanel.repaint();
			this.logPanel.revalidate();
		}
	}
	
	/**
	 * Event for initializing the start of a game.
	 */
	private class StartGameEvent extends EventObject {

		private static final long serialVersionUID = 5443729841541994158L;
		
		private Component parentPanel;
		private int playerCount;
		private String gameName;
		
		/**
		 * Constructs a new StartGameEvent with the specified source, parent panel, game name, and player count.
		 * 
		 * @param source   the object on which the event initially occurred.
		 * @param parentPanel   the parent panel where the game will be started.
		 * @param gameName   the name of the game.
		 * @param comboBox   the combo box containing the player count selection.
		 */
		public StartGameEvent(Component source, Component parentPanel, String gameName, JComboBox<Integer> comboBox) {
			super(source);
			this.parentPanel = parentPanel; this.playerCount = (int) comboBox.getSelectedItem(); this.gameName = gameName;
		}
		
		/**
		 * Gets the source panel of the event.
		 * 
		 * @return the source panel of the event.
		 */
		public Component getSourcePanel() {
			return (Component)getSource();
		}
		
		/**
		 * Gets the parent panel where the game will be started.
		 * 
		 * @return the parent panel where the game will be started.
		 */
		public Component getParentPanel() {
			return parentPanel;
		}
		
		/**
		 * Gets the player count selected for the game.
		 * 
		 * @return the player count selected for the game.
		 */
		public int getPlayerCount() {
			return playerCount;
		}
		
		/**
		 * Gets the name of the game.
		 * 
		 * @return the name of the game.
		 */
		public String getGameName() {
			return gameName;
		}
	}
	
	/**
	 * Listener for starting a new game.
	 */
	private class StartGameListener implements EventListener {
		
		/**
		 * Constructs a new StartGameListener.
		 */
		public StartGameListener() {
			super();
		}
		
		/**
		 * Handles the action of starting a new game and initializes a new game panel.
		 * 
		 * @param e   the StartGameEvent containing information about the game to start.
		 */
		public void startGameActionPerformed(StartGameEvent e) {
			Component sourcePanel = e.getSourcePanel();
			Component parentPanel = e.getParentPanel();
			String gameName = e.getGameName();
			
			if (gameName.equals("")) return;
			
			int playerCount = e.getPlayerCount();
			new GamePanel(gameName, playerCount);
			String logEntry = String.format("Started game with name: %s", gameName);
			logListener.updateGameLogEvent(logListener.new GameLogEvent(this, logEntry, gameName));
			sourcePanel.setVisible(false); parentPanel.setVisible(false);
		}
		
	}
	
	/**
	 * Button for the main menu.
	 */
	private class MenuButton extends JButton {
		
		private static final long serialVersionUID = -7407911836146780083L;

		/**
		 * Constructs a new MenuButton with the specified text and dimension.
		 * 
		 * @param text   the text displayed on the button.
		 * @param dimension   the dimension of the button.
		 */
		public MenuButton(String text, Dimension dimension) {
			super(text);
			setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
			setFocusable(false);
			setBackground(Color.DARK_GRAY);
			setForeground(Color.WHITE);
			setBorder(defaultBorder);
			setPreferredSize(dimension);
		}
	}
	/**
	 * Prompt window for starting a game. Used to determine name of the game session and the number of players.
	 */
	private class PromptWindow extends JFrame {
		
		private static final long serialVersionUID = -6417897788146328130L;
		
		/**
		 * Constructs a new PromptWindow with the specified anchor, parent panel, and start game listener.
		 * 
		 * @param anchor   the anchor component which is the button that creates the popup.
		 * @param parentPanel   the parent of the parent of the anchor.
		 * @param startGameListener   the start game listener that fires the start game events.
		 */
		public PromptWindow(Component anchor, Component parentPanel, StartGameListener startGameListener) {
			super("Game Config");
			setIconImage(unoLogo.getImage());
			setSize(300, 300);
			setLocationRelativeTo(anchor);
			setResizable(false);
			setLayout(null);
			getContentPane().setBackground(darkerPurple);
			anchor.setEnabled(false);
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					anchor.setEnabled(true);
				}
			});
			
			Integer[] choices = {2, 3, 4, 5, 6, 7, 8, 9, 10};
			JComboBox<Integer> comboBox = new JComboBox(choices);
			comboBox.setFocusable(false);
			comboBox.setBounds(new Rectangle(190, 50, 60, 40));
			comboBox.setBorder(defaultBorder);
			comboBox.setBackground(defaultPurple);
			comboBox.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
			
			JLabel playerCount = new JLabel("Player Count: ");
			playerCount.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
			playerCount.setBackground(darkerPurple);
			playerCount.setForeground(Color.white);
			playerCount.setBounds(new Rectangle(10, 50, 200, 40));
			
			JLabel gameName = new JLabel("Game Name: ");
			gameName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
			gameName.setBackground(darkerPurple);
			gameName.setForeground(Color.white);
			gameName.setBounds(new Rectangle(10, 100, 200, 40));
			
			JTextField gameNameField = new JTextField();
			gameNameField.setBounds(140, 100, 120, 40);
			gameNameField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
			gameNameField.setBorder(BorderFactory.createLineBorder(Color.orange, 3));
			gameNameField.setBackground(Color.DARK_GRAY);
			gameNameField.setForeground(Color.white);
			
			MenuButton startGameButton = new MenuButton("Start Game", new Dimension(100, 40));
			startGameButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
			startGameButton.setBounds(50, 160, 200, 40);
			startGameButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					startGameListener.startGameActionPerformed(new StartGameEvent(getFrame(), parentPanel, gameNameField.getText(), comboBox));
					anchor.setEnabled(true);
				}
			});
			
			add(startGameButton);
			add(playerCount);
			add(comboBox);
			add(gameNameField);
			add(gameName);
			setVisible(true);
		}
		
		private JFrame getFrame() {
			return this;
		}
	}
	
	/**
	 * Panel for displaying the winner of the game.
	 */
	public class WinnerPanel extends JPanel {

		private static final long serialVersionUID = -7662792157746327637L;
		
		/**
		 * Constructs a new WinnerPanel with the specified game panel and winner.
		 * 
		 * @param gamePanel   the game panel.
		 * @param winner   the winner of the game.
		 */
		public WinnerPanel(GamePanel gamePanel, Player winner) {
			super();
			setBackground(defaultPurple);
			setLayout(new BorderLayout(0, 0));
			PlayerLabel playerLabel = gamePanel.new PlayerLabel(winner);
			if (winner instanceof AiPlayer) {
				playerLabel.setText("WINNER: " + ((AiPlayer)winner).getBotName());
			} else {
				playerLabel.setText("WINNER: Player");
			}
			playerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 100));
			add(playerLabel);
		}
		
	}
	
	/**
	 * Panel for the main menu.
	 */
	private class MenuPanel extends JPanel {
		
		private static final long serialVersionUID = -6062989098068176936L;

		/**
		 * Constructs a new MenuPanel.
		 */
		public MenuPanel() {
			super();
			setBackground(defaultPurple);
			setLayout(new BorderLayout(0, 0));
			
			JLabel lblNewLabel = new JLabel("A REALLY SCUFFED UNO GAME");
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
			lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
			add(lblNewLabel, BorderLayout.NORTH);
			
			JPanel rightPanel = new JPanel();
			rightPanel.setBackground(defaultPurple);
			rightPanel.setPreferredSize(new Dimension(100, 1));
			add(rightPanel, BorderLayout.EAST);
			
			JPanel leftPanel = new JPanel();
			leftPanel.setBackground(defaultPurple);
			leftPanel.setPreferredSize(new Dimension(100, 1));
			add(leftPanel, BorderLayout.WEST);
			
			JPanel bottomPanel = new JPanel();
			bottomPanel.setBackground(new Color(80, 60, 125));
			bottomPanel.setPreferredSize(new Dimension(1, 100));
			add(bottomPanel, BorderLayout.SOUTH);
			
			JPanel centerPanel = new JPanel();
			centerPanel.setBackground(new Color(80, 60, 125));
			add(centerPanel, BorderLayout.CENTER);
			GridBagLayout gbl_centerPanel = new GridBagLayout();
			centerPanel.setLayout(gbl_centerPanel);
			GridBagConstraints gbc = new GridBagConstraints();
			
			JButton newGameButton = new MenuButton("New Game", new Dimension(250, 100));
			newGameButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new PromptWindow(newGameButton, centerPanel.getParent(), startGameListener);
				}
			});
			
			JButton loadGameButton = new MenuButton("Load Game", new Dimension(250, 100));
			JButton logsButton = new MenuButton("Game Logs", new Dimension(250, 100));
			logsButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					menuPanel.setVisible(false);
					logPanel.setVisible(true);
					logPanel.updateLogButtons();
				}
			});
			
			
			gbc.weightx = 0.5; gbc.weighty = 2;
			gbc.gridx = 0; gbc.gridy = 0;
			centerPanel.add(Box.createGlue(), gbc);
			
			gbc.gridy = 2; gbc.weighty = 0.5;
			gbc.anchor = GridBagConstraints.PAGE_END;
			centerPanel.add(newGameButton, gbc);
			
			gbc.gridy = 3; gbc.weighty = 0.3; // space
			centerPanel.add(Box.createGlue(), gbc);
			
			gbc.gridx = 0; gbc.gridy = 4; gbc.weighty = 0.5;
			gbc.anchor = GridBagConstraints.CENTER;
			centerPanel.add(loadGameButton, gbc);
			
			gbc.gridy = 5; gbc.weighty = 0.3; // space
			centerPanel.add(Box.createGlue(), gbc);
			
			gbc.gridx = 0; gbc.gridy = 6; gbc.weighty = 0.5;
			gbc.anchor = GridBagConstraints.PAGE_START;
			centerPanel.add(logsButton, gbc);
			
			gbc.weighty = 2;
			gbc.gridy = 7;
			centerPanel.add(Box.createGlue(), gbc);
		}
	}
	
	/**
	 * The {@link GamePanel} class represents the panel where the actual gameplay occurs.
	 * It manages player actions, card interactions, and game loops.
	 */
	public class GamePanel {
		
		private static final long serialVersionUID = -8658665296862775000L;
		
		public Color wildColor;
		public EColor wildColorEnum = EColor.NONE;
		public JPanel panelNorth;
		
		private String gameName;
		
		private static ImageIcon arrow = new ImageIcon(new ImageIcon("Assets/right_facing_arrow.png").getImage().getScaledInstance(90, 40, Image.SCALE_DEFAULT));
		
		private MenuButton drawButton;
		private MenuButton unoButton;
		private GameSession gameSession;
		private Player humanPlayer;
		private GameLoopActionListener gameLoopListener;
		
		private boolean saidUno = false;
		private boolean isYourTurn = true;
		private boolean inForceDraw = false;
		private int numOfCardsDrawn = 0;
		
		ArrayList<PlayerLabel> playerLabels;
		ArrayList<CardButton> cardButtons;
		
		/**
	     * Inner class representing a player label. Serves as a template for a widely used component.
	     */
		public class PlayerLabel extends JLabel {
			
			private static final long serialVersionUID = -1206035377034500794L;
			
			private Border turnBorder = BorderFactory.createLineBorder(Color.red, 3);
			private Player player;

			/**
		     * Constructs a new player label.
		     * @param player The player associated with this label.
		     */
			public PlayerLabel(Player player) {
				super();
				this.player = player;
				setBackground(darkerPurple);
				setForeground(Color.white);
				setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
				setPreferredSize(new Dimension(100, 80));
				setOpaque(true);
				setHorizontalAlignment(SwingConstants.CENTER);
				String text = (player == gameSession.getHumanPlayer()) ? "You": ((AiPlayer)player).getBotName(); 
				setText(String.format("%s: %d", text, player.getDeck().size()));
			}
			
			/**
		     * Retrieves the player associated with this label.
		     * @return The player associated with this label.
		     */
			public Player getPlayer() {
				return player;
			}
			
			/**
		     * Updates the border of the label based on whether it's the player's turn.
		     * @param isTurn Indicates if it's the player's turn.
		     */
			private void updateBorder(boolean isTurn) {
				setBorder((isTurn) ? turnBorder : defaultBorder);
			}
			
			/**
		     * Updates the player labels based on the current player and their deck size.
		     * @param labels The list of player labels.
		     * @param currentPlayer The current player.
		     */
			public static void updatePlayerLabels(ArrayList<PlayerLabel> labels, Player currentPlayer) {
				for (PlayerLabel label : labels) {
					label.updateBorder(currentPlayer == label.getPlayer());
					Player player = label.getPlayer();
					String text = (player == player.getGameSession().getHumanPlayer()) ? "You": ((AiPlayer)player).getBotName(); 
					label.setText(String.format("%s: %d", text, player.getDeck().size()));
					label.repaint();
					label.revalidate();
				}
			}
			
			/**
		     * Updates the location of player labels on the panel.
		     * @param northPanel The panel containing player labels.
		     * @param labels The list of player labels.
		     * @param newPlayerList The updated list of players.
		     */
			public static void updatePlayerLabelLocations(JPanel northPanel, ArrayList<PlayerLabel> labels, List<Player> newPlayerList) {
				northPanel.removeAll();
				JLabel arrowLabel = new JLabel();
				arrowLabel.setIcon(arrow);
				arrowLabel.setPreferredSize(new Dimension(120, 90));
				arrowLabel.setText("DIRECTION OF PLAY");
				arrowLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
				arrowLabel.setForeground(Color.LIGHT_GRAY);
				arrowLabel.setVerticalTextPosition(JLabel.BOTTOM);
				arrowLabel.setHorizontalTextPosition(JLabel.CENTER);
				northPanel.add(arrowLabel);
				for (Player player : newPlayerList) {
					for (PlayerLabel label : labels) {
						if (label.getPlayer() == player) {
							northPanel.add(label);
						}
					}
				}
			}
		}
		
		/**
		 * Inner class representing a card button. Serves as a template for a widely used component.
		 */
		private class CardButton extends JButton {
			
			private static final long serialVersionUID = -6546407021475779805L;

			private Card card;
			
			public CardButton(Card card) {
				super(); this.card = card;
				setFocusable(false);
				setBackground(card.getColorEnum().getColor());
				setForeground(Color.white);
				setSize(100, 130);
				setPreferredSize(new Dimension(100, 100));
				int fontSize = 20;
				if (card.getAction() == Action.NUMBER) {
					int num = ((NumberCard)card).getNumberInt();
					setText(String.format("%d", num));
				} else {
					setText(card.getAction().toString());
					fontSize = 10;
				}
				
				setFont(new Font(Font.SANS_SERIF, Font.BOLD, fontSize));
			}
			
			public Card getCard() {
				return card;
			}
		}
		
		/**
		 * Inner class representing a selection popup.
		 */
		private class SelectionPopup extends JFrame {
			
			/**
		     * Constructs a new selection popup.
		     * @param confirmButton The button to confirm the selection.
		     * @param comboBox The combo box for selection options.
		     */
			public SelectionPopup(JButton confirmButton, JComboBox comboBox) {
				setLocationRelativeTo(null);
				setLayout(null);
				getContentPane().setBackground(defaultPurple);
				setResizable(false);
				setSize(200, 200);
				
				comboBox.setBounds(60, 20, 80, 30);
				comboBox.setBackground(darkerPurple);
				comboBox.setForeground(Color.white);
				
				confirmButton.setBounds(25, 100, 150, 30);
				confirmButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
				
				add(comboBox);
				add(confirmButton);
				
				setVisible(true);
			}
		}
		
		/**
		 * Inner class for handling card button actions.
		 */
		private class CardButtonListener implements ActionListener {

			private JPanel cardPanel;
			private JLabel topDeckLabel;
			private ArrayList<CardButton> buttons;
			private CardButton sourceButton;
			private GameSession gameSession;
			
			/**
		     * Constructs a new card button listener.
		     * @param cardPanel The panel containing the cards.
		     * @param topDeckLabel The label for the top card of the deck.
		     * @param buttons The list of card buttons.
		     * @param sourceButton The button representing the played card.
		     * @param gameSession The ongoing game session.
		     */
			public CardButtonListener(JPanel cardPanel, JLabel topDeckLabel, ArrayList<CardButton> buttons, CardButton sourceButton, GameSession gameSession) {
				this.cardPanel = cardPanel; this.topDeckLabel = topDeckLabel; this.buttons = buttons; this.sourceButton = sourceButton; this.gameSession = gameSession;
			}
			
			/**
		     * Performs the action when a card button is clicked.
		     * @param e The action event triggered by the button click.
		     */
			@Override
			public void actionPerformed(ActionEvent e) {
				playCard(e, cardPanel, topDeckLabel, sourceButton, gameSession);
			}
			
		}
		
		/**
		 * Inner class representing the game loop thread. Use this to perform the game loop while the gui keeps rendering in a separate thread.
		 */
		private class GameLoopThread extends Thread {
			
			private ActionEvent e;
			private GameSession gameSession;
			
			/**
		     * Constructs a new game loop thread.
		     * @param e The action event triggering the game loop.
		     * @param gameSession The ongoing game session.
		     */
			public GameLoopThread(ActionEvent e, GameSession gameSession) {
				super();
				this.e = e;
				this.gameSession = gameSession;
			}

			/**
		     * Sets the enabled status of card buttons.
		     * @param enabled Indicates if the buttons should be enabled.
		     */
			public void setButtonsEnabled(boolean enabled) {
				for (CardButton button : cardButtons) {
					button.setEnabled(enabled);
				}
			}
			
			/**
		     * Runs the game loop.
		     */
			@Override
			public void run() {
				isYourTurn = false;
				setButtonsEnabled(false);
				drawButton.setEnabled(false);
				gameLoopListener.actionPerformed(e);
				isYourTurn = !gameLoopListener.skippedPlayer;
				setButtonsEnabled(isYourTurn);
				drawButton.setEnabled(isYourTurn);
				if (!isYourTurn) run();
				PlayerLabel.updatePlayerLabels(playerLabels, humanPlayer);
				forceDrawCard(gameLoopListener.cardsToDraw);
				if (humanPlayer.getDeck().size() == 1 && !saidUno) {
					forceDrawCard(2);
				}
			}
			
		}
		/**
		 * 
		 * Method for handling both playing player's cards and updating the GUI
		 * 
		 * @param e action event related to card button
		 * @param cardPanel panel where cards are displayed
		 * @param topDeckLabel top deck label that displays the card on top of discard pile
		 * @param sourceButton card button that is associated with card
		 * @param gameSession game session object that is currently in play
		 */
		private void playCard(ActionEvent e, JPanel cardPanel, JLabel topDeckLabel, CardButton sourceButton, GameSession gameSession) {
			if(inForceDraw) return;
			if (!(gameSession.getCardTopDeck() instanceof WildCard)) wildColorEnum = EColor.NONE;
			gameSession.setWildColor(wildColorEnum);
			
			if (gameSession.getCardTopDeck() instanceof WildCard && sourceButton.getCard().getColorEnum().equals(wildColorEnum)) {}
			else if (!gameSession.cardIsPlayable(sourceButton.getCard())) return;
				
			if (sourceButton.getCard() instanceof WildCard) {
				String[] choices = {"Red", "Green", "Blue", "Yellow"};
				JComboBox<String> comboBox = new JComboBox<String>(choices);
				MenuButton confirmButton = new MenuButton("Confirm", new Dimension(100,100));
				SelectionPopup selectionPopup = new SelectionPopup(confirmButton, comboBox);
				confirmButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e1) {
						String choice = (String)comboBox.getSelectedItem();
						switch (choice) {
							case "Red": {
								wildColorEnum = EColor.RED;
								break;
							}
							case "Green": {
								wildColorEnum = EColor.GREEN;
								break;
							}
							case "Blue": {
								wildColorEnum = EColor.BLUE;
								break;
							}
							case "Yellow": {
								wildColorEnum = EColor.YELLOW;
								break;
							}
						}
						wildColor = wildColorEnum.getColor();
						gameSession.setWildColor(wildColorEnum);
						((WildCard)sourceButton.getCard()).setColorEnum(wildColorEnum);
						selectionPopup.setVisible(false);
						String logEntry = String.format("Player plays: %s with color: %s", sourceButton.getCard(), wildColorEnum.toString());
						logListener.updateGameLogEvent(logListener.new GameLogEvent(this, logEntry, gameName));
						initializeGameLoop(e, cardPanel, topDeckLabel, sourceButton, false);
					}
				});
				return;
			} else if (sourceButton.getCard().getAction() == Action.REVERSE) {
				List<Player> playerList = gameSession.getPlayerListGameOrder();
				Collections.reverse(playerList);
				PlayerLabel.updatePlayerLabelLocations(panelNorth, playerLabels, playerList);
				String logEntry = String.format("Player plays: %s", sourceButton.getCard());
				logListener.updateGameLogEvent(logListener.new GameLogEvent(this, logEntry, gameName));
				initializeGameLoop(e, cardPanel, topDeckLabel, sourceButton, true);
				return;
			}
			
			String logEntry = String.format("Player plays: %s", sourceButton.getCard());
			logListener.updateGameLogEvent(logListener.new GameLogEvent(this, logEntry, gameName));
			initializeGameLoop(e, cardPanel, topDeckLabel, sourceButton, false);
		}
		 /**
		  * 
		  * Method for handling common operations in all outcomes of playing a card
		  * 
		  * @param e action event related to card button
		  * @param cardPanel panel where cards are displayed
		  * @param topDeckLabel top deck label that displays the card on top of discard pile
		  * @param sourceButton card button that is associated with card
		  * @param startReversed boolean value for checking if the loop is going to be reversed
		  */
		private void initializeGameLoop(ActionEvent e, JPanel cardPanel, JLabel topDeckLabel, CardButton sourceButton, boolean startReversed) {
			gameLoopListener = new GameLoopActionListener(findThis(), gameSession, this, topDeckLabel, startReversed);
			GameLoopThread loopThread = new GameLoopThread(e, gameSession);
			humanPlayer.playCard(sourceButton.getCard());
			cardButtons.remove(sourceButton);
			cardPanel.remove(sourceButton);
			cardPanel.repaint();
			cardPanel.revalidate();
			PlayerLabel.updatePlayerLabels(playerLabels, humanPlayer);
			gameSession.setCardTopDeck(sourceButton.getCard());
			updateTopDeckLabel(topDeckLabel, gameSession);
			checkUno();
			if (humanPlayer.getDeck().size() == 0) {
				WinnerPanel winnerPanel = new WinnerPanel(this, humanPlayer);
				gamePanel.getParent().add(winnerPanel, BorderLayout.CENTER);
				gamePanel.setVisible(false);
				return;
			}
			loopThread.start();
		}
		 /**
		  * Method for player's draw card action and also for updating the GUI
		  * 
		  * @param cardPanel the panel where card buttons are displayed 
		  * @param topDeckLabel label for displaying the card currently on top of the discard pile
		  * @param gameSession game session which is currently in play
		  */
		private void drawCard(JPanel cardPanel, JLabel topDeckLabel, GameSession gameSession) {
			List<Card> drawPile = gameSession.getDrawPile();
			if (drawPile.size() == 0) gameSession.reshuffleDiscardPile();
			Card cardDrawn = drawPile.get(drawPile.size() - 1);
			numOfCardsDrawn++;
			humanPlayer.drawCard(cardDrawn);
			CardButton button = new CardButton(cardDrawn);
			button.addActionListener(new CardButtonListener(cardPanel, topDeckLabel, cardButtons, button, gameSession));
			cardButtons.add(button);
			checkUno();
			PlayerLabel.updatePlayerLabels(playerLabels, humanPlayer);
			cardPanel.add(button);
			cardPanel.repaint();
			cardPanel.revalidate();
			
			String logEntry = String.format("Player draws card: %s", cardDrawn);
			logListener.updateGameLogEvent(logListener.new GameLogEvent(this, logEntry, gameName));
		}
		
		/**
		 * 
		 * Method for updating the GUI elements of the panel which stores card buttons
		 * 
		 * @param cardPanel card panel which stores the card buttons
		 * @param topDeckLabel label for displaying the card currently on top of the discard pile
		 * @param playerCards arraylist containing the player's cards
		 * @param gameSession game session which is currently in play
		 */
		private void updateCardPanel(JPanel cardPanel, JLabel topDeckLabel, ArrayList<Card> playerCards, GameSession gameSession) {
			updateCardButtonList(cardPanel, topDeckLabel, playerCards, gameSession);
			for(CardButton button : cardButtons) {
				cardPanel.add(button);
			}
		}
		
		/**
		 * Updates the list of card buttons based on the player's cards.
		 * Clears the existing card buttons and creates new ones based on the sorted player cards.
		 * Attaches action listeners to each card button.
		 * @param cardPanel card panel which stores the card buttons
		 * @param topDeckLabel label for displaying the card currently on top of the discard pile
		 * @param playerCards arraylist containing the player's cards
		 * @param gameSession game session which is currently in play
		 */
		private void updateCardButtonList(JPanel cardPanel, JLabel topDeckLabel, ArrayList<Card> playerCards, GameSession gameSession) {
			cardButtons.clear();
			for (Card card : Decks.sortedDeck(playerCards)) {
				CardButton button = new CardButton(card);
				button.addActionListener(new CardButtonListener(cardPanel, topDeckLabel, cardButtons, button, gameSession));
				cardButtons.add(button);
			}
		}
		
		/**
		 * Updates the top deck label based on the top card of the deck.
		 * Sets the background color, text, and border of the label according to the top deck card.
		 * @param topDeckLabel The label displaying the top card of the deck.
		 * @param gameSession The ongoing game session.
		 */
		public void updateTopDeckLabel(JLabel topDeckLabel, GameSession gameSession) {
			Card topDeckCard = gameSession.getCardTopDeck();
			if (topDeckCard instanceof WildCard) {
				topDeckLabel.setBackground(wildColor);
			} else {
				topDeckLabel.setBackground(topDeckCard.getColorEnum().getColor());
			}
			topDeckLabel.setForeground(Color.white);
			topDeckLabel.setOpaque(true);
			if (topDeckCard.getAction().equals(Action.NUMBER)) {
				topDeckLabel.setText(String.format("%d", ((NumberCard)topDeckCard).getNumberInt()));
			} else {
				topDeckLabel.setText(topDeckCard.getAction().toString());
			}
			topDeckLabel.repaint();
			topDeckLabel.revalidate();
		}
		
		/**
		 * Checks if the player is able to call UNO and enables the UNO button accordingly.
		 */
		private void checkUno() {
			if (unoButton == null) return;
			
			if(gameSession.getHumanPlayer().getDeck().size() == 1 && isYourTurn) {
				unoButton.setEnabled(true);
			} else {
				unoButton.setEnabled(false);
			}			
		}
		
		/**
		 * Forces the player to draw a specified number of cards.
		 * Displays a red border around the draw button during the forced draw.
		 * @param numberOfCardsToDraw The number of cards to draw.
		 */
		private void forceDrawCard(int numberOfCardsToDraw) {
			numOfCardsDrawn = 0;
			Thread forceDrawThread = new Thread() {
				@Override
				public void run() {
					inForceDraw = true;
					unoButton.setEnabled(false);
					while (numOfCardsDrawn < numberOfCardsToDraw) {
						drawButton.setBorder(BorderFactory.createLineBorder(Color.red, 3));
					}
					inForceDraw = false;
					drawButton.setBorder(defaultBorder);
					checkUno();
				}
			};
			
			forceDrawThread.start();
		}
		
		/**
		 * Retrieves the name of the game.
		 * @return The name of the game.
		 */
		public String getGameName() {
			return gameName;
		}
		
		/**
		 * Retrieves the game panel.
		 * @return The game panel.
		 */
		public JPanel getPane() {
			return gamePanel;
		}

		/**
		 * Constructs a new game panel with the specified game name and player count.
		 * Initializes the game session, human player, card buttons, and player labels.
		 * Constructs and organizes the user interface components.
		 * @param gameName The name of the game.
		 * @param playerCount The number of players in the game.
		 */
		public GamePanel(String gameName, int playerCount) {
			
			this.gameName = gameName;
			gamePanel = new JPanel();
			
			System.out.println("Started game with " + playerCount);
			
			gameSession = new GameSession(gameName);
			gameSession.initializeGame(playerCount);
			humanPlayer = gameSession.getHumanPlayer();
			cardButtons = new ArrayList<CardButton>();
			playerLabels = new ArrayList<PlayerLabel>();
			JLabel topDeckLabel = new JLabel();
			
			gamePanel.setLayout(new BorderLayout());
			panelNorth = new JPanel();
			panelNorth.setBackground(defaultPurple);
			panelNorth.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel arrowLabel = new JLabel();
			arrowLabel.setIcon(arrow);
			arrowLabel.setPreferredSize(new Dimension(120, 90));
			arrowLabel.setText("DIRECTION OF PLAY");
			arrowLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
			arrowLabel.setForeground(Color.LIGHT_GRAY);
			arrowLabel.setVerticalTextPosition(JLabel.BOTTOM);
			arrowLabel.setHorizontalTextPosition(JLabel.CENTER);
			panelNorth.add(arrowLabel);
			for (Player player : gameSession.getPlayerList()) {
				PlayerLabel label = new PlayerLabel(player);
				playerLabels.add(label);
				panelNorth.add(label);
			}
			PlayerLabel.updatePlayerLabels(playerLabels, humanPlayer);
			
			JPanel panelSouth = new JPanel();
			panelSouth.setBackground(defaultPurple);
			panelSouth.setPreferredSize(new Dimension(1, 200));
			FlowLayout fl = new FlowLayout();
			panelSouth.setLayout(fl);
			
			topDeckLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
			topDeckLabel.setHorizontalAlignment(SwingConstants.CENTER);
			topDeckLabel.setBorder(defaultBorder);
			topDeckLabel.setPreferredSize(new Dimension(200, 260));
			updateTopDeckLabel(topDeckLabel, gameSession);
			updateCardPanel(panelSouth, topDeckLabel, Decks.sortedDeck(humanPlayer.getDeck()), gameSession);
			
			JPanel panelEast = new JPanel();
			panelEast.setBackground(defaultPurple);
			panelEast.setPreferredSize(new Dimension(200, 1));
			panelEast.setLayout(new GridBagLayout());
			GridBagConstraints gbc_panelEast = new GridBagConstraints();
			
			gbc_panelEast.weightx = 0.5; gbc_panelEast.weighty = 1;
			gbc_panelEast.gridx = 0; gbc_panelEast.gridy = 0;
			gbc_panelEast.anchor = GridBagConstraints.PAGE_END;
			
			unoButton = new MenuButton("UNO", new Dimension(150, 100));
			unoButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					saidUno = true;					
				}
			});
			panelEast.add(unoButton, gbc_panelEast);
			
			gbc_panelEast.weighty = 0.1;
			gbc_panelEast.gridy = 1;
			
			panelEast.add(Box.createGlue(), gbc_panelEast);
			
			gbc_panelEast.anchor = GridBagConstraints.PAGE_START;
			gbc_panelEast.weighty = 1;
			gbc_panelEast.gridy = 2;
			
			drawButton = new MenuButton("DRAW", new Dimension(150, 100));
			drawButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					drawCard(panelSouth, topDeckLabel, gameSession);					
				}
			});
			
			drawButton.addActionListener(gameLoopListener);
			panelEast.add(drawButton, gbc_panelEast);
			
			JPanel panelWest = new JPanel();
			panelWest.setBackground(defaultPurple);
			
			panelWest.setLayout(new GridBagLayout());
			
			JPanel panelCenter = new JPanel();
			panelCenter.setBackground(defaultPurple);
			panelCenter.setLayout(new GridBagLayout());
			GridBagConstraints gbc_center = new GridBagConstraints();
			
			gbc_center.weightx = 0.2; gbc_center.weighty = 1;
			gbc_center.gridx = 0; gbc_center.gridy = 0;
			panelCenter.add(Box.createGlue(), gbc_center);
			gbc_center.gridx = 1; gbc_center.weightx = 1;
			panelCenter.add(topDeckLabel, gbc_center);
			
			gamePanel.add(panelNorth, BorderLayout.NORTH);
			gamePanel.add(panelSouth, BorderLayout.SOUTH);
			gamePanel.add(panelEast, BorderLayout.EAST);
			gamePanel.add(panelWest, BorderLayout.WEST);
			gamePanel.add(panelCenter, BorderLayout.CENTER);
			
			checkUno();
			frame.add(gamePanel);
		}
	}
	
	private JFrame frame;
	
	/**
	 * Constructs a new game window.
	 * Initializes the main frame and adds menu options.
	 */
	public GameWindow() {
		startGameListener = new StartGameListener();
		frame = new JFrame();
		frame.setIconImage(unoLogo.getImage());
		frame.setBounds(100, 100, 1000, 850);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		frame.setMinimumSize(new Dimension(1500, 800));
		Container con = frame.getContentPane();
		
		menuPanel = new MenuPanel();
		menuPanel.setVisible(true);
		logPanel = new LogPanel();
		logPanel.setVisible(false);

		frame.getContentPane().add(logPanel, BorderLayout.CENTER);
		frame.getContentPane().add(menuPanel, BorderLayout.CENTER);
		
		JFrame frame2 = new JFrame();
		frame2.setSize(new Dimension(1300, 800));
		frame2.setIconImage(unoLogo.getImage());
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.add(logPanel);
		frame2.setVisible(true);
		
		frame.repaint();
		frame.revalidate();
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(darkerPurple);
		JMenu gameMenu = new JMenu("Game");
		gameMenu.setFocusable(false);
		gameMenu.setForeground(Color.white);
		
		JMenuItem exitItem = new JMenuItem("Exit Game");
		exitItem.setBackground(darkerPurple);
		exitItem.setForeground(Color.white);
		exitItem.setBorder(BorderFactory.createLineBorder(darkerPurple, 3));
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenuItem mainMenuItem = new JMenuItem("Main Menu");
		mainMenuItem.setBackground(darkerPurple);
		mainMenuItem.setForeground(Color.white);
		mainMenuItem.setBorder(BorderFactory.createLineBorder(darkerPurple, 3));
		mainMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (gamePanel != null) gamePanel.setVisible(false);
				menuPanel.setVisible(true);
				logPanel.setVisible(false);
				menuBar.remove(((JMenuItem)e.getSource()));
			}
		});
		gameMenu.add(exitItem);
		gameMenu.add(mainMenuItem);
		menuBar.add(gameMenu);
		
		frame.setJMenuBar(menuBar);
		frame.setVisible(true);
	}
}