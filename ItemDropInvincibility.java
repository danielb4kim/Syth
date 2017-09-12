/**
 * ItemDropInvincibility Class
 * Class holds all info about any general ItemDropInvincibility
 * @author Willie, Daniel, Muhammed, Pravinthan
 * date: 6/14/2017
 */
//importing necessary libraries
import java.awt.Graphics;
 
import javax.swing.ImageIcon;
 
public class ItemDropInvincibility extends ItemDrop {
 
	/**
	 * ItemDropInvincibility Constructor
	 * @param xPosition is the x coordinate of the invincibility pick-up
	 * @param yPosition is the y coordinate of the invincibility pick-up
	 * @param width is the width of the invincibility pick-up
	 * @param height is the height of the invincibility pick-up
	 * @param isObtainable determines whether the invincibility pick-up has been picked up or not
	 */
	public ItemDropInvincibility(int xPosition, int yPosition, int width, int height, boolean isObtainable) {
		super(xPosition, yPosition, width, height, isObtainable);
	}
 
	@Override
	public void draw(Graphics g, int xPosition, int yPosition, int width, int height) {
		g.drawImage(new ImageIcon("resource/itemDropInvincibility.gif").getImage(), xPosition, yPosition, width, height, null);
	}
 
}
 
 
 
