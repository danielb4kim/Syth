/**
 * ItemDropExtraStrength class
 * This class hold information about any general ItemDropExtraStrength
 * @author Willie, Pravinthan, Muhammed, Daniel
 * date: 6/14/2017
 */
//importing necessary libraries
import java.awt.Graphics;
 
import javax.swing.ImageIcon;
 
public class ItemDropExtraStrength extends ItemDrop {
 
	/**
	 * ItemDropExtraStrength Constructor
	 * @param xPosition is the x coordinate of the extra strength pick-up
	 * @param yPosition is the y coordinate of the extra strength pick-up
	 * @param width is the width of the extra strength pick-up
	 * @param height is the height of the extra strength pick-up
	 * @param isObtainable determines whether the extra strength pick-up has been picked up or not
	 */
	public ItemDropExtraStrength(int xPosition, int yPosition, int width, int height, boolean isObtainable) {
		super(xPosition, yPosition, width, height, isObtainable);
	}
 
	@Override
	public void draw(Graphics g, int xPosition, int yPosition, int width, int height) {
		g.drawImage(new ImageIcon("resource/itemDropExtraStrength.gif").getImage(), xPosition, yPosition, width, height, null);
	}
 
}
 
 
 
