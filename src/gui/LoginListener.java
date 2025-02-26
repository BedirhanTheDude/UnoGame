package gui;

import java.awt.Component;
import java.io.File;
import java.nio.file.Paths;
import java.util.EventListener;
import java.util.Map;
import java.util.Map.Entry;

import exception.InvalidInputException;
import exception.NoSuchUserException;
import management.GameLogs;

/**
 * Listens for login events and performs corresponding actions such as logging in or registering.
 * <p>
 * This class implements the {@code EventListener} interface to listen for login events triggered by user actions.
 * It handles login and registration processes by interacting with the {@code GameLogs} class to validate user credentials.
 * </p>
 * <p>
 * The login action checks the entered username and password against the stored user data, while the registration action adds a new user to the system.
 * </p>
 * <p>
 * This listener class provides constants for identifying different event types: {@code LOGIN} and {@code REGISTER}.
 * </p>
 * 
 * @author Bedirhan Sakaoğlu
 */
public class LoginListener implements EventListener {
	
	public static final int LOGIN = 0;
	public static final int REGISTER = 1;
	public static File passwordFile = new File("Data/passwords.txt");
	
	/**
	 * Performs actions based on the login event type.
	 * <p>
	 * This method handles login or registration actions based on the event type provided.
	 * For login events it verifies the entered credentials against stored user data and logs the user in if successful.
	 * For registration events it adds a new user to the system.
	 * </p>
	 * 
	 * @param loginEvent the login event containing username and password information.
	 * @param eventType  the type of login event (LOGIN or REGISTER).
	 */
	public void loginActionPerformed(LoginEvent loginEvent, int eventType) {

		String username = loginEvent.getUsername();
		String password = loginEvent.getPassword();
		switch (eventType) {
			case (LOGIN): {
				try {
					if (username.equals("") || password.equals("")) throw 
					new InvalidInputException(String.format("%s:%s", username, password)); }
				catch (InvalidInputException e) {
					System.out.println(e.getMessage());
					return;
				}
				
				boolean loginSuccessful = false;
				boolean incorrectPassword = false;
				try {
					GameLogs.validateUsernameExistence(Paths.get("Data\\passwords.txt"), username);
				} catch (NoSuchUserException e1) {
					System.out.println(e1.getMessage());
					return;
				}
				
				Map<String, String> passwords = GameLogs.readPasswordFile(Paths.get("Data\\passwords.txt"));
				for (Entry<String, String> entry : passwords.entrySet()) {
					if (entry.getKey().equals(username) && entry.getValue().equals(password)) {
						loginSuccessful = true;
						break;
					} else if (entry.getKey().equals(username) && !entry.getValue().equals(password)) {
						incorrectPassword = true;
						break;
					}
				}
				
				if (loginSuccessful) {
					System.out.println("Logged in as: ".concat(username));
					Component source = ((Component) loginEvent.getSource());
					source.setVisible(false);
					new GameWindow();
					
				}
				else if (incorrectPassword) System.out.println("Incorrect password please try again.");
				break;
			}
			case (REGISTER): {
				try {
					GameLogs.registerPasswordToFile(Paths.get("Data\\passwords.txt"), username, password);
				} catch (InvalidInputException e) {
					System.out.println(e.getMessage());
				}
				break;
			}
			default:
				return;
		}
	}
}
