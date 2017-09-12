//import objects
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
 
/**
*ScreenMenu
*menu screen, that gives the user navigation buttons to go through the program
*@Date: 6/14/2016
*@author: Muhammed, Daniel, Pravinthan, Willie
*/
 
public class ScreenMenu implements Screen {
	private static Image screen;
	private static Rectangle startButton;
	private static Rectangle helpButton;
	private static Rectangle exitButton;
 
/**
*constructor
*creates objects to be used in the screen
*/
	public ScreenMenu() {
		screen = new ImageIcon("resource/screenMenu.png").getImage();
		startButton = new Rectangle(487, 305, 223, 80);
		helpButton = new Rectangle(503, 440, 183, 73);
		exitButton = new Rectangle(510, 580, 164, 70);
	}
 
	@Override
	public void draw(Graphics g) {
		g.drawImage(screen, 0, 0, SythFrame.WIDTH - 5, SythFrame.HEIGHT - 27, null);
	}
 
/**
*getStartButton
*gives the user a rectangle object that is the start button
*@return startButton, the rectangle object used for the start button
*/
	public static Rectangle getStartButton() {
		return startButton;
	}
 
 
 
 
 
/**
*getHelpButton
*gives the user a rectangle object that is the help button
*@return helpButton, the rectangle object used for the help button
*/
	public static Rectangle getHelpButton() {
		return helpButton;
	}
 
/**
*getExitButton
*gives the user a rectangle object that is the exit button
*@return exitButton, the rectangle object used for the exit button
*/
	public static Rectangle getExitButton() {
		return exitButton;
	}
}
 
 
