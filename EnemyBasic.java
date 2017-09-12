/**
 * EnemyBasic
 * This class stores all the general variables for any basic enemy
 * @author Willie, Muhammed, Daniel, Pravinthan
 * date: 6/14/2017
 */
 
// importing necessary libraries
import java.awt.Graphics;
 
import javax.swing.ImageIcon;
 
public class EnemyBasic extends Enemy {
 
	/**
	 * EnemyBasic Constructor
	 * Assigns values to variables within this class and its superclasses
	 * @param xPosition is the x coordinate of the enemy
	 * @param yPosition is the y coordinate of the enemy
	 * @param width is the width of the enemy
	 * @param height is the height of the enemy
	 * @param health is the current health of the enemy
	 * @param maxHealth is the max health of the enemy
	 * @param attack is the damage stat of the enemy
	 * @param xSpeed is the horizontal speed of the enemy
	 * @param ySpeed is the vertical speed of the enemy
	 * @param facingRight determines if the enemy is facing right or not
	 */
	public EnemyBasic(int xPosition, int yPosition, int width, int height, int health, int maxHealth, int attack, int xSpeed, int ySpeed, boolean facingRight) {
		super(xPosition, yPosition, width, height, health, maxHealth, attack, xSpeed, ySpeed, facingRight);
	}
 
	@Override
	public void draw(Graphics g, int xPosition, int yPosition, int width, int height) {
		g.drawImage(new ImageIcon("resource/enemyBasic.gif").getImage(), xPosition, yPosition, width, height, null);
	}
}
 
 
 
