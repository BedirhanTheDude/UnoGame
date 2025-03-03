package main;

import gui.LoginWindow;
import gui.GameWindow;

public class Main {
	public static void main(String[] args) {
		Runnable app = new Runnable() {

			@Override
			public void run() {
				new LoginWindow();
			}
		};
		
		app.run();
	}
}
