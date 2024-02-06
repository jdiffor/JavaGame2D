package com.jdiffor.Game2D;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	
	private int SCREEN_WIDTH = 1280;
	private int SCREEN_HEIGHT = 720;
	private GamePanel gamePanel;
	
	public GameWindow(String gameTitle) {
		super();
		
		// Frame properties
		this.setTitle(gameTitle);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    this.setUndecorated(true);

	    gamePanel = new GamePanel(this.getWidth(), this.getHeight());
	    this.add(gamePanel);
	    this.removeCursor();
	}
	
	public void startGameThread() {
		gamePanel.startGameThread();
	}
	
	public void showWindow() {
		this.setVisible(true);
	}
	
	public void goFullScreen() {
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.gamePanel.goFullScreen(new Dimension(this.getWidth(), this.getHeight()));
	}
	
	public void removeCursor() {
		
		BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		this.getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "empty cursor"));
	}
}
