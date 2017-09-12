/**
 * Player
 * @author Daniel, Willie, Muhammed, Pravinthan
 * @date June 14 2017
 * Class that creates a player
 */


//importing necessary libraries
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Player extends Entity {
	//declaring variables
	private boolean attackAllowed;
	private boolean damageAllowed;
	private boolean isInvincible;
	private int attackMultiplier;
	private int money;
	private Weapon selectedWeapon;
	private ArrayList<Weapon> purchasedWeapons;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean space;
	private boolean isShopOpened;
	private boolean isDead;
	
	public Player(int xPosition, int yPosition, int width, int height, int health, int maxHealth, int attack, int xSpeed, int ySpeed, boolean facingRight, 
			boolean attackAllowed, boolean damageAllowed, boolean isInvincible, int attackMultiplier, int money, Weapon selectedWeapon, 
			ArrayList<Weapon> purchasedWeapons, boolean up, boolean down, boolean left, boolean right, boolean space, boolean shopOpened, boolean isDead) {
		super(xPosition, yPosition, width, height, health , maxHealth, attack, xSpeed, ySpeed, facingRight);
		this.attackAllowed = attackAllowed;
		this.damageAllowed = damageAllowed;
		this.isInvincible = isInvincible;
		this.attackMultiplier = attackMultiplier;
		this.money = money;
		this.selectedWeapon = selectedWeapon;
		this.purchasedWeapons = purchasedWeapons;
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.space = space;
		this.isShopOpened = shopOpened;
		this.isDead = isDead;
	}

	@Override
	public void draw(Graphics g, int xPosition, int yPosition, int width, int height) {
		g.drawImage(new ImageIcon("resource/player1.gif").getImage(), xPosition, yPosition, width, height, null);
	}

	/**
	 * @return attackAllowed
	 */
	public boolean isAttackAllowed() {
		return attackAllowed;
	}// end of isAttackAllowed

	/**
	 * @param attackAllowed - boolean that allows attack
	 */
	public void setAttackAllowed(boolean attackAllowed) {
		this.attackAllowed = attackAllowed;
	}// end of setAttackAllowed

	/**
	 * @return damageAllowed
	 */
	public boolean isDamageAllowed() {
		return damageAllowed;
	}// end of isDamageAllowed

	/**
	 * @param damageAllowed - boolean that allows player to be damaged
	 */
	public void setDamageAllowed(boolean damageAllowed) {
		this.damageAllowed = damageAllowed;
	}// end of setDamageAllowed

	/**
	 * @return isInvincible
	 */
	public boolean isInvincible() {
		return isInvincible;
	}// end of isInvincible

	/**
	 * @param isInvincible - boolean that determines if player is invincible or not
	 */
	public void setInvincible(boolean isInvincible) {
		this.isInvincible = isInvincible;
	}// end of setInvincible

	/**
	 * @return attackMultiplier
	 */
	public int getAttackMultiplier() {
		return attackMultiplier;
	}// end of getAttackMultiplier

	/**
	 * @param attackMultiplier - int that multiplies player's attack
	 */
	public void setAttackMultiplier(int attackMultiplier) {
		this.attackMultiplier = attackMultiplier;
	}// end of setAttackMultiplier

	/**
	 * @return money
	 */
	public int getMoney() {
		return money;
	}// end of getMoney

	/**
	 * @param money - int holding player's money
	 */
	public void setMoney(int money) {
		this.money = money;
	}// end of setMoney

	/**
	 * @return selectedWeapon
	 */
	public Weapon getSelectedWeapon() {
		return selectedWeapon;
	}// end of getSelectedWeapon

	/**
	 * @param selectedWeapon - weapon that player is holding
	 */
	public void setSelectedWeapon(Weapon selectedWeapon) {
		this.selectedWeapon = selectedWeapon;
	}// end of setSelectedWeapon

	/**
	 * @return purchasedWeapons
	 */
	public ArrayList<Weapon> getPurchasedWeapons() {
		return purchasedWeapons;
	}// end of getPurchasedWeapons

	/**
	 * @param purchasedWeapons - ArrayList holding player's purchased weapons
	 */
	public void setPurchasedWeapons(ArrayList<Weapon> purchasedWeapons) {
		this.purchasedWeapons = purchasedWeapons;
	}// end of setPurchasedWeapons

	/**
	 * @return up
	 */
	public boolean isUp() {
		return up;
	}// end of isUp

	/**
	 * @param up - boolean that determines if player is moving up
	 */
	public void setUp(boolean up) {
		this.up = up;
	}// end of setUp

	/**
	 * @return down
	 */
	public boolean isDown() {
		return down;
	}// end of isDown

	/**
	 * @param down - boolean that determines if player is moving down
	 */
	public void setDown(boolean down) {
		this.down = down;
	}// end of setDown

	/**
	 * @return left
	 */
	public boolean isLeft() {
		return left;
	}// end of isLeft

	/**
	 * @param left - boolean that determines if player is moving left
	 */
	public void setLeft(boolean left) {
		this.left = left;
	}// end of setLeft

	/**
	 * @return right
	 */
	public boolean isRight() {
		return right;
	}// end of isRight

	/**
	 * @param right - boolean that determines if player is moving right
	 */
	public void setRight(boolean right) {
		this.right = right;
	}// end of setRight

	/**
	 * @return space
	 */
	public boolean isSpace() {
		return space;
	}// end of isSpace

	/**
	 * @param space - boolean that determines if player is attacking
	 */
	public void setSpace(boolean space) {
		this.space = space;
	}// end of setSpace

	/**
	 * @return isShopOpened
	 */
	public boolean isShopOpened() {
		return isShopOpened;
	}// end of isShopOpened

	/**
	 * @param isShopOpened - boolean determining which player opened shop
	 */
	public void setShopOpened(boolean isShopOpened) {
		this.isShopOpened = isShopOpened;
	}// end of setShopOpened

	/**
	 * @return isDead
	 */
	public boolean isDead() {
		return isDead;
	}// end of isDead

	/**
	 * @param isDead - boolean determining if player is dead
	 */
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}// end of setDead

}// end of class