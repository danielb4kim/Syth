//import objects
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
 
/**
*ScreenHelp 
*help screen that is used to tell the user the instructions of the game
*@Date: 6/14/2016
*@author: Muhammed, Daniel, Pravinthan, Willie
*/
public class ScreenHelp implements Screen {
//private variable declaration
	private static Image screen;
	private static Rectangle backButton;
 
/**
*constructor
*creates objects to be used in the screen
*/
	public ScreenHelp() {
		screen = new ImageIcon("resource/screenHelp.png").getImage();
		backButton = new Rectangle(SythFrame.WIDTH - 215, 10, 205, 85);
	}
 
	@Override
	public void draw(Graphics g) {
		g.drawImage(screen, 0, 0, SythFrame.WIDTH - 5, SythFrame.HEIGHT - 27, null);
	}
/**
*getBackButton
*gives the user a rectangle object that is the back button
*@return backButton, the rectangle object used for the back button
*/
	public static Rectangle getBackButton() {
		return backButton;
	}
}
 
 
