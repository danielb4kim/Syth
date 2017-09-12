/**
 * ItemDropMaxHealth Class
 * Class holds necessary information about the max health pick-up
 * @author Daniel, Muhammed, Willie, Pravinthan
 * date 6/14/2017
 */
//importing necessary libraries
import java.awt.Graphics;
 
import javax.swing.ImageIcon;
 
public class ItemDropMaxHealth extends ItemDrop {
 
	/**
	 * ItemDropMaxHealth Constructor
	 * @param xPosition is the x coordinate of the max health pick-up
	 * @param yPosition is the y coordinate of the max health pick-up
	 * @param width is the width of the max health pick-up
	 * @param height is the height of the max health pick-up
	 * @param isObtainable determines whether the max health pick-up has been picked up or not
	 */
	public ItemDropMaxHealth(int xPosition, int yPosition, int width, int height, boolean isObtainable) {
		super(xPosition, yPosition, width, height, isObtainable);
	}
 
	@Override
	public void draw(Graphics g, int xPosition, int yPosition, int width, int height) {
		g.drawImage(new ImageIcon("resource/itemDropMaxHealth.gif").getImage(), xPosition, yPosition, width, height, null);
	}
 
}
 
 
 
