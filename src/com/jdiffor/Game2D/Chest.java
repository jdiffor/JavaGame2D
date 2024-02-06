package com.jdiffor.Game2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Chest {

	private Color itemColor;
	private BufferedImage image;
	private BufferedImage coloredImage;
	boolean complete;
	
	public Chest(String color) {
		this.itemColor = Utils.getColorFromString(color);
		this.image = Utils.tryGetImage(AssetManager.mapItemImage("chest.png"));
		this.complete = false;
		this.coloredImage = getColoredChest(this.image, this.itemColor);
	}
	
	public Color getColor() {
		return this.itemColor;
	}
	
	public boolean deposit(InventoryItem item, Map map) {
		if(this.getColor().equals(item.getColor())) {
			this.complete = true;
			map.removeChest(this);
			return true;
		}
		return false;
	}
	
	public void draw(Graphics2D g, int x, int y, int width, int height) {
		if(complete) {
			g.drawImage(image, x, y, width, height, null);
		} else {
			g.drawImage(coloredImage, x, y, width, height, null);
		}
	}
	
	
	public BufferedImage getColoredChest(BufferedImage baseImage, Color color) {
		BufferedImage newImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < baseImage.getWidth(); x++) {
			for(int y = 0; y < baseImage.getHeight(); y++) {
				if(baseImage.getRGB(x, y) == 0xffffffff) {
					newImage.setRGB(x, y, color.getRGB());
					baseImage.setRGB(x, y, 0xff000000);
				} else {
					newImage.setRGB(x, y, baseImage.getRGB(x, y));
				}
			}
		}
		return newImage;
	}
}
