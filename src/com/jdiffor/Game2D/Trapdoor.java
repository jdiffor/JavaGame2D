package com.jdiffor.Game2D;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Trapdoor {

	private boolean isOpen;
	private BufferedImage image;
	
	public Trapdoor() {
		this(false);
	}
	public Trapdoor(boolean isOpen) {
		this.isOpen = isOpen;
		this.setImage();
	}
	
	public void setOpen() {
		this.isOpen = true;
		this.setImage();
	}
	
	public void setClosed() {
		this.isOpen = false;
		this.setImage();
	}
	
	public boolean isOpen() {
		return this.isOpen;
	}
	
	public void draw(Graphics2D g, int x, int y, int width, int height) {
		g.drawImage(image, x, y, width, height, null);
	}
	
	private void setImage() {
		if(this.isOpen) {
			this.image = Utils.tryGetImage(AssetManager.groundImage("trapdoor_open.png"));
		} else {
			this.image = Utils.tryGetImage(AssetManager.groundImage("trapdoor_closed.png"));
		}
	}
}
