import java.awt.Graphics;

import javax.swing.ImageIcon;
 
public class SwordGod extends Sword {
 
	public SwordGod(int xPosition, int yPosition, int width, int height, int addAttackValue, int storeCost) {
		super(xPosition, yPosition, width, height, addAttackValue, storeCost);
	}
 
	@Override
	public void draw(Graphics g, int xPosition, int yPosition, int width, int height) {
		g.drawImage(new ImageIcon("resource/swordGod.png").getImage(), xPosition, yPosition, width, height, null);
	}
 
}