package com.jdiffor.Game2D;
import java.awt.image.BufferedImage;

public class AnimatedSpritesheet {

	private BufferedImage[] images;
	private int currentImage;
	private int frameDuration;
	private int frameCounter;
	
	public AnimatedSpritesheet(String filename, int rows, int cols, int singleWidth, int singleHeight) {
		this(filename, rows, cols, singleWidth, singleHeight, 30, 0);
	}
	
	public AnimatedSpritesheet(String filename, int rows, int cols, int singleWidth, int singleHeight, int frameDuration) {
		this(filename, rows, cols, singleWidth, singleHeight, frameDuration, 0);
	}
	
	public AnimatedSpritesheet(String filename, int rows, int cols, int singleWidth, int singleHeight, int frameDuration, int startingImage) {
		BufferedImage wholeImage = Utils.tryGetImage(filename);
		if(wholeImage.getWidth() != cols*singleWidth || wholeImage.getHeight() != rows*singleHeight) {
			System.err.println("AnimatedSpritesheet image does not subdivide correctly");
			System.err.println("Whole width: " + wholeImage.getWidth() + " but " + cols +  "x" + singleWidth);
			System.err.println("Whole height: " + wholeImage.getHeight() + " but " + rows +  "x" + singleHeight);
		}
		
		images = new BufferedImage[rows * cols];
		
		// Divide wholeImage into individual images
		for(int i = 0; i < cols; i++) {
			for(int j = 0; j < rows; j++) {
				images[i + j*cols] = wholeImage.getSubimage(i * singleWidth, j * singleHeight, singleWidth, singleHeight);
			}
		}
		
		this.currentImage = startingImage;
		this.frameDuration = frameDuration;
		this.frameCounter = 1;
	}
	
	public BufferedImage getNextImage() {
		if(frameCounter >= frameDuration) {
			currentImage++;
			if(currentImage >= images.length) {
				currentImage = 0;
			}
			frameCounter = 1;
		} else {
			frameCounter++;
		}
		
		return images[currentImage];
	}
	
	public int getFrameDuration() {
		return this.frameDuration;
	}
	
}
