//importing necessary library
import java.awt.Graphics;
 
/**
 * EnemyBoss
 * @author Willie, Muhammed, Daniel, Pravinthan
 * date: 6/14/2017
 *
 */
 
//is the toughest opponent, which is an extension of the enemy class
public class EnemyBoss extends Enemy {
 
	/**
	 * Enemy Boss Constructor
	 * @param xPosition the x position of the enemy boss
	 * @param yPosition the y position of the enemy boss
	 * @param width the width of the enemy boss
	 * @param height the height of the enemy boss
	 * @param health the health remaining of the boss
	 * @param attack the attack strength of the boss
	 * @param xSpeed the horizontal speed of the boss
	 * @param ySpeed the vertical speed of the boss
	 */
	//all the necessary characteristics of an enemy boss
	public EnemyBoss(int xPosition, int yPosition, int width, int height, int health, int maxHealth, int attack, int xSpeed, int ySpeed, boolean facingRight) {
		super(xPosition, yPosition, width, height, health, maxHealth, attack, xSpeed, ySpeed, facingRight);
	}
 
	@Override
	//draws the enemy boss, same information as in PositionalObject
	public void draw(Graphics g, int xPosition, int yPosition, int width, int height) {
		g.drawImage(null, xPosition, yPosition, width, height, null);
	}
 
}
 
 
 
 
 
 
