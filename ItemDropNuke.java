/**
 * ItemDropNuke class
 * This class holds all necessary information about ItemDropNuke
 * @author Daniel, Muhammed, Pravinthan, Willie
 * date: 6/14/2017
 */
//importing necessary libraries
import java.awt.Graphics;
 
import javax.swing.ImageIcon;
 
public class ItemDropNuke extends ItemDrop {
 
	/**
	 * ItemDropNuke constructor
	 * @param xPosition is the x coordinate of the nuke
	 * @param yPosition is the y coordinate of the nuke
	 * @param width is the width of the nuke
	 * @param height is the height of the nuke
	 * @param isObtainable determines whether the nuke has been picked up or not
	 */
	public ItemDropNuke(int xPosition, int yPosition, int width, int height, boolean isObtainable) {
		super(xPosition, yPosition, width, height, isObtainable);
	}
 
	@Override
	public void draw(Graphics g, int xPosition, int yPosition, int width, int height) {
		g.drawImage(new ImageIcon("resource/itemDropNuke.gif").getImage(), xPosition, yPosition, width, height, null);
	}
 
}
 
 
 
