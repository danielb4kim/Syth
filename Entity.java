/**
 * Entity
 * This class stores all the general data for any Entity
 * @author Willie, Muhammed, Daniel, Pravinthan
 * date: 6/14/2017
 */
 
public abstract class Entity extends PositionalObject {
	private int health;
	private int maxHealth;
	private int attack;
	private int xSpeed;
	private int ySpeed;
	private boolean facingRight;
	
	/**
	 * Entity Constructor
	 * Sets values to all the variables within this class and the superclass
	* @param xPosition is the x coordinate of the entity
	 * @param yPosition is the y coordinate of the entity
	 * @param width is the width of the entity
	 * @param height is the height of the entity
	 * @param health is the current health of the entity
	 * @param maxHealth is the max health of the entity
	 * @param attack is the damage stat of the entity
	 * @param xSpeed is the horizontal speed of the entity
	 * @param ySpeed is the vertical speed of the entity
	 * @param facingRight determines if the entity is facing right or not
	 */
	
	public Entity(int xPosition, int yPosition, int width, int height, int health, int maxHealth, int attack, int xSpeed, int ySpeed, boolean facingRight) {
		super(xPosition, yPosition, width, height);
		this.health = health;
		this.maxHealth = maxHealth;
		this.attack = attack;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.facingRight = facingRight;
	}
	
	// getters and setters
	
	/**
	 * @return the current health of the entity
	 */
	public int getHealth() {
		return health;
	}
 
	/**
	 * @param health is the new health of the entity
	 */
	public void setHealth(int health) {
		this.health = health;
	}
 
	/**
	 * @return the maximum amount of health the entity has
	 */
	public int getMaxHealth() {
		return maxHealth;
	}
 
	/**
	 * @param maxHealth determines the maximum health the different entities have
	 */
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
 
	/**
	 * @return the amount of damage the entity can do
	 */
	public int getAttack() {
		return attack;
	}
 
	/**
	 * @param attack determines how much damage the entity can do
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}
 
	/**
	 * @return the horizontal speed of the entity
	 */
	public int getxSpeed() {
		return xSpeed;
	}
 
	/**
	 * @param xSpeed sets the horizontal speed of the entity
	 */
	public void setxSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}
 
	/**
	 * @return the vertical speed of the entity
	 */
	public int getySpeed() {
		return ySpeed;
	}
 
	/**
	 * @param ySpeed sets the vertical speed
	 */
	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}
 
	/**
	 * @return whether the entity is facing right or not
	 */
	public boolean isFacingRight() {
		return facingRight;
	}
 
	/**
	 * @param facingRight makes an entity face right or not
	 */
	public void setFacingRight(boolean facingRight) {
		this.facingRight = facingRight;
	}
 
}
 
