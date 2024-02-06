package com.jdiffor.Game2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Utils {

	public static final int SINGLE_UNIT = 32; // Standard width/height in game
	
	public Utils() {
		
	}
	
	public static long time() {
		return System.nanoTime();
	}
	
	public static boolean isInRange(int num, int lower, int upper) {
		return num >= lower && num <= upper;
	}
	
	public static int min(int one, int two) {
		return one < two ? one : two;
	}
	
	public static int max(int one, int two) {
		return one > two ? one : two;
	}
	
	public static int randomInt(int max) {
		return (int) ((max+1) * Math.random()); 
	}
	
	public static BufferedImage tryGetImage(String path) {
		if(path == null || path.equals("")) {
			return null;
		}
		try {
			return ImageIO.read(new File(AssetManager.loadResource(path)));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static BufferedImage getImagePart(BufferedImage image, int x, int y, int size) {
		int scaledSize = size / GamePanel.SCALE;
		return image.getSubimage(x, y, scaledSize, scaledSize);
	}
	
	public static Color getColorFromString(String colorName) {
		Color color = null;
		try {
			color = (Color) Color.class.getField(colorName).get(null);
		} catch (Exception e) {
			color = Color.black;
		}
		return color;
	}
}
