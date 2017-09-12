/**
 * Enemy
 * This class stores all the general data for any Enemy
 * @author Willie, Muhammed, Daniel, Pravinthan
 * date: 6/14/2017
 */
public abstract class Enemy extends Entity {
 
	/**
	 * Enemy Constructor
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
	public Enemy(int xPosition, int yPosition, int width, int height, int health, int maxHealth, int attack, int xSpeed, int ySpeed, boolean facingRight) {
		super(xPosition, yPosition, width, height, health, maxHealth, attack, xSpeed, ySpeed, facingRight);
	}
 
}
 
 
 
