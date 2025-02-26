package management;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import exception.InvalidInputException;
import exception.NoSuchUserException;

/**
 * Provides methods for reading and writing user credentials to a password file,
 * as well as checking the existence of a username.
 * <p>
 * This class handles user registration by managing a password file containing
 * username, password pairs and validating the existence of usernames.
 * </p>
 *
 * @author Bedirhan Sakaoğlu
 */
public class GameLogs {
	
	/**
     * Reads a password file and returns a map containing username, password pairs.
     *
     * @param filePath the path to the password file.
     * @return a map containing username, password pairs read from the file.
     */
	public static Map<String, String> readPasswordFile(Path filePath) {
		File file = new File(filePath.toString());
		Scanner input;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		HashMap<String, String> passwords = new HashMap<String, String>();
		while(input.hasNextLine()) {
			String line = input.nextLine();
			String userArray[] = line.split(":");
			passwords.put(userArray[0], userArray[1]);
		} input.close();
		return passwords;
	}
	
	/**
     * Registers a new user with the specified username and password in the password file.
     *
     * @param filePath the path to the password file.
     * @param username the username to register.
     * @param password the password associated with the username.
     * @throws InvalidInputException if the username or password is empty.
     */
	public static void registerPasswordToFile(Path filePath, String username, String password) throws InvalidInputException {
		try {
			if(validateUsernameExistence(filePath, username)) System.out.println("User already exists"); return;
		} catch (NoSuchUserException e) {}
		
		if (username.equals("") || password.equals("")) throw 
			new InvalidInputException(String.format("%s:%s", username, password));
			
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toString(), true));
			String logFormat = String.format("%s:%s%n", username, password);
			writer.append(logFormat);
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
	}
	
	/**
     * Validates the existence of a username in the password file.
     *
     * @param filePath the path to the password file.
     * @param username the username to validate.
     * @return true if the username exists in the file, otherwise false.
     * @throws NoSuchUserException if the username does not exist in the file.
     */
	public static boolean validateUsernameExistence(Path filePath, String username) throws NoSuchUserException{
		Map<String, String> passwords = readPasswordFile(filePath);
		for (String username2 : passwords.keySet()) {
			if (username2.equals(username)) return true;
		} 
		throw new NoSuchUserException(username);
	}
}
