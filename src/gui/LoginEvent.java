package gui;

import java.util.EventObject;

/**
 * Represents a login event.
 * <p>
 * This class extends the {@code EventObject} class to represent a login event triggered by user interaction.
 * It contains the username and password entered by the user during the login process.
 * </p>
 * 
 * @author Bedirhan Sakaoğlu
 */
public class LoginEvent extends EventObject {

	private static final long serialVersionUID = -5169986359531025581L;
	private String username;
	private String password;
	
	/**
	 * Constructs a new login event with the specified source, username, and password.
	 * 
	 * @param source   the object on which the event initially occurred.
	 * @param username the username entered during the login process.
	 * @param password the password entered during the login process.
	 */
	public LoginEvent(Object source, String username, String password) {
		super(source);
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Gets the username from the login event.
	 * 
	 * @return the username entered during the login process.
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Gets the password from the login event.
	 * 
	 * @return the password entered during the login process.
	 */
	public String getPassword() {
		return password;
	}
}
