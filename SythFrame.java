/**
 * SythFrame
 * @author Daniel, Willie, Muhammed, Pravinthan
 * @date June 14 2017
 * Class that creates a frame for the game
 */

//importing necessary libraries
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class SythFrame extends JFrame {
	//Declaring constants
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 800;
	
	//Constructor
	public SythFrame() {
		super("Syth"); // constructs a new JFrame
		
		// sets the look and feel of the UI to the same look and feel as the system
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		};
		
		setSize(WIDTH, HEIGHT);
		setIconImage(new ImageIcon("resource/SythIcon.png").getImage());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // disposes window and invokes the window listeners
		setResizable(false);
		
		SythPanel panel = new SythPanel(); // declare and instantiate SythPanel
		add(panel); // adds the game panel to the frame
		addWindowListener(new WindowListener()); // add our window listener
		SythPanel.KeyListen keyListener = panel.new KeyListen(); // instantiate the inner class object
		addKeyListener(keyListener); // add our key listener to the frame
		
		setLocationRelativeTo(null); // center frame
		setVisible(true);
	}// end of constructor
	
	/**
	 * WindowListener
	 * @author Daniel, Willie, Muhammed, Pravinthan
	 * @date June 14 2017
	 * Class that creates a window listener for the frame
	 */
	private class WindowListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			System.exit(0); // stops all threads
		}
	}// end of WindowListener
	
}// end of class