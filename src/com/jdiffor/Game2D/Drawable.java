package com.jdiffor.Game2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Drawable {

private final static Color DEFAULT_COLOR = Color.GREEN;
	
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	private Color color;
	private BufferedImage image;
	private boolean flippedHorizontally = false;
	protected Camera camera;
	
	/*
	 * Class for things that can be drawn in the world (non-menu and non-HUD images)
	 * Things that should be drawn in the world should inherit from Drawable
	 */
	public Drawable(Camera camera, int x, int y, int size, Color c) {
		this(camera, x, y, size, size, c);
	}
	
	public Drawable(Camera camera, int x, int y, int width, int height, Color c) {
		this(camera, x, y, width, height, null, c, null);
	}
	
	public Drawable(Camera camera, int x, int y, int size, String imgName) {
		this(camera, x, y, size, size, imgName, DEFAULT_COLOR, null);
	}
	
	public Drawable(Camera camera, int x, int y, int width, int height, String imgName) {
		this(camera, x, y, width, height, imgName, DEFAULT_COLOR, null);
	}
	
	public Drawable(Camera camera, int x, int y, int size, BufferedImage image) {
		this(camera, x, y, size, size, null, DEFAULT_COLOR, image);
	}
	
	public Drawable(Camera camera, int x, int y, int width, int height, BufferedImage image) {
		this(camera, x, y, width, height, null, DEFAULT_COLOR, image);
	}
	
	public Drawable(Camera camera, int x, int y, int width, int height, String imgName, Color c, BufferedImage image) {
		this.camera = camera;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = (image == null && imgName != null) ? Utils.tryGetImage(imgName) : image;
		this.color = c;
	}
	
	public void draw(Graphics2D g) {
		this.draw(g, false);
	}
	
	public void draw(Graphics2D g, boolean addOpacity) {
		// Draws tile with opacity (to see player through the tile)
		if(addOpacity) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		} else {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		
		if(this.image != null) {
			if(this.flippedHorizontally) {
				g.drawImage(image, getScreenX() + width, getScreenY(), -width, height, null);
			} else {
				g.drawImage(image, getScreenX(), getScreenY(), width, height, null);
			}
		} else {
			g.setColor(color);
			g.fillRect(getScreenX(), getScreenY(), width, height);
		}	
	}
	
	public int getScreenX() {
		return camera.getXOffset() + x;
	}
	
	public int getScreenY() {
		return camera.getYOffset() + y;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public void flip(boolean flipHorizontally) {
		this.flippedHorizontally = flipHorizontally;
	}
}
