//import objects
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
 
/**ScreenGame
*the screen of the actual game
*@Date: 6/14/2016
*@author: Muhammed, Daniel, Pravinthan, Willie
*/
public class ScreenGame implements Screen {
//private variable declaration
	private static Image screen;
	private static Rectangle shopButton;
	
/**
*constructor
*creates objects to be used in the screen
*/
	public ScreenGame() {
		screen = new ImageIcon("resource/screenGame.png").getImage();
		shopButton = new Rectangle(SythFrame.WIDTH - 230, 15, 220, 95);
	}
 
	@Override
	public void draw(Graphics g) {
		g.drawImage(screen, 0, 0, SythFrame.WIDTH - 5, SythFrame.HEIGHT - 27, null);
	}
 
/**
*getShopButton
*gives the user a rectangle object that is the shop button
*@return shopButton, the rectangle object used for the shop button
*/
	public static Rectangle getShopButton() {
		return shopButton;
	}
 
}
 
 
