/**
 * ItemDropMoney Class
 * This class holds all information about the money pick-up
 * @author Willie, Pravinthan, Muhammed, Daniel
 * date: 6/14/2017
 */
//importing necessary libraries
import java.awt.Graphics;
 
import javax.swing.ImageIcon;
 
public class ItemDropMoney extends ItemDrop {
 
	/**
	 * ItemDropMoney Constructor
	 * @param xPosition is the x coordinate of the money pick-up
	 * @param yPosition is the y coordinate of the money pick-up
	 * @param width is the width of the money pick-up
	 * @param height is the height of the money pick-up
	 * @param isObtainable determines whether the money pick-up has been picked up or not
	 */
	public ItemDropMoney(int xPosition, int yPosition, int width, int height, boolean isObtainable) {
		super(xPosition, yPosition, width, height, isObtainable);
	}
 
	@Override
	public void draw(Graphics g, int xPosition, int yPosition, int width, int height) {
		g.drawImage(new ImageIcon("resource/itemDropMoney.gif").getImage(), xPosition, yPosition, width, height, null);
	}
 
}
 
 
 
