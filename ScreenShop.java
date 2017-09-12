/**
* ScreenShop
* shop screen, that gives the user navigation buttons to go through the shop
* @author Muhammed, Daniel, Pravinthan, Willie
* Date: 6/14/2016
*/

//importing necessary libraries
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
 
import javax.swing.ImageIcon;
 
public class ScreenShop implements Screen {
	//declaring variables
	private static Image screen;
	private static Rectangle backButton;
	private static Rectangle drumstickSwordEquipButton;
	private static Rectangle longSwordEquipButton;
	private static Rectangle greatSwordEquipButton;
	private static Rectangle godSwordEquipButton;
 
	/**
	 * ScreenShop constructor
	 * intializes the variables
	 */
	public ScreenShop() {
		screen = new ImageIcon("resource/screenShop.png").getImage();	
		backButton = new Rectangle(SythFrame.WIDTH - 230, 10, 217, 100);
		drumstickSwordEquipButton = new Rectangle(50, 690, 150, 50);
		longSwordEquipButton = new Rectangle(335, 690, 150, 50);
		greatSwordEquipButton = new Rectangle(650, 690, 150, 50);
		godSwordEquipButton = new Rectangle(975, 690, 150, 50);
	}// end of constructor
 
	@Override
	public void draw(Graphics g) {
		g.drawImage(screen, 0, 0, SythFrame.WIDTH - 5, SythFrame.HEIGHT - 27, null);
	}// end of draw
 
	/**
	 * @return backButton
	 */
	public static Rectangle getBackButton() {
		return backButton;
	}// end of getBackButton
 
	/**
	 * @return drumstickSwordEquipButton
	 */
	public static Rectangle getDrumstickSwordEquipButton() {
		return drumstickSwordEquipButton;
	}// end of getDrumstickSwordEquipButton
 
	/**
	 * @return longSwordEquipButton
	 */
	public static Rectangle getLongSwordEquipButton() {
		return longSwordEquipButton;
	}// end of getLongSwordEquipButton
 
	/**
	 * @return greatSwordEquipButton
	 */
	public static Rectangle getGreatSwordEquipButton() {
		return greatSwordEquipButton;
	}// end of getGreatSwordEquipButton
 
	/**
	 * @return godSwordEquipButton
	 */
	public static Rectangle getGodSwordEquipButton() {
		return godSwordEquipButton;
	}// end of getGodSwordEquipButton
}// end of class
 
 
 
