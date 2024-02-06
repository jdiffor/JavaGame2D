package com.jdiffor.Game2D;
public class Main {

	public static boolean DEBUG = false;
	public static String GAME_TITLE = "My game";
	
	/*
	 *  Entry point for game
	 *  Initializes the game window
	 */
	public static void main(String[] args) {
		GameWindow gameFrame = new GameWindow(GAME_TITLE);
		gameFrame.showWindow();
		gameFrame.goFullScreen();
		gameFrame.startGameThread();
	}

}
