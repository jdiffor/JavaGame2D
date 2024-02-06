package com.jdiffor.Game2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class InventoryItem {
	
	private BufferedImage iconImage;
	private BufferedImage itemImage;
	private Color color = Color.black;

	public InventoryItem(String iconPath, String itemImagePath) {
		this.iconImage = Utils.tryGetImage(iconPath);
		this.itemImage = Utils.tryGetImage(itemImagePath);
	}
	
	public InventoryItem(String color) {
		this.color = Utils.getColorFromString(color);
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void drawIcon(Graphics2D g, int x, int y, int width, int height) {
		if(iconImage != null) {
			g.drawImage(iconImage, x, y, width, height, null);
		} else {
			g.setColor(color);
			g.fillOval(x, y, width, height);
		}
	}
	
	public void drawPlayerItem(Graphics2D g, int x, int y, int width, int height) {
		if(itemImage != null) {
			g.drawImage(itemImage, x, y, width, height, null);
		} else {
			g.setColor(color);
			g.fillOval(x+width/4, y+height/4, width/2, height/2);
		}
		
	}
	
}
