package management;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Scanner;

/**
 * A listener class for handling game log events and updating log files.
 * <p>
 * This class listens for game log events and updates the corresponding log file
 * with the provided log entry.
 * </p>
 * <p>
 * It maintains an entry ID to keep track of log entries within each game log file.
 * </p>
 *
 * @author Bedirhan Sakaoğlu
 */
public class GameLogListener implements EventListener {

	/**
     * An event object representing a game log entry.
     */
	public class GameLogEvent extends EventObject {

		private static final long serialVersionUID = 9206929019829918380L;
		private String entry;
		private String gameName;
		
		/**
         * Constructs a new GameLogEvent with the specified source, log entry, and game name.
         *
         * @param source   the object on which the event initially occurred.
         * @param entry    the log entry.
         * @param gameName the name of the game associated with the log entry.
         */
		public GameLogEvent(Object source, String entry, String gameName) {
			super(source);
			this.entry = entry;
			this.gameName = gameName;
		}	
		
		/**
         * Returns the log entry associated with this event.
         *
         * @return the log entry.
         */
		public String getEntry() {
			return entry;
		}
		
		/**
         * Returns the name of the game associated with this event.
         *
         * @return the game name.
         */
		public String getGameName() {
			return gameName;
		}
	}
	
	private int entryId = 0;
	
	/**
     * Updates the game log file with the provided event's log entry.
     *
     * @param event the game log event containing the log entry and game name.
     */
	public void updateGameLogEvent(GameLogEvent event) {
		String entry = event.getEntry();
		String gameName = event.getGameName();
		
		entryId = getEntryIdFromLog(gameName);		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("Data\\GameLogs\\" + gameName + ".txt", true));
			String log = String.format("%d. %s%n", ++entryId, entry);
			writer.append(log);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * Gets the last entry ID from the game log file.
     *
     * @param gameName the name of the game log file.
     * @return the last entry ID in the log file.
     */
	private int getEntryIdFromLog(String gameName) {
		File file = new File("Data\\GameLogs\\" + gameName + ".txt");
		try {
			Scanner input = new Scanner(file);
			while (input.hasNextLine()) {
				String line = input.nextLine();
				if (!input.hasNextLine()) {
					String[] array = line.split(". ");
					return Integer.valueOf(array[0]);
				}
			}
		} catch (FileNotFoundException e) {
			return 0;
		}
		return 0;
	}
}
