package com.jdiffor.Game2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {
	
	// CONSTANTS
	public static final int FPS = 60;
	private static final int TILE_SIZE = 32;
	public static final int SCALE = 3;
	public static final int SCALED_TILE_SIZE = TILE_SIZE * SCALE;

	// THREAD
	private Thread gameThread;
	
	// INPUTS
	private KeyboardInputManager keyboardManager;
	private ScrollWheelInputManager scrollWheelManager;
	
	//CAMERA
	private Camera camera;
	
	// WORLD
	private World world;
	
	//GUIs
	private HotBarGUI hotBarGUI;
	
	
	public GamePanel(int screenWidth, int screenHeight) {
		// JPanel properties
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		
		// Initialize game properties
		Dimension worldDimensionsInTiles = new Dimension(100,50);
		Dimension worldDimensionsInPixels = new Dimension(worldDimensionsInTiles.width * SCALED_TILE_SIZE, worldDimensionsInTiles.height * GamePanel.SCALED_TILE_SIZE);
		this.camera = new Camera(new Dimension(screenWidth, screenHeight), worldDimensionsInPixels, true);
		this.hotBarGUI = new HotBarGUI(this.camera);
		this.keyboardManager = new KeyboardInputManager(this.hotBarGUI);
		this.scrollWheelManager = new ScrollWheelInputManager(this.hotBarGUI.getHotbar());
		this.world = new World(keyboardManager, camera, hotBarGUI.getHotbar(), worldDimensionsInTiles);
		
		// More JPanel properties
		this.addKeyListener(keyboardManager);
		this.addMouseWheelListener(scrollWheelManager);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	/*
	 * Main game loop
	 * Uses time passed to determine if we should update/repaint based on FPS
	 */
	public void run() {
		int frameInterval = 1000000000 / FPS;
		long nextTime = Utils.time() + frameInterval;
		
		while(gameThread != null) {
			
			long thisTime = Utils.time();
			
			if(thisTime > nextTime) {
				update();
				repaint();
				nextTime = thisTime + frameInterval;
			}
		}
	}
	
	public void update() {
		world.update();
	}
	
	public void goFullScreen(Dimension fullScreenDim) {
		world.goFullScreen(fullScreenDim);
	}
	
	/*
	 * All graphics originate from here
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		
		this.world.draw(graphics);
		this.hotBarGUI.draw(graphics);
		
		g.dispose();
	}	
}
