 
/**
 * Weapon Class
 * Class holds all necessary information about general Weapon
 * @author Pravinthan, Daniel, Muhammed, Willie
 * date 6/14/2017
 */
public abstract class Weapon extends PositionalObject {
	private int addAttackValue;
	private int storeCost;
 
	/**
	 * Weapon Constructor
	 * @param xPosition is the x coordinate of the weapon
	 * @param yPosition is the y coordinate of the weapon
	 * @param width is the width of the weapon
	 * @param height is the height of the weapon
	 * @param addAttackValue is the amount of damage the weapon does 
	 * @param storeCost is the amount of money needed to buy the weapon
	 */
	public Weapon(int xPosition, int yPosition, int width, int height, int addAttackValue, int storeCost) {
		super(xPosition, yPosition, width, height);
		this.addAttackValue = addAttackValue;
		this.storeCost = storeCost;
	}
 
	//getters and setters
	
	/**
	 * @return the attack value
	 */
	public int getAddAttackValue() {
		return addAttackValue;
	}
 
	/**
	 * @param addAttackValue is the new attack value 
	 */
	public void setAddAttackValue(int addAttackValue) {
		this.addAttackValue = addAttackValue;
	}
	
	/**
	 * @return the store cost
	 */
	public int getStoreCost() {
		return storeCost;
	}
 
	/**
	 * @param storeCost is the price needed to buy the weapon
	 */
	public void setStoreCost(int storeCost) {
		this.storeCost = storeCost;
	}
}
 
 
 
 
