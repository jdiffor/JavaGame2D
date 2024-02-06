package com.jdiffor.Game2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tile extends Drawable {

	private boolean isCollisionable;
	private boolean isInFrontOfPlayer; // Should be drawn on top of player (is closer to camera than player)
	private String teleportToMap;
	private AnimatedSpritesheet spritesheet;
	private Chest chest;
	private InventoryItem item;
	
	public Tile(Camera camera, int x, int y, int size, Color c) {
		super(camera, x, y, size, c);
	}
	
	public Tile(Camera camera, int x, int y, int size, String teleportToMap, String imageName) {
		this(camera, x, y, size, teleportToMap, imageName, false, 0);
	}
	
	public Tile(Camera camera, int x, int y, int size, String teleportToMap, BufferedImage image) {
		super(camera, x, y, size, image);
		this.teleportToMap = teleportToMap;
	}
	
	public Tile(Camera camera, int x, int y, int size, String teleportToMap, String imageName, boolean isAnimated, int startingImage) {
		super(camera, x, y, size, imageName);
		this.teleportToMap = teleportToMap;
		if(isAnimated) {
			this.spritesheet = new AnimatedSpritesheet(imageName, 1, 2, Utils.SINGLE_UNIT, Utils.SINGLE_UNIT, 50, startingImage);
		}
	}
	
	public Tile collisionable(boolean canCollide) {
		this.isCollisionable = canCollide;
		return this;
	}
	
	public Tile inFrontOfPlayer(boolean isInFront) {
		this.isInFrontOfPlayer = isInFront;
		return this;
	}
	
	public void addChest(String chestColor, ArrayList<Chest> chests) {
		if(chestColor != null) {
			this.chest = new Chest(chestColor);
			chests.add(this.chest);
		}
	}
	
	public void addItem(String itemColor) {
		if(itemColor != null) {
			this.item = new InventoryItem(itemColor);
		}
	}
	
	/*
	 * Returns whether item was deposited
	 */
	public boolean depositToChest(InventoryItem item, Map map) {
		if(this.chest != null) {
			return this.chest.deposit(item, map);
		}
		return false;
	}
	
	/*
	 * Returns item and sets item to null
	 * If item was already null, returns null
	 */
	public InventoryItem removeItem() {
		InventoryItem temp = this.item;
		this.item = null;
		return temp;
	}
	
	public void draw(Graphics2D g) {
		this.draw(g, false);
	}
	
	public void draw(Graphics2D g, boolean blockingPlayer) {
		super.draw(g, blockingPlayer);
		if(this.isCollisionable) {
			
			if(Main.DEBUG) {
				g.setColor(Color.ORANGE);
				g.drawRect(this.getScreenX(), this.getScreenY(), width - 1, height - 1);
			}
			
			if(spritesheet != null) {
				this.updateSprite();
			}
		}
		
		if(this.chest != null) {
			this.chest.draw(g, this.getScreenX(), this.getScreenY(), Utils.SINGLE_UNIT*GamePanel.SCALE, Utils.SINGLE_UNIT*GamePanel.SCALE);
			
			if(Main.DEBUG) {
				g.setColor(new Color(69, 43, 37));
				g.drawRect(this.getScreenX(), this.getScreenY(), width - 1, height - 1);
			}
		}
		
		if(this.item != null) {
			this.item.drawPlayerItem(g, this.getScreenX(), this.getScreenY(), Utils.SINGLE_UNIT*GamePanel.SCALE, Utils.SINGLE_UNIT*GamePanel.SCALE);
			if(Main.DEBUG) {
				g.setColor(Color.CYAN);
				g.fillRect(this.getScreenX(), this.getScreenY(), width - 1, height - 1);
			}
		}
		
		if(Main.DEBUG && this.teleportToMap != null) {
			g.setColor(Color.PINK);
			g.drawRect(this.getScreenX(), this.getScreenY(), width - 1, height - 1);
			g.drawOval(this.getScreenX(), this.getScreenY(), width - 1, height - 1);
		}
	}
	
	public void updateSprite() {
		super.setImage(this.spritesheet.getNextImage());
	}
	
	public boolean canCollide() {
		return this.isCollisionable;
	}
	
	public boolean isInFrontOfPlayer() {
		return this.isInFrontOfPlayer;
	}
	
	public String getTeleport() {
		return this.teleportToMap;
	}
	
}
