package neuralfire.core;

import javax.swing.JFrame;

public class Main {
	
	/**
	 * This main routine creates a window and sets its content
	 * to be a panel of type Grid.  It shows the window in the
	 * center of the screen.
	 */
	public static void main(String[] args) {
		
		if(Constants.configuration == 1){
			Constants.mapFile = "map1";
			Constants.fireRadius = 3;
			Constants.yellRadius = 4;
			Constants.scaleUI = 5;
			Constants.sleepDuration = 200;
		} else if (Constants.configuration == 2){
			Constants.mapFile = "map2";
			Constants.fireRadius = 100;
			Constants.yellRadius = 20;
			Constants.scaleUI = 1;
			Constants.sleepDuration = 20;
			Constants.relays = 1;
		} else if(Constants.configuration == 3){
			Constants.mapFile = "map3";
			Constants.fireRadius = 3;
			Constants.yellRadius = 8;
			Constants.scaleUI = 5;
			Constants.sleepDuration = 100;
		} else if (Constants.configuration == 4){
			Constants.mapFile = "map4";
			Constants.fireRadius = 15;
			Constants.yellRadius = 50;
			Constants.scaleUI = 0.4;
			Constants.sleepDuration = 20;
		} else if (Constants.configuration == 5){
			Constants.mapFile = "map5";
			Constants.fireRadius = 10;
			Constants.yellRadius = 20;
			Constants.scaleUI = 1.5;
			Constants.sleepDuration = 20;
			Constants.displayPheromoneDots = true;
		}
		JFrame window; // The object that represents the window.
		window = new JFrame("Grid");  // Create a window with "Grid" in the title bar.
		
		EnvironmentParser envParser = new EnvironmentParser();
		Grid grid = envParser.parseImage("../../environmentMaps/"+Constants.mapFile+".bmp");
		
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
			    Thread.sleep(Constants.sleepDuration);                 //200 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
	}
}
