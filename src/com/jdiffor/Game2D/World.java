package com.jdiffor.Game2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class World {

	private KeyboardInputManager keyboardManager;
	private Player player;
	private Map map;
	private DirectionManager directionManager;
	private Camera camera;
	private HotBar hotBar;
	private WinManager winManager;
	private LevelManager levelManager;
	
	private int loadingFrames;
	private int winDelayFrames = 100;
	
	public World(KeyboardInputManager keyboardmanager, Camera camera, HotBar hotBar, Dimension worldDimensionsInTiles) {
		this.keyboardManager = keyboardmanager;
		this.camera = camera;
		this.hotBar = hotBar;
		this.winManager = new WinManager();
		this.levelManager = new LevelManager(this, winManager);
	}
	
	public Map loadNewMap(String newMapName) {
		return this.loadNewMap(newMapName, "");
	}
	
	public Map loadNewMap(String newMapName, String oldMapName) {
		this.map = new Map(camera, newMapName, oldMapName);
		this.player = new Player(camera, hotBar, this.map.getPlayerSpawnX(), this.map.getPlayerSpawnY());
		camera.setPlayer(player);
		this.directionManager = new DirectionManager(player, keyboardManager);
		return this.map;
	}
	
	public void update() {
		if(this.loadingFrames > 0) {
			loadingFrames--;
			return;
		}
		
		// Move player
		String[] teleports = this.directionManager.update(map);
		
		// Interact
		if(this.keyboardManager.getKeyIsPressed('e')) {
			this.player.interact(this.map);
		}
		
		// Update offsets
		this.camera.updateCamera();
		
		// Teleport
		if(teleports != null && teleports[0] != null) {
			loadNewMap(teleports[0], this.map.getMapName());
			loadingFrames = 60;
			return;
		}
		
		// Trapdoor
		if(teleports != null && teleports[1] != null) {
			this.levelManager.setNextLevel();
			loadingFrames = 60;
			return;
		}
		
		// Check if map is complete
		if(this.map != null) {
			if(this.map.chestsCompleted()) {
				this.map.openTrapdoors();
			}
		}
	}
	
	public void draw(Graphics2D g) {
		// Arbitrary loading screen to make game feel more cohesive
		if(this.loadingFrames > 0) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, this.camera.getScreenWidth(), this.camera.getScreenHeight());
			g.setColor(Color.WHITE);
			g.setFont(new Font("Tahoma", Font.PLAIN, 30));
			g.drawString("Loading...", 20, 40);
			return;
		}
		
		ArrayList<Tile> afterPlayer = this.map.draw(g, player.getPlayerX(), player.getPlayerY(), camera.getScreenWidth(), camera.getScreenHeight());
		this.player.draw(g);
		for(Tile tile : afterPlayer) {
			tile.draw(g);
		}
		
		if(Main.DEBUG) {
			g.setColor(Color.RED);
			g.drawLine(this.camera.getScreenWidth()/2, 0, this.camera.getScreenWidth()/2, this.camera.getScreenHeight());
			g.drawLine(0, this.camera.getScreenHeight()/2, this.camera.getScreenWidth(), this.camera.getScreenHeight()/2);
		}
		
		if(this.winManager.isDone()) {
			if(winDelayFrames > 0) {
				winDelayFrames--;
			} else {
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, this.camera.getScreenWidth(), this.camera.getScreenHeight());
				g.setColor(Color.WHITE);
				g.setFont(new Font("Tahoma", Font.PLAIN, 30));
				g.drawString("You win!!!", 20, 40);
				g.drawString(this.winManager.secondsPlayed() + " seconds", 20, 80);
			}
		}
	}
	
	public void goFullScreen(Dimension fullScreenDim) {
		this.camera.updateScreenDimensions(fullScreenDim);
	}
	
}
