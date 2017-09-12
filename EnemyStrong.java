//importing necessary library
import java.awt.Graphics;
 
 
/**
 * 
 * @author Willie, Muhammed, Daniel, Pravinthan
 * date: 6/14/2017
 *
 */
 
 //is another type of enemy based of the normal enemy
public class EnemyStrong extends Enemy {
	
	/**
	 * Strong Enemy Constructor
	 * @param xPosition the x position of the stronger enemy 
	 * @param yPosition the y position of the stronger enemy 
	 * @param width the width of the stronger enemy
	 * @param height the height of the stronger enemy
	 * @param health the health remaining of the stronger enemy
	 * @param attack the attack strength of the stronger enemy
	 * @param xSpeed the horizontal speed of the stronger enemy
	 * @param ySpeed the vertical speed of the stronger enemy
	 */
 
	//all the necessary characteristics of a strong enemy
	public EnemyStrong(int xPosition, int yPosition, int width, int height, int health, int maxHealth, int attack, int xSpeed, int ySpeed, boolean facingRight) {
		super(xPosition, yPosition, width, height, health, maxHealth, attack, xSpeed, ySpeed, facingRight);
	}
 
	@Override
	//draws the stronger enemy on the map
	//uses same info as PositionalObject
	public void draw(Graphics g, int xPosition, int yPosition, int width, int height) {
		g.drawImage(null, xPosition, yPosition, width, height, null);
	}
 
}
 
 
 
