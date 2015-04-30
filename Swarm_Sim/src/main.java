import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class main {
	
	/*
	static final int GRIDROWS = 100;
	static final int GRIDCOLS = 100;
	*/
	
	/**
	 * This main routine creates a window and sets its content
	 * to be a panel of type Grid.  It shows the window in the
	 * center of the screen.
	 */
	public static void main(String[] args) {
		JFrame window; // The object that represents the window.
		window = new JFrame("Grid");  // Create a window with "Grid" in the title bar.
		
		EnvironmentParser envParser = new EnvironmentParser();
		Grid grid = envParser.parseImage("environmentMaps/map1.bmp");
		
		window.setContentPane(grid);  // Add the Grid panel to the window.
		window.pack(); // Set the size of the window based on the panel's preferred size.

		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		grid.finalizeSimStep();
		
		while(true)
		{
			grid.Spin(grid);
			
			try {
			    Thread.sleep(200);                 //200 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
	}
}
