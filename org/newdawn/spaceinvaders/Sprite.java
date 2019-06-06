package org.newdawn.spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * A sprite to be displayed on the screen. Note that a sprite
 * contains no state information, i.e. its just the image and 
 * not the location. This allows us to use a single sprite in
 * lots of different places without having to store multiple 
 * copies of the image.
 * 
 * @author Kevin Glass
 */
public class Sprite {
	/** The image to be drawn for this sprite */
	private Image image;
	private boolean flipped = false;
	
	/**
	 * Create a new sprite based on an image
	 * 
	 * @param image The image that is this sprite
	 */
	public Sprite(Image image) {
		this.image = image;
	}

	/**
	 * Create a new sprite based on an image
	 * 
	 * @param image The image that is this sprite
	 * @param flipped If the image should be flipped vertically
	 */
	public Sprite(Image image, boolean flipped) {
		this.image = image;
		this.flipped = flipped;
	}
	
	/**
	 * Get the width of the drawn sprite
	 * 
	 * @return The width in pixels of this sprite
	 */
	public int getWidth() {
		return image.getWidth(null);
	}

	/**
	 * Get the height of the drawn sprite
	 * 
	 * @return The height in pixels of this sprite
	 */
	public int getHeight() {
		return image.getHeight(null);
	}

	/**
	 * Set an image to be drawn flipped vertically
	 */
	public void flipSpriteVertically() {
		flipped = !flipped;
	}
	
	/**
	 * Draw the sprite onto the graphics context provided
	 * 
	 * @param g The graphics context on which to draw the sprite
	 * @param x The x location at which to draw the sprite
	 * @param y The y location at which to draw the sprite
	 */
	public void draw(Graphics g,int x,int y) {
		//if (!flipped) {
			g.drawImage(image,x,y,null);
		// } else {
		// 	BufferedImage img = (BufferedImage)image;
		// 	int width = img.getWidth();
		// 	int height = img.getHeight();
		// 	g.drawImage(img, x, y, x+width, y+height, x, y+height, x+width, y, null);
		// }
	}
}