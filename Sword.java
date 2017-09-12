 
/** 
 * Sword class
 * general format for swords
 * @author: Muhammed, Daniel , Pravinthan , Willie
 * Date: 6/14/2017 
 */
public abstract class Sword extends Weapon {
 
	/**
	 * Sword constructor
	 * @param xPosition, x position of Sword
	 * @param yPosition, y position of Sword
	 * @param width,  width of Sword
	 * @param height,  height of Sword
	 * @param addAttackValue, added attack value of Sword
	 * @param storeCost, store cost of Sword
	 */
	public Sword(int xPosition, int yPosition, int width, int height, int addAttackValue,  int storeCost) {
		
		super(xPosition, yPosition, width, height, addAttackValue, storeCost);
	}
 
}
 
 
 
 
