package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Represents the login window of the UNO game.
 * <p>
 * The login window allows users to enter their username and password to log in or register for a new account.
 * </p>
 * <p>
 * This class contains GUI components such as text fields, buttons, and check boxes for user interaction.
 * </p>
 * <p>
 * The window also includes functionality to toggle password visibility and to handle login and registration actions.
 * </p>
 * 
 * @author [Your Name]
 * @version 1.0
 * @since 1.0
 */
public class LoginWindow {
	
	private class CustomJLabel extends JLabel {

		private static final long serialVersionUID = -407967067884339452L;
		
		/**
		 * Constructs a custom JLabel with specified text and bounds.
		 *
		 * @param text      the text to display on the label.
		 * @param rectangle the bounds of the label.
		 */
		public CustomJLabel(String text, Rectangle rectangle) {
			super(text);
			this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
			this.setBounds(rectangle);
			this.setForeground(Color.white);
		}
		
		/**
		 * Constructs a custom JLabel with specified bounds.
		 *
		 * @param rectangle the bounds of the label.
		 */
		public CustomJLabel(Rectangle rectangle) {
			super();
			this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
			this.setBounds(rectangle);
			this.setForeground(Color.white);
		}
	}
	
	private static ImageIcon closedEyeIcon = new ImageIcon(new ImageIcon("Assets/closed_eye.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	private static ImageIcon openEyeIcon = new ImageIcon(new ImageIcon("Assets/open_eye.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	private static ImageIcon unoLogo = new ImageIcon("Assets\\logo.png");
	
	LoginListener loginListener;
	
	JFrame frame;
	JCheckBox showPasswordBox;
	JTextField usernameField;
	JPasswordField passwordField;
	JButton loginButton;
	JButton registerButton;
	
	/**
	 * Constructs a new login window for the UNO game.
	 */
	public LoginWindow() {
		
		loginListener = new LoginListener();

		frame = new JFrame();
		frame.setTitle("UNO Game");
		frame.setIconImage(unoLogo.getImage());
		frame.setResizable(false);
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		int loginPanelXBasis = 20;
		int loginPanelYBasis = 170;
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(new Color(80, 60, 125));
		loginPanel.setBounds(386, 0, 400, 463);
		loginPanel.setBorder(BorderFactory.createLineBorder(Color.orange, 3));
		frame.getContentPane().add(loginPanel);
		loginPanel.setLayout(null);
		
		JPanel imagePanel = new JPanel();
		
		CustomJLabel usernameLabel = new CustomJLabel("Username: ", new Rectangle(loginPanelXBasis, loginPanelYBasis, 100, 30)); loginPanel.add(usernameLabel);
		CustomJLabel passwordLabel = new CustomJLabel("Password: ", new Rectangle(loginPanelXBasis, loginPanelYBasis + 40, 100, 30)); loginPanel.add(passwordLabel);
		
		usernameField = new JTextField();
		usernameField.setBounds(loginPanelXBasis + 70, loginPanelYBasis, 180, 30);
		usernameField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
		usernameField.setBorder(BorderFactory.createLineBorder(Color.orange, 3));
		usernameField.setBackground(Color.DARK_GRAY);
		usernameField.setForeground(Color.white);
		loginPanel.add(usernameField);
		
		passwordField = new JPasswordField();
		//passwordField.setEchoChar('*');
		passwordField.setBounds(loginPanelXBasis + 70, loginPanelYBasis + 40, 180, 30);
		passwordField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
		passwordField.setBackground(Color.DARK_GRAY);
		passwordField.setForeground(Color.white);
		char originalCharacter = passwordField.getEchoChar();
		passwordField.setBorder(BorderFactory.createLineBorder(Color.orange, 3));
		loginPanel.add(passwordField);
		
		showPasswordBox = new JCheckBox(closedEyeIcon);
		showPasswordBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (showPasswordBox.isSelected()) {
					passwordField.setEchoChar((char)0);
					showPasswordBox.setIcon(openEyeIcon);
				}
				else {
					passwordField.setEchoChar(originalCharacter);
					showPasswordBox.setIcon(closedEyeIcon);
				}
			}
		});
		showPasswordBox.setBounds(loginPanelXBasis + 255, loginPanelYBasis + 42, 25, 25);
		showPasswordBox.setBackground(new Color(80, 60, 125));
		loginPanel.add(showPasswordBox);
		
		loginButton = new JButton("Log In");
		loginButton.setBounds(loginPanelXBasis + 70, loginPanelYBasis + 80, 80, 30);
		loginButton.setFocusable(false);
		loginButton.setBorder(BorderFactory.createLineBorder(Color.orange, 2));
		loginButton.setBackground(Color.DARK_GRAY);
		loginButton.setForeground(Color.white);
		loginButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginListener.loginActionPerformed(new LoginEvent(frame, usernameField.getText(), String.valueOf(passwordField.getPassword())), LoginListener.LOGIN);				
			}
		});
		loginPanel.add(loginButton);
		
		registerButton = new JButton("Register");
		registerButton.setBounds(loginPanelXBasis + 160, loginPanelYBasis + 80, 80, 30);
		registerButton.setFocusable(false);
		registerButton.setBorder(BorderFactory.createLineBorder(Color.orange, 2));
		registerButton.setBackground(Color.DARK_GRAY);
		registerButton.setForeground(Color.white);
		registerButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginListener.loginActionPerformed(new LoginEvent(frame, usernameField.getText(), String.valueOf(passwordField.getPassword())), LoginListener.REGISTER);				
			}
		});
		loginPanel.add(registerButton);

		frame.setVisible(true);
	}
}
