 
/**
 * ItemDrop Class
 * This class holds all general information on dropped items
 * @author Muhammed, Daniel, Willie, Pravinthan
 *
 */
public abstract class ItemDrop extends PositionalObject {
	private boolean isObtainable;
 
	/**
	 * ItemDrop Constructor
	 * @param xPosition is the x coordinate of the pick-up
	 * @param yPosition is the y coordinate of the pick-up
	 * @param width is the width of the pick-up
	 * @param height is the height of the pick-up
	 * @param isObtainable determines whether the pick-up has been picked up or not
	 */
	public ItemDrop(int xPosition, int yPosition, int width, int height, boolean isObtainable) {
		super(xPosition, yPosition, width, height);
		this.isObtainable = isObtainable;
	}
 
	//getter and setter
	
	/**
	 * @return isObtainable
	 */
	public boolean isObtainable() {
		return isObtainable;
	}
 
	/**
	 * @param isObtainable says if item has been picked up or not
	 */
	public void setObtainable(boolean isObtainable) {
		this.isObtainable = isObtainable;
	}
 
}
 
 
 
