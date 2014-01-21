package lib;

import lib.jog.graphics;
import lib.jog.graphics.Image;
import lib.jog.graphics.Quad;

public class SpriteAnimation {

	private Image image;		 // the animation sequence
	private Quad quad;	         // the rectangle to be drawn from the animation bitmap
	private int frameCount;		 // number of frames in animation
	private int currentFrame;	 // the current frame
	private double framePeriod;	 // milliseconds between each frame (1000/fps)

	private double spriteWidth;	 // the width of the sprite to calculate the cut out rectangle
	private double spriteHeight; // the height of the sprite
	
	private boolean hasFinished; // Flag which is set to mark when all frames have been drawn

	private int x;				 // the X coordinate of the object (top left of the image)
	private int y;				 // the Y coordinate of the object (top left of the image)
	private double gameTime;	 // tracks how long a frame has been shown for. Updated by the parent scene update(dt)
	private double imageW, imageH;
	
	public SpriteAnimation(Image image, int x, int y, int fps, int frameCount){
		this.image = image;
		imageW = 2520;
		imageH = 200;
		this.x = x;
		this.y = y;
		this.frameCount = frameCount;
		currentFrame = 0;
		spriteWidth = 140;
		spriteHeight = 200;
		framePeriod = 1.0/fps;
		gameTime = 0;
		quad = getQuad(0);
		hasFinished = false;
	}
	/**
	 * Gets a new Quad of the image to be drawn
	 * @param currentFrame the currentFrame of the image to draw
	 * @return the new quad
	 */
	public Quad getQuad(int currentFrame){
		return graphics.newQuad(currentFrame * spriteWidth, 0, spriteWidth, spriteHeight, imageW * 1.62, imageH);
	}
	
	public void update(double dt) {
		if (hasFinished) return;
		
		gameTime += dt;
		if (gameTime > framePeriod) { // frame period exceeded
			gameTime = 0; //reset timer
			currentFrame++; //increment frame
			if (currentFrame > frameCount) {
				hasFinished = true;
			}
			quad = getQuad(currentFrame); //get new quad for new frame
		}
	}
	
	public void draw() {
		if (hasFinished) return;
		graphics.drawq(image, quad, x, y);
	}
	
	public boolean hasFinished() {
		return hasFinished;
	}

}