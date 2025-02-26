package gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;

import gui.GameWindow.GamePanel;
import gui.GameWindow.GamePanel.PlayerLabel;
import gui.GameWindow.WinnerPanel;
import management.AiPlayer;
import management.GameLogListener;
import management.GameSession;
import management.Player;
import uno.Action;
import uno.ActionCard;
import uno.Card;
import uno.WildCard;

/**
 * ActionListener implementation for managing the game loop events.
 * Responsible for handling actions triggered during the game loop, such as card button clicks.
 */
public class GameLoopActionListener implements ActionListener {

	private GameLogListener logListener = new GameLogListener();
	
	private GameSession gameSession;
	private GamePanel gamePanel;
	private JLabel topDeckLabel;
	private GameWindow gameWindow;
	private boolean startReversed;

	public boolean skippedPlayer = false;
	public int cardsToDraw = 0;
	
	/**
     * Constructs a new GameLoopActionListener instance.
     * @param gameWindow The main game window.
     * @param gameSession The ongoing game session.
     * @param gamePanel The game panel containing UI components.
     * @param topDeckLabel The label displaying the top card of the deck.
     * @param startReversed Indicates whether the game starts in reverse order.
     */
	public GameLoopActionListener(GameWindow gameWindow, GameSession gameSession, GamePanel gamePanel, JLabel topDeckLabel, boolean startReversed) {
		super();
		this.gameSession = gameSession;
		this.gamePanel = gamePanel;
		this.topDeckLabel = topDeckLabel;
		this.startReversed = startReversed;
		this.gameWindow = gameWindow;
	}

	/**
     * Handles action events triggered during the game loop.
     * @param e The ActionEvent triggered by the user.
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().getClass().getSimpleName().equals("CardButton")) {
			Player winnerBot = performGameLoop();
			if (winnerBot != null) {
				Container mainFrame = gamePanel.getPane().getParent();
				gamePanel.getPane().setVisible(false);
				mainFrame.remove(gamePanel.getPane());
				mainFrame.add(gameWindow.new WinnerPanel(gamePanel, winnerBot));
				mainFrame.repaint();
				mainFrame.revalidate();
			}
		}
	}
	
	/**
     * Performs the main game loop logic.
     * Iterates through players, allowing them to play their turns.
     * Checks for special actions and updates the game state accordingly.
     * @return The winning player if the game ends, otherwise null.
     */
	@SuppressWarnings("unchecked")
	private Player performGameLoop() {
		ArrayList<Player> playerList = (ArrayList<Player>) ((ArrayList<Player>) gameSession.getPlayerListGameOrder()).clone();

		Player humanPlayer = gameSession.getHumanPlayer();
		playerList.remove(humanPlayer);
		
		int loopLength = playerList.size(); int i = 0;
		int turnEndType = 1;
		
		boolean usedSkipBeforeEnd = false;
		
		while (i < loopLength && i >= 0) {
			Card cardLastPlayed = gameSession.getCardTopDeck();
			if (cardLastPlayed instanceof ActionCard) {
				if (cardLastPlayed.getAction() == Action.SKIP && !skippedPlayer) {
					i += 1;
					if (i >= loopLength || i < 0) return null;
				}
			}
			Player player = playerList.get(i);
			PlayerLabel.updatePlayerLabels(gamePanel.playerLabels, player);
			
			if (cardLastPlayed instanceof ActionCard || cardLastPlayed instanceof WildCard) {
				int returnDraw = 1;
				switch (cardLastPlayed.getAction()) {
					case DRAWTWO: {
						returnDraw = ((AiPlayer)player).forcedDrawCard(2);
						break;
					}
					case WILDFOUR: {
						returnDraw = ((AiPlayer)player).forcedDrawCard(4);
						break;
					}
					case REVERSE: {
						if (startReversed) break;
						gameSession.reverse();
						playerList = (ArrayList<Player>) ((ArrayList<Player>) gameSession.getPlayerListGameOrder()).clone();
						PlayerLabel.updatePlayerLabelLocations(gamePanel.panelNorth, gamePanel.playerLabels, playerList);
						PlayerLabel.updatePlayerLabels(gamePanel.playerLabels, player);
						playerList.remove(humanPlayer);
						i = playerList.size() - i;
						break;
					}
				}
				if (i >= loopLength || i < 0) break;
				if (returnDraw == 3) {
					gameSession.reshuffleDiscardPile();
				}
			}
			
			turnEndType = ((AiPlayer)player).playRandom();
			if (turnEndType == 2) {
				gamePanel.wildColorEnum = gameSession.getCardTopDeck().getColorEnum();
				gamePanel.wildColor = gamePanel.wildColorEnum.getColor();
			} else if (turnEndType == 3) {
				gameSession.reshuffleDiscardPile();
			} else if (turnEndType == 4) {
				usedSkipBeforeEnd = (i != loopLength - 1);
			}
			
			i += (turnEndType != 0) ? 1 : 0;
			
			long sleepDuration = (turnEndType != 0) ? 1000 : 200;
			try {
				Thread.sleep(sleepDuration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			gamePanel.updateTopDeckLabel(topDeckLabel, gameSession);
			System.out.println(gameSession.getDrawPile().size());
			if (player.getDeck().size() == 1) {
				String logEntry = String.format("%s says: UNO!", ((AiPlayer)player).getBotName());
				logListener.updateGameLogEvent(logListener.new GameLogEvent(this, logEntry, gamePanel.getGameName()));
			} else if (player.getDeck().size() == 0) {
				String logEntry = String.format("%s wins the game", ((AiPlayer)player).getBotName());
				logListener.updateGameLogEvent(logListener.new GameLogEvent(this, logEntry, gamePanel.getGameName()));
				return player;
			}
		}

		skippedPlayer = gameSession.getCardTopDeck().getAction() == Action.SKIP && !usedSkipBeforeEnd;
		int draw = (gameSession.getCardTopDeck().getAction() == Action.DRAWTWO) ? 2 : 4;
		cardsToDraw = (gameSession.getCardTopDeck().getAction() == Action.DRAWTWO || gameSession.getCardTopDeck().getAction() == Action.WILDFOUR) ?
				draw : 0;
		return null;
	}
	
}
