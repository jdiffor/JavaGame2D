package com.jdiffor.Game2D;
import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends Drawable {

	// CONSTANTS
	private static final int PLAYER_MAX_SPEED = 4 * GamePanel.SCALE;
	private static final int IMG_WIDTH = 32;
	private static final int IMG_HEIGHT = 32;
	private static final String IMG_NAME = AssetManager.characterImage("CharacterSpritesheet.png");
	
	// PLAYER
	private int playerX;
	private int playerY;
	private Vector playerSpeed;
	private int playerWidth;
	private int playerHeight;
	private HotBar hotBar;
	
	// SPRITE
	private AnimatedSpritesheet spritesheet;
		
	public Player(Camera camera, HotBar hotBar, int x, int y) {		
		super(camera, x, y, IMG_WIDTH*GamePanel.SCALE, IMG_HEIGHT*GamePanel.SCALE, IMG_NAME);
		initializePlayerHotBar(hotBar);
		this.playerX = x;
		this.playerY = y;
		this.playerWidth = IMG_WIDTH*GamePanel.SCALE;
		this.playerHeight = IMG_HEIGHT*GamePanel.SCALE;
		this.playerSpeed = new Vector();
		this.spritesheet = new AnimatedSpritesheet(IMG_NAME, 1, 2, IMG_WIDTH, IMG_HEIGHT, 30);
		this.updateSprite();
	}
	
	private void initializePlayerHotBar(HotBar hotBar) {
		this.hotBar = hotBar;
		
		// Initialize with a test item
		this.hotBar.addItem(new InventoryItem(AssetManager.inventoryItemImage("sword.png"), AssetManager.inventoryItemImage("sword.png")), 1);
	}
	
	public int getPlayerX() {
		return this.playerX;
	}
	
	public int getPlayerY() {
		return this.playerY;
	}
	
	public int getLeftEdge() {
		return playerX;
	}
	
	public int getRightEdge() {
		return playerX + playerWidth;
	}
	
	public int getTopEdge() {
		return playerY;
	}
	
	public int getBottomEdge() {
		return playerY + playerHeight;
	}
	
	public int getCenterX() {
		return playerX + (playerWidth / 2);
	}
	
	public int getCenterY() {
		return playerY + (playerHeight / 2);
	}
	
	public Vector getPlayerXY() {
		return new Vector(this.playerX, this.playerY);
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
		
		if(this.hotBar.getSelectedItem() != null) {
			// TODO: attach to player. Currently hardcoded where player hands are
			//this.hotBar.getSelectedItem().drawPlayerItem(g, this.camera.getXOffset() + getCenterX()-8, this.camera.getYOffset() + getCenterY()-30, 48, 48);
		}
		
		if(Main.DEBUG) {
			// Hit box highlighting
			g.setColor(Color.ORANGE);
			g.drawRect(this.getScreenX(), this.getScreenY(), this.playerWidth, this.playerHeight);
		}
		
		updateSprite();
	}
	
	private void updateSprite() {
		super.setImage(spritesheet.getNextImage());
	}
	
	/*
	 * Moves player, and returns a string of the new map to teleport to if the player reaches a teleporter
	 */
	public String move(Direction d, Map ground) {
		recalculateSpeed(d);
		
		adjustPlayerCoordinates(ground, d);
		
		/* FLIPPING HORIZONTALLY
		if(d == Direction.LEFT) {
			super.flip(true);
		} else if(d == Direction.RIGHT) {
			super.flip(false);
		}
		*/
		
		return checkForTeleport(ground);
	}
	
	public void interact(Map map) {
		if(this.hotBar.getSelectedItem() == null) {
			tryPickUp(map);
		} else {
			tryDeposit(map);
		}
	}
	
	public void tryPickUp(Map map) {
		Tile[] surrounding = map.getSurrounding(getCenterX(), getCenterY());
		for(Tile tile : surrounding) {
			InventoryItem item = tile.removeItem();
			if(item != null) {
				this.hotBar.addItem(item, this.hotBar.getSelectedInventoryItemSlot());
				break;
			}
		}
	}
	
	public void tryDeposit(Map map) {
		InventoryItem item = this.hotBar.getSelectedItem();
		Tile[] surrounding = map.getSurrounding(getCenterX(), getCenterY());
		for(Tile tile : surrounding) {
			boolean success = tile.depositToChest(item, map);
			if(success) {
				this.hotBar.removeSelectedItem();
				break;
			}
		}
	}
	
	/*
	 * Moves player if possible
	 */
	private void adjustPlayerCoordinates(Map ground, Direction d) {		
		Vector playerPos = adjustForCollisions(ground, d);
		this.playerX = playerPos.getX();
		this.playerY = playerPos.getY();
		
		super.x = this.playerX;
		super.y = this.playerY;
	}
	
	/*
	 * Changes players coordinates if no collision, keeps them the same if collision in direction
	 */
	private Vector adjustForCollisions(Map ground, Direction d) {
		
		int playerLeft = this.playerX + playerSpeed.getX();
		int playerRight = playerLeft + this.playerWidth - (PLAYER_MAX_SPEED - 1);
		int playerTop = this.playerY + playerSpeed.getY();
		int playerBottom = playerTop + this.playerHeight - (PLAYER_MAX_SPEED - 1);
		
		boolean hitX = false;
		boolean hitY = false;
		
		if(d == Direction.RIGHT) {
			Tile thisTile = ground.getTileAtCoordinates(playerRight, playerTop);
			if(thisTile != null && thisTile.canCollide()) {
				hitX = true;
			}
			thisTile = ground.getTileAtCoordinates(playerRight, playerBottom);
			if(thisTile != null && thisTile.canCollide()) {
				hitX = true;
			}
			
		} else if(d == Direction.LEFT) {
			Tile thisTile = ground.getTileAtCoordinates(playerLeft, playerTop);
			if(thisTile != null && thisTile.canCollide()) {
				hitX = true;
			}
			thisTile = ground.getTileAtCoordinates(playerLeft, playerBottom);
			if(thisTile != null && thisTile.canCollide()) {
				hitX = true;
			}
			
		} else if(d == Direction.UP) {
			Tile thisTile = ground.getTileAtCoordinates(playerLeft, playerTop);
			if(thisTile != null && thisTile.canCollide()) {
				hitY = true;
			}
			thisTile = ground.getTileAtCoordinates(playerRight, playerTop);
			if(thisTile != null && thisTile.canCollide()) {
				hitY = true;
			}
			
		} else if(d == Direction.DOWN) {
			Tile thisTile = ground.getTileAtCoordinates(playerLeft, playerBottom);
			if(thisTile != null && thisTile.canCollide()) {
				hitY = true;
			}
			thisTile = ground.getTileAtCoordinates(playerRight, playerBottom);
			if(thisTile != null && thisTile.canCollide()) {
				hitY = true;
			}
		}
		
		
		return new Vector(hitX ? this.playerX : playerLeft, hitY ? this.playerY : playerTop);
	}
	
	/*
	 * Returns map name to teleport to if player overlaps with a teleporter
	 */
	private String checkForTeleport(Map ground) {
		int playerLeft = this.playerX;
		int playerRight = playerLeft + this.playerWidth - 1;
		int playerTop = this.playerY;
		int playerBottom = playerTop + this.playerHeight - 1;
		
		// Upper-right corner
		Tile thisTile = ground.getTileAtCoordinates(playerRight, playerTop);
		if(thisTile != null && thisTile.getTeleport() != null) {
			return thisTile.getTeleport();
		}
		
		// Bottom-right corner
		thisTile = ground.getTileAtCoordinates(playerRight, playerBottom);
		if(thisTile != null && thisTile.getTeleport() != null) {
			return thisTile.getTeleport();
		}
		
		// Top-left corner
		thisTile = ground.getTileAtCoordinates(playerLeft, playerTop);
		if(thisTile != null && thisTile.getTeleport() != null) {
			return thisTile.getTeleport();
		}
		
		// Bottom-left corner
		thisTile = ground.getTileAtCoordinates(playerLeft, playerBottom);
		if(thisTile != null && thisTile.getTeleport() != null) {
			return thisTile.getTeleport();
		}
		
		return null;
	}
	
	/*
	 * Sets player speed to max speed in given direction 
	 */
	private void recalculateSpeed(Direction d) {
		playerSpeed = d.vector.getMultiplied(PLAYER_MAX_SPEED);
	}
	
	public void exhaustExtraMovement() {
		this.playerSpeed.clear();
	}
}
