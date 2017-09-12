/**
 * ScreenGameOver
 * @author Willie, Pravinthan, Daniel, Muhammed
 * @date June 14 2017
 * Class that creates the game over screen
 */

//importing necessary libraries
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class ScreenGameOver implements Screen {
	//declaring variables
	private static Image screen;
	private static Rectangle menuButton;

	//Constructor
	public ScreenGameOver() {
		screen = new ImageIcon("resource/screenGameOver.png").getImage();
		menuButton = new Rectangle(SythFrame.WIDTH - 235, 13, 227, 90);
	}// end of constructor

	@Override
	public void draw(Graphics g) {
		g.drawImage(screen, 0, 0, SythFrame.WIDTH - 5, SythFrame.HEIGHT - 27, null);
	}// end of draw

	/**
	 * @return menuButton
	 */
	public static Rectangle getMenuButton() {
		return menuButton;
	}// end of getMenuButton
}// end of class