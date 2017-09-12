/**
 * PositionalObject
 * This class stores all general data for any Positional Object
 * @author Willie, Muhammed, Daniel, Pravinthan
 * date: 6/14/2017
 */
 
 
//importing necessary libraries
import java.awt.Graphics;
import java.awt.Rectangle;
 
 
 
public abstract class PositionalObject {
	private int xPosition;
	private int yPosition;
	private int width;
	private int height;
 
	/**
	 * PositionalObject Constructor
	 * Sets values to the variables within the class
	 * @param xPosition is the x coordinate of the object
	 * @param yPosition is the y coordinate of the object
	 * @param width is the width of the object
	 * @param height is the height of the object
	 * 
	 */
	public PositionalObject(int xPosition, int yPosition, int width, int height) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
	}
 
	
	public abstract void draw(Graphics g, int xPosition, int yPosition, int width, int height);
 
	public Rectangle getHitBox() {
		return new Rectangle(xPosition, yPosition, width, height);
	}
 
 
	//getters and setters
 
	/**
	 * @return the x coordinate of the object
	 */
	public int getxPosition() {
		return xPosition;
	}
 
	/**
	 * @param xPosition is the x position that the object is now in
	 */
	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}
 
	/**
	 * @return the y coordinate of the object
	 */
	public int getyPosition() {
		return yPosition;
	}
 
	/**
	 * @param yPosition is the y position that the object is now in
	 */
	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
 
	/**
	 * @return the width of the object
	 */
	public int getWidth() {
		return width;
	}
 
	/**
	 * @param width is the width of the object
	 */
	public void setWidth(int width) {
		this.width = width;
	}
 
	/**
	 * @return the height of the object
	 */
	public int getHeight() {
		return height;
	}
 
	/**
	 * @param height is the height of the object
	 */
	public void setHeight(int height) {
		this.height = height;
	}
 
}
 
 
 
