package com.jdiffor.Game2D;
import java.awt.Dimension;

public class Camera {

	private int screenWidth;
	private int screenHeight;
	private int worldWidthInPixels;
	private int worldHeightInPixels;
	private Player player;
	private boolean playerMustStayAtCenter;
	private int xOffset;
	private int yOffset;
	
	/*
	 * Camera class is used to calculate offsets when deciding where to draw assets on screen
	 */
	public Camera(Dimension screenDim, Dimension worldDim) {
		this(screenDim, worldDim, false);
	}
	
	public Camera(Dimension screenDim, Dimension worldDim, boolean playerMustStayAtCenter) {
		this.screenWidth = screenDim.width;
		this.screenHeight = screenDim.height;
		this.worldWidthInPixels = worldDim.width;
		this.worldHeightInPixels = worldDim.height;
		this.playerMustStayAtCenter = playerMustStayAtCenter;
	}
	
	/*
	 * Initializes reference to the player.
	 * Can't be included in the constructor because:
	 *  - player references camera AND
	 *  - camera references player
	 * One had to be made first, so the camera gets created, then the player with a camera reference, then the camera gets its player reference set
	 */
	public void setPlayer(Player player){
		this.player = player;
		this.xOffset = calculateXOffset();
		this.yOffset = calculateYOffset();
	}
	
	public void updateCamera() {
		this.xOffset = calculateXOffset();
		this.yOffset = calculateYOffset();
	}
	
	public int getXOffset() {
		return this.xOffset;
	}
	
	public int getYOffset() {
		return this.yOffset;
	}
	
	public int getScreenWidth() {
		return this.screenWidth;
	}
	
	public int getScreenHeight() {
		return this.screenHeight;
	}
	
	public int getWorldMaxX() {
		return this.worldWidthInPixels;
	}
	
	public int getWorldMaxY() {
		return this.worldHeightInPixels;
	}
	
	/*
	 * Called when screen dimensions change (currently only from World.goFullScreen()
	 */
	public void updateScreenDimensions(Dimension newDimension) {
		this.screenWidth = newDimension.width;
		this.screenHeight = newDimension.height;
	}
	
	
	/*
	 * Returns offset from the player's X coordinate to the center of the screen
	 */
	private int calculateXOffset() {
		if(this.playerMustStayAtCenter) {
			return this.screenWidth/2 - this.player.getCenterX(); // TODO UPDATE
		} else {
			int offset = this.screenWidth/2 - this.player.getCenterX();
			
			if(offset > 0) {
				return 0; //Hide invisible border tiles
			}
			if(offset < this.screenWidth - this.worldWidthInPixels) {
				return this.screenWidth - this.worldWidthInPixels;
			}
			
			return offset;
		}
	}
	
	/*
	 * Returns offset from the player's Y coordinate to the center of the screen
	 */
	private int calculateYOffset() {
		if(this.playerMustStayAtCenter) {
			return this.screenHeight/2 - this.player.getCenterY(); // TODO UPDATE
		} else {
			int offset = this.screenHeight/2 - this.player.getCenterY();
			
			if(offset > 0) {
				return 0;
			}
			if(offset < this.screenHeight - this.worldHeightInPixels) {
				return this.screenHeight - this.worldHeightInPixels;
			}
			
			return offset;
		}
	}
}
