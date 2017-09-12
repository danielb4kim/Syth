/**
 * SythLauncher
 * @author Daniel, Willie, Muhammed, Pravinthan
 * @date June 14 2017
 * Class that launches the game
 */

//importing necessary libraries
import javax.swing.SwingUtilities;

public class SythLauncher {

	//start of main method
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SythFrame(); // constructs the frame
			}
		});
	}// end of main method
}// end of class